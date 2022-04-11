package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.LoginMapper;
import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author youxin
 * @program alumni_management
 * @description 用户登录业务实现类
 * @date 2022-03-26 15:00
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public User findUserByName(String userName) {
        return loginMapper.findUserByName(userName);
    }

    @Override
    public Admin findAdminByName(String adminName) {
        return loginMapper.findAdminByName(adminName);
    }
}
