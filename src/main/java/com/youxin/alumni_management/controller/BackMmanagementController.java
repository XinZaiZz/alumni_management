package com.youxin.alumni_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author youxin
 * @program alumni_management
 * @description 后台管理controller
 * @date 2022-02-08 23:49
 */
@Controller
public class BackMmanagementController {

    @GetMapping("/dashboard")
    public String toDashboard() {
        return "/back_management/dashboard";
    }
}
