package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    /**
     * @author youxin
     * @date 2022-02-08 21:49
     * @param userName
     * @return com.youxin.pojo.User
     * @throws
     * @since
     */
    //通过用户名查找是否存在该用户
    User findUserByName(String userName);

    /**
     * @author youxin
     * @date 2022-02-08 21:49
     * @param adminName
     * @return com.youxin.pojo.User
     * @throws
     * @since
     */
    //通过用户名查找是否存在该用户
    Admin findAdminByName(String adminName);
}
