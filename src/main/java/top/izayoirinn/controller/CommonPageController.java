package top.izayoirinn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.domain.UserInfo;
import top.izayoirinn.service.UserInfoService;

import javax.servlet.http.HttpSession;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 15:54
 */
@Controller
public class CommonPageController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }

    @GetMapping("/reset")
    @ResponseBody
    public JsonResult<Object> reset(HttpSession session) {
        String userName = (String) session.getAttribute("user");
        UserInfo userInfo = userInfoService.getUserByName(userName);
        if (userInfo == null) {
            return JsonResult.errorMsg("null");
        }

        return JsonResult.ok();
    }
}
