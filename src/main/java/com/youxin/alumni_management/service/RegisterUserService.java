package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.RegisterUser;
import com.youxin.alumni_management.pojo.User;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 注册表服务层
 * @date 2022-04-09 23:26
 */
public interface RegisterUserService {

    List<RegisterUser> findAllRegisterUser(Integer departmentId);

    int updUserStatus(Integer registerId, Integer status);

    int insRegisterUser(RegisterUser registerUser);

    List<RegisterUser> findAllAlreadyPassRegisterUser();

    int insPassUser(User user);
}
