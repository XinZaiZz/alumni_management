package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.*;

import java.util.List;

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

    List<Activity> findAllActivityByAdminDepartmentId(Integer departmentId);

    int delActivityByActivityId(Integer activityId);

    List<Forum> findAllForumByAdminDepartmentId(Integer departmentId);

    int delForumByForumId(Integer forumId);

    List<AlumniHelp> findAllAlumniHelpByAdminId(Integer adminId);

    int delAlumniHelpByHelpId(Integer helpId);

    //获取管理员学院所有校友展示文章
    List<AlumniPhoto> findAllAlumniPhotoByAdminDepartmentId(Integer departmentId);

    //删除校友展示文章
    int delAlumniPhotoByPhotoId(Integer photoId);

    //更新校友展示文章基本信息
    int updAlumniPhoto(AlumniPhoto alumniPhoto);
}
