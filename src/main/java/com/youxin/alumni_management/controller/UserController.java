package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.LoginService;
import com.youxin.alumni_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author youxin
 * @program alumni_management
 * @description 用户控制类
 * @date 2022-05-04 14:29
 */

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LoginService loginService;

    @GetMapping("/toUpdatePage")
    public String toUpdatePage(HttpServletRequest request, Model model) {
        User loginUser = (User) request.getSession().getAttribute("user");
        //如果未登录
        if (loginUser == null) {
            return "redirect:/unAuthorized";
        }
        User user = loginService.findUserByName(loginUser.getUserName());
        //获取当前用户信息
        model.addAttribute("user", user);
//        System.out.println("updUser==>" + user);
        return "user/update";
    }

    @GetMapping("/toChangePasswordPage")
    public String toChangePasswordPage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/unAuthorized";
        }
        return "user/change_password";
    }

    @PostMapping("/user/update")
    public String updateUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/unAuthorized";
        }
        //日期工具类
        Calendar cal = Calendar.getInstance();
//        User user = (User) request.getSession().getAttribute("user");
        user.setNickName(request.getParameter("nickName") != null ? request.getParameter("nickName") : user.getNickName());
        user.setGender(request.getParameter("gender") != null ? request.getParameter("gender") : user.getGender());
        //设置出生日期
        user.setBirth( (!request.getParameter("birth").equals("")) ? request.getParameter("birth") : user.getBirth());
        //设置年龄
        user.setAge(request.getParameter("birth") != null ? (cal.get(Calendar.YEAR) - Integer.parseInt(request.getParameter("birth").substring(0, 4))) : user.getAge());
        user.setStudentNumber((! request.getParameter("studentNumber").equals("")) ? Long.parseLong(request.getParameter("studentNumber")) : user.getStudentNumber());
        user.setMajor(request.getParameter("major") != null ? request.getParameter("major") : user.getMajor());
        user.setGrade(request.getParameter("grade") != null ? request.getParameter("grade") : user.getGrade());
        user.setBedroomNumber(request.getParameter("bedroomNumber") != null ? request.getParameter("bedroomNumber") : user.getBedroomNumber());
        user.setOccupation(request.getParameter("occupation") != null ? request.getParameter("occupation") : user.getOccupation());
        user.setCompany(request.getParameter("company") != null ? request.getParameter("company") : user.getCompany());
        user.setOccupationGroup(request.getParameter("occupationGroup") != null ? request.getParameter("occupationGroup") : user.getOccupationGroup());
        user.setRegion(request.getParameter("province") + "-" + request.getParameter("city") + "-" + request.getParameter("district"));
        user.setNumber( (! request.getParameter("number").equals("")) ? Long.parseLong(request.getParameter("number")) : user.getNumber());
        user.setEmail(request.getParameter("email") != null ? request.getParameter("email") : user.getEmail());
        user.setPersonalProfile(request.getParameter("personalProfile") != null ? request.getParameter("personalProfile") : user.getPersonalProfile());

        int data = -1;
        data = userService.updUserBySelf(user);
        //修改成功
        if (data > 0) {
            request.setAttribute("updUserMsg", "修改成功！返回首页");
            return "index";
        }
        request.setAttribute("updUserMsg", "修改失败！检查网络或者联系管理员试试哦QAQ");
        return "500";
    }
}
