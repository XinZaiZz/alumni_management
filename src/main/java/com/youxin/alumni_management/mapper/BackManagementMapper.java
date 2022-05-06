package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.*;
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

    //管理员新增用户
    int insUserByAdmin(User user);

    //管理员修改用户基本信息
    int updUserByAdmin(User user);

    //管理员删除用户
    int delUserByAdmin(Integer userId);

    //查询管理员发布的新闻文章
    List<NewsArticle> findAllNewsArticleByAdminId(Integer adminId);

    //管理员删除新闻文章
    int delNewsArticleByNewArticleId(Integer newArticleId);

    //查询管理员所属学院的活动文章
    List<Activity> findAllActivityByAdminDepartmentId(Integer departmentId);

    //删除管理员学院活动文章
    int delActivityByActivityId(Integer activityId);

    //查询所属学院所有论坛文章
    List<Forum> findAllForumByAdminDepartmentId(Integer departmentId);

    //删除论坛文章
    int delForumByForumId(Integer forumId);

    //查询当前管理员发布所有帮扶文章
    List<AlumniHelp> findAllAlumniHelpByAdminId(Integer adminId);

    //删除帮扶文章
    int delAlumniHelpByAlumniHelpId(Integer helpId);

    //获取管理员学院所有校友展示文章
    List<AlumniPhoto> findAllAlumniPhotoByAdminDepartmentId(Integer departmentId);

    //删除校友展示文章
    int delAlumniPhotoByPhotoId(Integer photoId);

    //更新校友展示文章基本信息
    int updAlumniPhoto(AlumniPhoto alumniPhoto);
}
