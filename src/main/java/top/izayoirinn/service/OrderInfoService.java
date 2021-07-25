package top.izayoirinn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.List;

/**
 *
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 秒杀商品，扣减库存,并生成订单信息
     *
     * @param userId
     * @param killGoodsVO
     * @return
     */
    OrderInfo killGoods(Integer userId, KillGoodsVO killGoodsVO);

    void payOrder(Integer userId, Integer orderId);

    void cancelOrder(Integer userId, Integer orderId);

    List<OrderInfo> getOrderInfoByUserId(Integer userId);
}
