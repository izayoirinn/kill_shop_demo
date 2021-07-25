package top.izayoirinn.mapper;

import org.apache.ibatis.annotations.Param;
import top.izayoirinn.domain.KillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.List;

/**
 * @Entity top.izayoirinn.domain.KillGoods
 */
public interface KillGoodsMapper extends BaseMapper<KillGoods> {
    List<KillGoodsVO> killGoodsList();

    /**
     * 通过主键查询出秒杀商品信息
     *
     * @param killId 主键
     * @return
     */
    KillGoodsVO getKillGoodsByKillId(Integer killId);

    /**
     * 通过商品id获取秒杀商品信息
     *
     * @param goodsId
     * @return
     */
    KillGoodsVO getKillGoodsByGoodsId(Integer goodsId);

    Integer decreaseStockCount(@Param("orderId") Integer orderId, @Param("count") Integer count);
}




