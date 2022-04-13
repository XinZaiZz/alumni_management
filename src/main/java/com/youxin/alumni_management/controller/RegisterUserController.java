package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.RegisterUser;
import com.youxin.alumni_management.service.RegisterUserService;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @author youxin
 * @program alumni_management
 * @description 用户注册控制层
 * @date 2022-04-09 23:29
 */

@Controller
@RequiredArgsConstructor
public class RegisterUserController {

//    @Autowired
    private final RegisterUserService registerUserService;

    @GetMapping("/findAllRegisterUser/{departmentId}")
    public Object findAllRegisterUser(@PathVariable("departmentId") Integer departmentId, HttpServletRequest request) {

        List<RegisterUser> allRegisteringUser = registerUserService.findAllRegisterUser(departmentId);

        request.setAttribute("allRegisteringUser", allRegisteringUser);
        //如果返回结果为空
        if (allRegisteringUser.isEmpty()) {
            return Result.failure(ResultCode.RESULT_EMPTY, allRegisteringUser);
        }
        //如果返回结果不为空
        return Result.success(ResultCode.SUCCESS, allRegisteringUser);
    }

    @GetMapping("/admin/register/Pass-Or-UnPass/{registerId}/{status}")
    public String registerUserPass(@PathVariable("registerId") Integer registerId, @PathVariable("status") Integer status, HttpServletRequest request) {
        //修改用户在注册用户表中的状态
        int data = -1;
        //1为设置为通过，2为未通过
        data = registerUserService.updUserStatus(registerId, status);
        if (data > 0) {
            //修改成功
            List<RegisterUser> allRegisteringUser = registerUserService.findAllRegisterUser(((Admin) request.getSession().getAttribute("admin")).getDepartmentId());
            return "redirect:/toDashboard";

        }
        //修改失败返回到500错误页面
        return "500";
    }
}
