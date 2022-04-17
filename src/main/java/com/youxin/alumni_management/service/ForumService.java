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
}
