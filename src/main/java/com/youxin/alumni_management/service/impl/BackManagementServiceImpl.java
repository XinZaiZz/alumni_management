package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.BackManagementMapper;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.BackManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-06 02:24
 */
@Service
@RequiredArgsConstructor
public class BackManagementServiceImpl implements BackManagementService {

    private final BackManagementMapper backManagementMapper;

    @Override
    public List<User> findAllUserByAdminDepartmentId(Integer departmentId) {
        return backManagementMapper.findAllUserByAdminDepartmentId(departmentId);
    }
}
