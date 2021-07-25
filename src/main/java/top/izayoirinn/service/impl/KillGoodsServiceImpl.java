package top.izayoirinn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.izayoirinn.domain.KillGoods;
import top.izayoirinn.service.KillGoodsService;
import top.izayoirinn.mapper.KillGoodsMapper;
import org.springframework.stereotype.Service;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.List;

/**
 *
 */
@Service
public class KillGoodsServiceImpl extends ServiceImpl<KillGoodsMapper, KillGoods>
        implements KillGoodsService {
    @Autowired
    private KillGoodsMapper killGoodsMapper;

    @Override
    public List<KillGoodsVO> listKillGoods() {
        return killGoodsMapper.killGoodsList();
    }

    @Override
    public KillGoodsVO getKillGoodsByKillId(Integer killId) {
        return killGoodsMapper.getKillGoodsByKillId(killId);
    }

    @Override
    public KillGoodsVO getKillGoodsByGoodsId(Integer goodsId) {
        return killGoodsMapper.getKillGoodsByGoodsId(goodsId);
    }
}




