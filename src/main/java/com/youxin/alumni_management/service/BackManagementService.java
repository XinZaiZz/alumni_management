package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.User;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-06 02:23
 */
public interface BackManagementService {
    List<User> findAllUserByAdminDepartmentId(Integer departmentId);
}
