package com.youxin.alumni_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.RegisterUser;
import com.youxin.alumni_management.service.RegisterUserService;
import com.youxin.alumni_management.utils.Result;
import com.youxin.alumni_management.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    private final Logger logger = Logger.getLogger(this.getClass());

//    @Autowired
    private final RegisterUserService registerUserService;

    @GetMapping("/toRegisterPage")
    public String toRegisterPage() {
        return "login/register";
    }

    @GetMapping("/findAllRegisterUser/{departmentId}")
    public Object findAllRegisterUser(@PathVariable("departmentId") Integer departmentId, HttpServletRequest request) throws JsonProcessingException {

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

    @PostMapping("/register/registerUser")
    public String registerUser(@ModelAttribute RegisterUser registerUser, HttpServletRequest request) {
        registerUser.setDepartmentId(Integer.parseInt(request.getParameter("department")));
        String gender = registerUser.getGender();
        switch (gender) {
            case "male" :
                registerUser.setGender("男");
            case "female" :
                registerUser.setGender("女");
        }
        logger.info("注册用户：" + registerUser);
        int data = -1;
        if ((data = registerUserService.insRegisterUser(registerUser)) > 0) {
            //插入成功
            return "redirect:/toIndex";
        }
        //插入失败
        return "500";

    }
}
