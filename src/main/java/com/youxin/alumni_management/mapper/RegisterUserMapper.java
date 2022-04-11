package com.youxin.alumni_management.mapper;


import com.youxin.alumni_management.pojo.RegisterUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegisterUserMapper {

    List<RegisterUser> findAllRegisterUser(Integer departmentId);
}
