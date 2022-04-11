package com.youxin.alumni_management.controller;

import org.springframework.stereotype.Controller;
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
        HttpSession session = request.getSession();
        session.setAttribute("unAuthorized","授权未成功!");
        return "index";
    }

}
