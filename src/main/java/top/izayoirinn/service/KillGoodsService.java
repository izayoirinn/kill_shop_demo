package top.izayoirinn.service;

import top.izayoirinn.domain.KillGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.List;

/**
 *
 */
public interface KillGoodsService extends IService<KillGoods> {
    List<KillGoodsVO> listKillGoods();

    /**
     * 通过kill_goods的主键id获取相关商品信息
     *
     * @param killId 主键id
     * @return
     */
    KillGoodsVO getKillGoodsByKillId(Integer killId);

    /**
     * @param goodsId 商品id
     * @return
     */
    KillGoodsVO getKillGoodsByGoodsId(Integer goodsId);
}
