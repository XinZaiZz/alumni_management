package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.Forum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumMapper {

    //新增论坛文章
    int insForum(Forum forum);

    //查询所有论坛文章
    List<Forum> findAllForum();

    //根据文章id查询论坛内容
    Forum findForumById(Integer forumId);

    //查询前8条讨论标题
    List<Forum> findTop8Forum();

    //查询所属学院论坛
    List<Forum> findAllForumByDepartmentId(Integer departmentId);

    //查询当前用户发布的论坛
    List<Forum> findAllForumByUserId(Integer userId);
}
