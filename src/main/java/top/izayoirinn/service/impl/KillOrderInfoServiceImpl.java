package top.izayoirinn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.izayoirinn.common.KillData;
import top.izayoirinn.domain.KillOrderInfo;
import top.izayoirinn.domain.OrderInfo;
import top.izayoirinn.enums.OrderStatusEnum;
import top.izayoirinn.exception.GlobalException;
import top.izayoirinn.mapper.OrderInfoMapper;
import top.izayoirinn.service.KillOrderInfoService;
import top.izayoirinn.mapper.KillOrderInfoMapper;
import org.springframework.stereotype.Service;
import top.izayoirinn.utils.JsonUtils;
import top.izayoirinn.vo.KillGoodsVO;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class KillOrderInfoServiceImpl extends ServiceImpl<KillOrderInfoMapper, KillOrderInfo>
        implements KillOrderInfoService {
    @Autowired
    private KillOrderInfoMapper killOrderInfoMapper;

    @Override
    public Integer getOrderIdByUserIdAndKillId(Integer userId, Integer killId) {
        List<KillOrderInfo> killOrderInfoList =
                killOrderInfoMapper.listKillOrderInfoByUserIdAndKillId(userId, killId);
        if (killOrderInfoList == null || killOrderInfoList.size() != 1) {
            throw new GlobalException("获取订单id信息异常,订单为空或有多条订单记录");
        }
        return killOrderInfoList.get(0).getOrderId();
    }

    @Override
    public Integer getKillIdByOrderId(Integer orderId) {
        QueryWrapper<KillOrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        KillOrderInfo killOrderInfo = getOne(queryWrapper);
        return killOrderInfo == null ? null : killOrderInfo.getKillId();
    }

    public Integer getKillIdByOrderIdUserMapper(Integer orderId) {
        return killOrderInfoMapper.getKillIdByOrderId(orderId);
    }
}




