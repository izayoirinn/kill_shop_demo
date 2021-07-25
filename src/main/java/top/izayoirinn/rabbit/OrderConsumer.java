package top.izayoirinn.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import top.izayoirinn.common.KillData;
import top.izayoirinn.config.RabbitMQConfig;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.enums.OrderStatusEnum;
import top.izayoirinn.exception.RabbitReQueueException;
import top.izayoirinn.service.KillGoodsService;
import top.izayoirinn.service.OrderInfoService;
import top.izayoirinn.utils.JsonUtils;
import top.izayoirinn.utils.RedisOperator;
import top.izayoirinn.vo.KillGoodsVO;

import java.io.IOException;
import java.util.*;

/**
 * @author Rinn Izayoi
 * @date 2021/7/21 12:32
 * 实现对订单的异步处理以及对订单的定时任务
 */
@Slf4j
@Component
public class OrderConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private KillGoodsService killGoodsService;

    /**
     * 创建预支付订单
     */
    @RabbitListener(queues = RabbitMQConfig.KILL_DIRECT_QUEUE)
    public void killGoods(OrderMessage orderMessage,
                          Channel channel, Message message) throws IOException {
        Integer userId = orderMessage.getUserId();
        Integer killId = orderMessage.getKillId();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        /**
         * 判断秒杀商品是否存在
         * 不是获取存放在redis中商品数据,而是获取数据库中的商品数据
         * 原因:
         *   redis中存放的是实时数据,key可能会失效,造成查询不到
         */
        KillGoodsVO killGoodsVO = killGoodsService.getKillGoodsByKillId(killId);
        if (killGoodsVO == null) {
            // 拒绝消息，不重新入队
            channel.basicNack(deliveryTag, false, false);
            log.error("秒杀商品数据为空,消息DeliveryTag:{}被拒绝,消息被丢弃", deliveryTag);
            return;
        }
        // 生成一笔订单
        try {
            OrderInfo orderInfo = orderInfoService.killGoods(userId, killGoodsVO);
            // 将用户秒杀的信息存到redis中
            redisOperator.sAdd(
                    KillData.KILL_ORDER_USER + ":" + userId,
                    killId + "");
            // 签收消息
            channel.basicAck(deliveryTag, false);

            // 生成订单后,将订单信息放入延迟交换机中
            Map<String, Integer> sendData = new HashMap<>();
            sendData.put("orderId", orderInfo.getOrderId());
            sendData.put("userId", orderInfo.getUserId());
            rabbitTemplate.convertAndSend(RabbitMQConfig.KILL_DELAYED_EXCHANGE,
                    RabbitMQConfig.KILL_DELAYED_ROUTING_KEY, sendData,
                    correlationData -> {
                        correlationData.getMessageProperties().setDelay(1000 * 60 * 3);
                        return correlationData;
                    });
            log.info("预支付订单生成,订单id:{},下单用户id:{}。将消息放入延迟交换机中,三分钟后关闭交易!", orderInfo.getOrderId(), userId);

        } catch (RabbitReQueueException e) {
            log.error("生成订单出现异常,消息DeliveryTag:{}被拒绝,异常信息为:{},消息重新放入队列", deliveryTag, e.getMessage());
            channel.basicNack(deliveryTag, false, true);
        } catch (Exception e) {
            //TODO  如果在插入订单信息时,出现异常该如何处理
            log.error("生成订单出现异常,消息DeliveryTag:{}被拒绝,异常信息为:{},消息被丢弃", deliveryTag, e.getMessage());
            channel.basicNack(deliveryTag, false, false);
        }
    }

    /**
     * 判断三分钟后是否下单,否则自动关闭订单
     */
    @RabbitListener(queues = RabbitMQConfig.KILL_DELAYED_QUEUE)
    public void confirmPaidStatus(Map<String, Integer> receive, Channel channel, Message message) throws IOException {
        Integer orderId = receive.get("orderId");
        Integer userId = receive.get("userId");
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        if (orderId == null || userId == null) {
            // 否认应答,并不重新放回队列
            channel.basicNack(deliveryTag, false, false);
            log.warn("定时队列消费者否认了消息。原因: userId或orderId为null");
            return;
        }
        // 从数据库中查询出订单
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (orderInfo == null) {
            // 否认应答,并不重新放回队列
            log.warn("定时队列消费者否认了消息。原因: 数据库中无订单记录");
            channel.basicNack(deliveryTag, false, false);
            return;
        }

        // 用户仍未支付,自动关闭
        if (orderInfo.getOrderStatus().equals(OrderStatusEnum.WAIT_PAY.state)) {
            try {
                orderInfoService.cancelOrder(userId, orderId);
                log.warn("时间到期,订单id {} 被自动关闭", orderId);
                channel.basicAck(deliveryTag, false);
            } catch (RabbitReQueueException e) {
                // 将消息放回
                log.warn("定时消息未被签收,将重新放回队列,异常信息: {} ", e.getMessage());
                channel.basicNack(deliveryTag, false, true);
            }
            return;
        }
        // 订单状态为其他情况(已经关闭或者已经下单),签收消息
        channel.basicAck(deliveryTag, false);
    }
}
