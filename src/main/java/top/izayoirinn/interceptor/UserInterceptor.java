package top.izayoirinn.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import top.izayoirinn.utils.RedirectUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author Rinn Izayoi
 * @date 2021/7/12 18:33
 */
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (null != user) {
            return true;
        }
        String returnUrl = request.getRequestURL().toString();
        if (returnUrl.contains("/seckill/kill")) {
            returnUrl = returnUrl.replace("/kill/", "/view/");
        }
        String encodeReturnUrl = URLEncoder.encode(returnUrl, "utf-8");

        RedirectUtils.redirect(request, response, "/login?returnUrl= " + encodeReturnUrl);
        return false;
    }
}
