package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.LoginService;
import com.youxin.alumni_management.service.UserService;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        user.setNickName(request.getParameter("nickName"));
        user.setGender(request.getParameter("gender"));
        //设置出生日期
        user.setBirth(request.getParameter("birth"));
        //设置年龄
        user.setAge((request.getParameter("birth").equals("")) ? 0 : (cal.get(Calendar.YEAR) - Integer.parseInt(request.getParameter("birth").substring(0, 4))));
        user.setStudentNumber(((request.getParameter("studentNumber").equals("")) || (request.getParameter("number").equals("0"))) ? 0 : Long.parseLong(request.getParameter("studentNumber")));
        user.setMajor(request.getParameter("major"));
        user.setGrade(Integer.parseInt(request.getParameter("grade")));
        user.setBedroomNumber(request.getParameter("bedroomNumber"));
        user.setOccupation(request.getParameter("occupation"));
        user.setCompany(request.getParameter("company"));
        user.setOccupationGroup(request.getParameter("occupationGroup"));
        user.setRegion(request.getParameter("province") + "-" + request.getParameter("city") + "-" + request.getParameter("district"));
        user.setNumber(((request.getParameter("number").equals("")) || (request.getParameter("number").equals("0"))) ? 0 : Long.parseLong(request.getParameter("number")));
        user.setEmail(request.getParameter("email"));
        user.setPersonalProfile(request.getParameter("personalProfile"));

        int data = -1;
        data = userService.updUserBySelf(user);
        //修改成功
        if (data > 0) {
            request.setAttribute("updUserMsg", "修改成功！返回首页");
            return "redirect:/toIndex";
        }
        request.setAttribute("updUserMsg", "修改失败！检查网络或者联系管理员试试哦QAQ");
        return "500";
    }

    @PostMapping("/updPassword")
    @ResponseBody
    public String updPasswordBySelf(HttpServletRequest request) {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        User user = (User) request.getSession().getAttribute("user");
        int data = -1;
        if (!oldPassword.equals(user.getPassword())) {
            return Result.failure(ResultCode.USER_PWD_ERROR, data);
        }
        //修改
        data = userService.updUserPasswordBySelf(oldPassword, newPassword);
        if (data > 0) {
            //修改成功,删除系统中用户session，必须重新登录
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            return Result.success(ResultCode.SUCCESS, data);
        }else {
            return Result.failure(ResultCode.FAIL, data);
        }
    }
}
