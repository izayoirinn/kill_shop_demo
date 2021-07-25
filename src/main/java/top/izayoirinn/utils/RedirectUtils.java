package top.izayoirinn.utils;

/**
 * @author Rinn Izayoi
 * @date 2021/7/14 19:09
 */

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能描述
 *
 * @author lgj
 * @Description 重定向工具类
 * @date 2/27/19
 */
@Slf4j
public class RedirectUtils {
    /**
     * 功能描述
     *
     * @author lgj
     * @Description 重定向
     * @date 2/27/19
     * @param:
     * @return:
     */
    public static void redirect(HttpServletRequest request, HttpServletResponse response, String redirectUrl) {

        try {
            //如果是Ajax请求
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                log.info("ajax redirect");
                sendRedirect(response, redirectUrl);
            }
            //如果是浏览器地址栏请求
            else {
                log.info("normal redirect ");
                response.sendRedirect(redirectUrl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 功能描述
     *
     * @author lgj
     * @Description Ajax请求时重定向处理
     * @date 2/27/19
     * @param:
     * @return:
     */
    private static void sendRedirect(HttpServletResponse response, String redirectUrl) {
        try {
            //这里并不是设置跳转页面，而是将重定向的地址发给前端，让前端执行重定向
            //设置跳转地址
            response.setHeader("redirectUrl", redirectUrl);
            //设置跳转使能
            response.setHeader("enableRedirect", "true");
            response.flushBuffer();
        } catch (IOException ex) {
            log.error("Could not redirect to: " + redirectUrl, ex);
        }
    }
}