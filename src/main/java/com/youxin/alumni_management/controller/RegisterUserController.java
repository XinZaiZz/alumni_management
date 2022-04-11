package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.RegisterUser;
import com.youxin.alumni_management.service.RegisterUserService;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
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
public class RegisterUserController {

    @Autowired
    RegisterUserService registerUserService;

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
}
