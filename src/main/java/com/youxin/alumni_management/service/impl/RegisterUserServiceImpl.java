package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.RegisterUserMapper;
import com.youxin.alumni_management.pojo.RegisterUser;
import com.youxin.alumni_management.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 注册表服务层实现类
 * @date 2022-04-09 23:27
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    @Autowired
    RegisterUserMapper registerUserMapper;

    @Override
    public List<RegisterUser> findAllRegisterUser(Integer departmentId) {
        return registerUserMapper.findAllRegisterUser(departmentId);
    }
}
