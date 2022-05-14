package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.BackManagementMapper;
import com.youxin.alumni_management.pojo.*;
import com.youxin.alumni_management.service.BackManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<NewsArticle> findAllExamineNewArticles() {
        return backManagementMapper.findAllExamineNewArticles();
    }

    @Override
    public int updNewsArticleStatus(Integer newArticleId, Integer status) {
        return backManagementMapper.updNewsArticleStatus(newArticleId, status);
    }

    @Override
    public List<Activity> findAllActivityByAdminDepartmentId(Integer departmentId) {
        return backManagementMapper.findAllActivityByAdminDepartmentId(departmentId);
    }

    @Override
    public List<Activity> findAllExamineActivityByAdminDepartmentId(Integer departmentId) {
        return backManagementMapper.findAllExamineActivityByAdminDepartmentId(departmentId);
    }

    @Override
    public List<Activity> findAllExamineActivityByPZHUAdmin() {
        return backManagementMapper.findAllExamineActivityByPZHUAdmin();
    }

    @Override
    public int updActivityStatus(Integer activityId, Integer status) {
        return backManagementMapper.updActivityStatus(activityId, status);
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
    public List<AlumniHelp> findAllExamineAlumniHelp() {
        return backManagementMapper.findAllExamineAlumniHelp();
    }

    @Override
    public int updAlumniHelpStatus(Integer helpId, Integer status) {
        return backManagementMapper.updAlumniHelpStatus(helpId, status);
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

    @Override
    public List<AlumniPhoto> findAllExamineAlumniPhoto() {
        return backManagementMapper.findAllExamineAlumniPhoto();
    }

    @Override
    public int updAlumniPhotoStatus(Integer photoId, Integer status) {
        return backManagementMapper.updAlumniPhotoStatus(photoId, status);
    }

    @Override
    public int selCountUser(Integer departmentId) {
        return backManagementMapper.selUserCount(departmentId);
    }

    @Override
    public int selCountMale(Integer departmentId) {
        return backManagementMapper.selMaleCount(departmentId);
    }

    @Override
    public List<Map<String, Object>> countByOccupationGroup(Integer departmentId) {
        return backManagementMapper.countByOccupationGroup(departmentId);
    }

    @Override
    public List<Map<String, Object>> countByMajorGroup(Integer departmentId) {
        return backManagementMapper.countByMajorGroup(departmentId);
    }

    @Override
    public List<Map<String, Object>> countByGrade(Integer departmentId) {
        return backManagementMapper.countByGrade(departmentId);
    }

    @Override
    public List<Map<String, Object>> countByAge(Integer departmentId) {
        return backManagementMapper.countByAge(departmentId);
    }

    @Override
    public List<Map<String, Object>> countByInstructorName(Integer departmentId) {
        return backManagementMapper.countByInstructorName(departmentId);
    }


}
