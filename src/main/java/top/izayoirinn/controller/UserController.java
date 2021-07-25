package top.izayoirinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.izayoirinn.bo.UserBO;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.domain.UserInfo;
import top.izayoirinn.service.UserInfoService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Rinn Izayoi
 * @date 2021/7/12 18:39
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userService;

    /**
     * 用户登录验证
     *
     * @param userBO
     * @param session
     * @return
     */
    @PostMapping("/login")
    public JsonResult<Object> login(@Valid @RequestBody UserBO userBO,
                                    HttpSession session) {

        UserInfo userInfo = userService.userLogin(userBO);

        session.setAttribute("user", userInfo.getUsername());
        return JsonResult.ok();

    }

    /**
     * 用户退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public JsonResult<Object> logout(HttpSession session) {
        session.removeAttribute("user");
        return JsonResult.ok();
    }

}
