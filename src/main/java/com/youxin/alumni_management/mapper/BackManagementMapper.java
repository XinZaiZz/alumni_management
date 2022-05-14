package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

    //查询所有未发布的新闻
    List<NewsArticle> findAllExamineNewArticles();

    //修改新闻发布状态
    int updNewsArticleStatus(Integer newArticleId, Integer status);

    //查询申请中和申请未通过的所有活动文章
    List<Activity> findAllExamineActivityByAdminDepartmentId(Integer departmentId);

    //查询二级学院已经通过但还没有发布的活动文章
    List<Activity> findAllExamineActivityByPZHUAdmin();

    //改变活动文章状态
    int updActivityStatus(Integer activityId, Integer status);

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

    //查询所有未发布的帮扶文章
    List<AlumniHelp> findAllExamineAlumniHelp();

    //改变帮扶发布状态
    int updAlumniHelpStatus(Integer helpId, Integer status);

    //获取管理员学院所有校友展示文章
    List<AlumniPhoto> findAllAlumniPhotoByAdminDepartmentId(Integer departmentId);

    //删除校友展示文章
    int delAlumniPhotoByPhotoId(Integer photoId);

    //更新校友展示文章基本信息
    int updAlumniPhoto(AlumniPhoto alumniPhoto);

    //查询未发布的校友风采
    List<AlumniPhoto> findAllExamineAlumniPhoto();

    //修改风采展示状态
    int updAlumniPhotoStatus(Integer photoId, Integer status);

    //查询所属学院人数
    int selUserCount(Integer departmentId);

    //查询学院男性人数
    int selMaleCount(Integer departmentId);

    //查询职业类别和人数
    @MapKey("occupationGroupCount")
    List<Map<String, Object>> countByOccupationGroup(Integer departmentId);

    //查询专业类别和人数
    @MapKey("majorUserCount")
    List<Map<String, Object>> countByMajorGroup(Integer departmentId);

    //查询学院年级人数分布
    @MapKey("majorUserCount")
    List<Map<String, Object>> countByGrade(Integer departmentId);

    //查询所属学院各年龄段人数
    @MapKey("ageGroup")
    List<Map<String, Object>> countByAge(Integer departmentId);

    //根据辅导员姓名分组学院人数
    @MapKey("")
    List<Map<String, Object>> countByInstructorName(Integer departmentId);
}
