package com.youxin.alumni_management.service;

/*
    用户登录服务层接口
 */

import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.User;

public interface LoginService {

    //根据用户名查找登录用户信息
    User findUserByName(String userName);

    //根据管理员用户名查找管理员用户信息
    Admin findAdminByName(String adminName);
}
