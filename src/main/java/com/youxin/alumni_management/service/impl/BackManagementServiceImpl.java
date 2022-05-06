package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.BackManagementMapper;
import com.youxin.alumni_management.pojo.*;
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

    @Override
    public int insUserByAdmin(User user) {
        return backManagementMapper.insUserByAdmin(user);
    }

    @Override
    public int updUserByAdmin(User user) {
        return backManagementMapper.updUserByAdmin(user);
    }

    @Override
    public int delUserByAdmin(Integer userId) {
        return backManagementMapper.delUserByAdmin(userId);
    }

    @Override
    public List<NewsArticle> findAllNewsArticleByAdminId(Integer adminId) {
        return backManagementMapper.findAllNewsArticleByAdminId(adminId);
    }

    @Override
    public int delNewsArticleByNewArticleId(Integer newArticleId) {
        return backManagementMapper.delNewsArticleByNewArticleId(newArticleId);
    }

    @Override
    public List<Activity> findAllActivityByAdminDepartmentId(Integer departmentId) {
        return backManagementMapper.findAllActivityByAdminDepartmentId(departmentId);
    }

    @Override
    public int delActivityByActivityId(Integer activityId) {
        return backManagementMapper.delActivityByActivityId(activityId);
    }

    @Override
    public List<Forum> findAllForumByAdminDepartmentId(Integer departmentId) {
        return backManagementMapper.findAllForumByAdminDepartmentId(departmentId);
    }

    @Override
    public int delForumByForumId(Integer forumId) {
        return backManagementMapper.delForumByForumId(forumId);
    }

    @Override
    public List<AlumniHelp> findAllAlumniHelpByAdminId(Integer adminId) {
        return backManagementMapper.findAllAlumniHelpByAdminId(adminId);
    }

    @Override
    public int delAlumniHelpByHelpId(Integer helpId) {
        return backManagementMapper.delAlumniHelpByAlumniHelpId(helpId);
    }

    @Override
    public List<AlumniPhoto> findAllAlumniPhotoByAdminDepartmentId(Integer departmentId) {
        return backManagementMapper.findAllAlumniPhotoByAdminDepartmentId(departmentId);
    }

    @Override
    public int delAlumniPhotoByPhotoId(Integer photoId) {
        return backManagementMapper.delAlumniPhotoByPhotoId(photoId);
    }

    @Override
    public int updAlumniPhoto(AlumniPhoto alumniPhoto) {
        return backManagementMapper.updAlumniPhoto(alumniPhoto);
    }
}
