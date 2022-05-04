package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-04 18:56
 */
@Mapper
public interface UserMapper {

    //用户自己修改用户基本信息
    int updUserBySelf(User user);

}
