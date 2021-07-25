package top.izayoirinn.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;
import top.izayoirinn.annotation.Monitor;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.common.KillData;
import top.izayoirinn.config.RabbitMQConfig;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.rabbit.OrderMessage;
import top.izayoirinn.service.KillGoodsService;
import top.izayoirinn.utils.JsonUtils;
import top.izayoirinn.utils.RedisOperator;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Rinn Izayoi
 * @date 2021/7/23 9:06
 */
@Slf4j
@RestController
@RequestMapping("/jmeter")
public class JmeterTestController {

    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private KillGoodsService killGoodsService;


    /**
     * 测试提交下单请求(取消对用户的校验)
     *
     * @param killId 秒杀商品id
     * @return 订单信息
     */
    @Monitor
    @PostMapping("/kill/{killId}")
    @ResponseBody
    public JsonResult<OrderInfo> killGoods(@PathVariable("killId") Integer killId) {
        // 参数校验
        if (killId == null || killId < 1) {
            return JsonResult.errorMsg("请选择秒杀商品");
        }
        String stockCountRedisStr = redisOperator.hGet(KillData.KILL_STOCK, String.valueOf(killId));
        if (StringUtils.isBlank(stockCountRedisStr)
                || Integer.parseInt(stockCountRedisStr) <= 0) {
            return JsonResult.errorMsg("商品库存不够...");
        }
        // 判断秒杀商品是否存在
        KillGoodsVO killGoodsVO;
        // 获取存放在redis中商品数据
        String killGoodsRedis = redisOperator.get(KillData.KILL_GOODS_DATA + ":" + killId);
        // null是手动放置的数据(三分钟)，表示数据库并没有此数据信息
        if ("null".equals(killGoodsRedis)) {
            return JsonResult.errorMsg("商品不存在");
        }
        // 正常判断
        if (StringUtils.isNotBlank(killGoodsRedis)) {
            killGoodsVO = JsonUtils.jsonToPojo(killGoodsRedis, KillGoodsVO.class);
        } else {
            synchronized (this) {
                killGoodsRedis = redisOperator.get(KillData.KILL_GOODS_DATA + ":" + killId);
                // null是手动放置的数据(三分钟)，表示数据库并没有此数据信息
                if ("null".equals(killGoodsRedis)) {
                    return JsonResult.errorMsg("商品不存在");
                }
                if (StringUtils.isNotBlank(killGoodsRedis)) {
                    killGoodsVO = JsonUtils.jsonToPojo(killGoodsRedis, KillGoodsVO.class);
                } else {
                    killGoodsVO = killGoodsService.getKillGoodsByKillId(killId);
                    if (killGoodsVO != null) {
                        // 将商品基本信息存储到redis中
                        redisOperator.set(KillData.KILL_GOODS_DATA + ":" + killId,
                                JsonUtils.objectToJson(killGoodsVO), 60 * 10);
                    } else {
                        redisOperator.set(KillData.KILL_GOODS_DATA + ":" + killId,
                                "null", 60 * 3);
                    }
                }
            }
        }
        if (killGoodsVO == null) {
            return JsonResult.errorMsg("商品不存在");
        }
        // 商品秒杀时间判断
        Date startTime = killGoodsVO.getStartDate();
        Date endTime = killGoodsVO.getEndDate();
        Date nowTime = new Date();
        // 如果当前时间比开始时间还早，则秒杀还未开始
        if (nowTime.before(startTime)) {
            return JsonResult.errorMsg("秒杀尚未开始");
        }
        // 如果当前时间比结束时间晚，则秒杀已经结束
        if (nowTime.after(endTime)) {
            return JsonResult.errorMsg("秒杀已经结束");
        }

        // 从redis中扣减一件商品,数据库库存不做改变
        // 指定 lua 脚本，并且指定返回值类型
        DefaultRedisScript<Long> redisScript =
                new DefaultRedisScript<>(KillData.STOCK_KILL_LUA_SCRIPT, Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        List<String> keyList = new ArrayList<>();
        keyList.add(KillData.KILL_STOCK);
        Long result = redisTemplate.execute(redisScript, keyList, String.valueOf(killId), "-1");
        // result : 返回的值(在这里是剩余的库存数)|| null代表商品为零或者没有此商品
        log.info("redis减少库存,lua脚本执行结果: {} (null表示库存小于等于零)", result);
        if (result == null) {
            return JsonResult.errorMsg("商品库存不够...");
        }

        // 封装消息
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setUserId(1);
        orderMessage.setKillId(killId);

        // 发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.KILL_DIRECT_EXCHANGE,
                RabbitMQConfig.KILL_DIRECT_ROUTING_KEY,
                orderMessage);
        // 等待完成队列
        return JsonResult.delayOrder();
    }
}
