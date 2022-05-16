package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-05-06 02:23
 */
public interface BackManagementService {
    List<User> findAllUserByAdminDepartmentId(Integer departmentId);

    int insUserByAdmin(User user);

    int updUserByAdmin(User user);

    int delUserByAdmin(Integer userId);

    List<NewsArticle> findAllNewsArticleByAdminId(Integer adminId);

    int delNewsArticleByNewArticleId(Integer newArticleId);

    List<NewsArticle> findAllExamineNewArticles();

    int updNewsArticleStatus(Integer newArticleId, Integer status);

    List<Activity> findAllActivityByAdminDepartmentId(Integer departmentId);

    List<Activity> findAllExamineActivityByAdminDepartmentId(Integer departmentId);

    List<Activity> findAllExamineActivityByPZHUAdmin();

    int updActivityStatus(Integer activityId, Integer status);

    int delActivityByActivityId(Integer activityId);

    List<Forum> findAllForumByAdminDepartmentId(Integer departmentId);

    int delForumByForumId(Integer forumId);

    List<AlumniHelp> findAllAlumniHelpByAdminId(Integer adminId);

    int delAlumniHelpByHelpId(Integer helpId);

    List<AlumniHelp> findAllExamineAlumniHelp();

    int updAlumniHelpStatus(Integer helpId, Integer status);

    //获取管理员学院所有校友展示文章
    List<AlumniPhoto> findAllAlumniPhotoByAdminDepartmentId(Integer departmentId);

    //删除校友展示文章
    int delAlumniPhotoByPhotoId(Integer photoId);

    //更新校友展示文章基本信息
    int updAlumniPhoto(AlumniPhoto alumniPhoto);

    List<AlumniPhoto> findAllExamineAlumniPhoto();

    int updAlumniPhotoStatus(Integer photoId, Integer status);

    int selCountUser(Integer departmentId);

    int selCountMale(Integer departmentId);

    List<Map<String, Object>> countByOccupationGroup(Integer departmentId);

    List<Map<String, Object>> countByMajorGroup(Integer departmentId);

    List<Map<String, Object>> countByGrade(Integer departmentId);

    List<Map<String, Object>> countByAge(Integer departmentId);

    List<Map<String, Object>> countByInstructorName(Integer departmentId);

    //查询所有管理员信息
    List<Admin> findAllAdmins();

    //新增管理员
    int insAdmin(Admin admin);

    //修改管理员用户信息
    int updAdmin(Admin admin);

    //删除管理员信息
    int delAdmin(Integer adminId);
}
