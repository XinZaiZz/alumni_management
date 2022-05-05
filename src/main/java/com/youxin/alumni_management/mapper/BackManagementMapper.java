package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-06 02:16
 */
@Mapper
public interface BackManagementMapper {

    //查询该管理员下所有校友信息
    List<User> findAllUserByAdminDepartmentId(Integer departmentId);
}
