package com.youxin.alumni_management.mapper;


import com.youxin.alumni_management.pojo.RegisterUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegisterUserMapper {

    //根据管理员所属学院查询该管理员下的所有注册用户信息
    List<RegisterUser> findAllRegisterUser(Integer departmentId);

    //根据注册用户id更新注册用户状态
    int updUserStatus(Integer registerId, Integer status);

    //用户注册
    int insRegisterUser(RegisterUser registerUser);
}
