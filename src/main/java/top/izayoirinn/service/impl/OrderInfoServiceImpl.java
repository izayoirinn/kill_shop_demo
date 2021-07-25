package top.izayoirinn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.izayoirinn.common.KillData;
import top.izayoirinn.domain.KillOrderInfo;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.enums.OrderStatusEnum;
import top.izayoirinn.exception.GlobalException;
import top.izayoirinn.exception.RabbitReQueueException;
import top.izayoirinn.mapper.KillGoodsMapper;
import top.izayoirinn.mapper.KillOrderInfoMapper;
import top.izayoirinn.mapper.OrderInfoMapper;
import top.izayoirinn.service.OrderInfoService;
import top.izayoirinn.utils.RedisOperator;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Slf4j
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private KillGoodsMapper killGoodsMapper;

    @Autowired
    private KillOrderInfoMapper killOrderInfoMapper;

    @Autowired
    private RedisOperator redisOperator;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderInfo killGoods(Integer userId, KillGoodsVO killGoodsVO) {
        if (userId == null || killGoodsVO == null) {
            throw new GlobalException("秒杀信息错误!");
        }
        // 新加订单
        OrderInfo orderInfo = new OrderInfo();
        // 商品id
        orderInfo.setGoodsId(killGoodsVO.getGoodsId());
        orderInfo.setUserId(userId);
        //  设置购买数量 固定为1
        orderInfo.setGoodsCount(1);
        //  设置购买价格 固定为1件的价格
        orderInfo.setGoodsPrice(killGoodsVO.getKillPrice());
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderStatus(OrderStatusEnum.WAIT_PAY.state);

        orderInfo.setGoodsName(killGoodsVO.getGoodsName());

        int insertOrderInfoStatus = orderInfoMapper.insert(orderInfo);
        if (insertOrderInfoStatus < 1) {
            throw new RabbitReQueueException("创建订单失败...");
        }

        KillOrderInfo killOrderInfo = new KillOrderInfo();
        killOrderInfo.setKillId(killGoodsVO.getKillId());
        killOrderInfo.setUserId(userId);
        killOrderInfo.setOrderId(orderInfo.getOrderId());

        int insert = killOrderInfoMapper.insert(killOrderInfo);
        if (insert < 1) {
            throw new RabbitReQueueException("秒杀订单生成失败");
        }

        return orderInfo;
    }

    /**
     * 1. 修改orderInfo订单状态(已支付,支付时间)
     * 2. 减少数据库中的商品信息库存(秒杀商品库存|基本库存信息?)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void payOrder(Integer userId, Integer orderId) {
        // 更新订单状态
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId);
        updateWrapper.set("order_status", OrderStatusEnum.WAIT_DELIVER.state);
        updateWrapper.set("pay_date", new Date());
        // 更新订单状态
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new GlobalException("下单失败,请重试...");
        }
        // 数据库扣减秒杀商品表的库存
        Integer decreaseStockStatus = killGoodsMapper.decreaseStockCount(orderId, 1);
        if (decreaseStockStatus < 1) {
            throw new GlobalException("下单失败,请重试...");
        }

    }

    @Transactional
    @Override
    public void cancelOrder(Integer userId, Integer orderId) {

        // 更新数据库的订单状态
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId);
        updateWrapper.set("order_status", OrderStatusEnum.CLOSE.state);
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new RabbitReQueueException("更新数据库订单状态失败,请重试...");
        }

        // 更新redis中的库存数量
        Integer killId = killOrderInfoMapper.getKillIdByOrderId(orderId);
        if (killId == null) {
            // 关联秒杀订单失败
            throw new GlobalException("killId为null,取消订单失败,请重试...");
        }

        // 库存加1
        redisOperator.hIncrement(KillData.KILL_STOCK, String.valueOf(killId), 1);

        // 删除redis中用户的下单状态
        redisOperator.sRem(KillData.KILL_ORDER_USER + ":" + userId, String.valueOf(killId));
        log.info("cancelOrder():订单id {} 取消支付", orderId);
    }

    @Override
    public List<OrderInfo> getOrderInfoByUserId(Integer userId) {
        // TODO 订单的逻辑删除判断
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.list(queryWrapper);
    }
}




