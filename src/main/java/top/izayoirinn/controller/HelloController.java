package top.izayoirinn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rinn Izayoi
 * @date 2021/7/13 19:12
 */
@Slf4j
@Controller
public class HelloController {

    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping("/hello")
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "hello redo";
    }
}
