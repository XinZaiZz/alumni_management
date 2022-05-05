package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.UserMapper;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-04 19:12
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public int updUserBySelf(User user) {
        return userMapper.updUserBySelf(user);
    }

    @Override
    public int updUserPasswordBySelf(String oldPassword, String newPassword) {
        return userMapper.updUserPasswordBySelf(oldPassword, newPassword);
    }
}
