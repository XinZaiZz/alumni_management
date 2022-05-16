package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.Forum;

import java.util.List;

public interface ForumService {

    //插入论坛文章
    int insForum(Forum forum);

    //查询所有论坛文章
    List<Forum> findAllForum();

    //根据文章id查询文章
    Forum findForumById(Integer forumId);

    List<Forum> findTop8Forum();

    //查询所属学院论坛
    List<Forum> findAllForumByDepartmentId(Integer departmentId);

    //查询当前用户发布的论坛
    List<Forum> findAllForumByUserId(Integer userId);
}
