package com.youxin.alumni_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author youxin
 * @program youxinblog
 * @description
 * @date 2022-02-09 21:48
 */
@Controller
public class ShiroController {

    @RequestMapping("/unAuthorized")
    public String unAuthorized(HttpServletRequest request) {
        request.setAttribute("unAuthorized","未授权成功，返回首页！");
        return "forward:/toIndex";
    }

}
