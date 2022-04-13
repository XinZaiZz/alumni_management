package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.service.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author youxin
 * @program alumni_management
 * @description 后台管理controller
 * @date 2022-02-08 23:49
 */
@Controller
@RequiredArgsConstructor
public class BackManagementController {

//    @Autowired
    private final RegisterUserService registerUserService;

    @GetMapping("/dashboard")
    public String toDashboard() {

        return "/back_management/dashboard";
    }
}
