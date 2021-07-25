package top.izayoirinn.mapper;

import org.apache.ibatis.annotations.Param;
import top.izayoirinn.domain.KillOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity top.izayoirinn.domain.KillOrderInfo
 */
public interface KillOrderInfoMapper extends BaseMapper<KillOrderInfo> {
    Integer getKillIdByOrderId(Integer orderId);

    /**
     * 查询用户的秒杀订单信息,除去关闭的订单信息
     *
     * @param userId 用户id
     * @param killId 秒杀商品id
     * @return 秒杀订单集合
     */
    List<KillOrderInfo> listKillOrderInfoByUserIdAndKillId(@Param("userId") Integer userId,
                                                           @Param("killId") Integer killId);
}




