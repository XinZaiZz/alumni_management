package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.RegisterUserMapper;
import com.youxin.alumni_management.pojo.RegisterUser;
import com.youxin.alumni_management.service.RegisterUserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RegisterUserServiceImpl implements RegisterUserService {

//    @Autowired
    private final RegisterUserMapper registerUserMapper;

    @Override
    public List<RegisterUser> findAllRegisterUser(Integer departmentId) {
        return registerUserMapper.findAllRegisterUser(departmentId);
    }

    @Override
    public int updUserStatus(Integer registerId, Integer status) {
        return registerUserMapper.updUserStatus(registerId, status);
    }
}
