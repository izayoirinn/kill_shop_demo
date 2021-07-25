package top.izayoirinn.service;

import top.izayoirinn.bo.UserBO;
import top.izayoirinn.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserInfoService extends IService<UserInfo> {
    UserInfo userLogin(UserBO userBO);

    UserInfo getUserByName(String username);
}
