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
}
