package top.izayoirinn.service;

import top.izayoirinn.domain.KillOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.vo.KillGoodsVO;

/**
 *
 */
public interface KillOrderInfoService extends IService<KillOrderInfo> {

    /**
     * 通过用户id以及秒杀商品id获取唯一的一件商品信息
     * 非关闭状态订单记录只能有一条
     *
     * @param userId
     * @param killId
     * @return
     */
    Integer getOrderIdByUserIdAndKillId(Integer userId, Integer killId);

    Integer getKillIdByOrderId(Integer orderId);
}
