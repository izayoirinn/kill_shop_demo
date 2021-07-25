package top.izayoirinn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import top.izayoirinn.bo.UserBO;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.domain.UserInfo;
import top.izayoirinn.exception.GlobalException;
import top.izayoirinn.handler.GlobalExceptionHandler;
import top.izayoirinn.service.UserInfoService;
import top.izayoirinn.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 *
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Override
    public UserInfo userLogin(UserBO userBO) {
        if (userBO == null ||
                StringUtils.isBlank(userBO.getUsername()) ||
                StringUtils.isBlank(userBO.getPassword())) {
            throw new GlobalException("请输入用户信息");
        }
        String md5Password = DigestUtils.md5DigestAsHex(userBO.getPassword().getBytes(StandardCharsets.UTF_8));

        UserInfo userInfo = getUserByName(userBO.getUsername());

        if (null == userInfo) {
            throw new GlobalException("用户名错误");
        }
        if (!userInfo.getPassword().equals(md5Password)) {
            throw new GlobalException("密码错误");
        }

        return userInfo;
    }

    @Override
    public UserInfo getUserByName(String username) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);
    }
}




