package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.BackManagementService;
import com.youxin.alumni_management.service.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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

    private final BackManagementService backManagementService;

    @GetMapping("/dashboard")
    public String toDashboard(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        return "/back_management/dashboard";
    }

    @GetMapping("/toHomePage")
    public String toHomePage(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        return "back_management/home";
    }

    @GetMapping("/toUserInfoPage")
    public String toUserInfoPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<User> allUser = backManagementService.findAllUserByAdminDepartmentId(admin.getDepartmentId());
        if (allUser != null) {
            model.addAttribute("allUser", allUser);
            return "back_management/user_info";
        }else
            return "500";
    }
}
