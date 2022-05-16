package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.ForumMapper;
import com.youxin.alumni_management.pojo.Forum;
import com.youxin.alumni_management.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 论坛功能服务层实现类
 * @date 2022-04-05 12:53
 */
@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

//    @Autowired
    private final ForumMapper forumMapper;

    @Override
    public int insForum(Forum forum) {
        return forumMapper.insForum(forum);
    }

    @Override
    public List<Forum> findAllForum() {
        return forumMapper.findAllForum();
    }

    @Override
    public Forum findForumById(Integer forumId) {
        return forumMapper.findForumById(forumId);
    }

    @Override
    public List<Forum> findTop8Forum() {
        return forumMapper.findTop8Forum();
    }

    @Override
    public List<Forum> findAllForumByDepartmentId(Integer departmentId) {
        return forumMapper.findAllForumByDepartmentId(departmentId);
    }

    @Override
    public List<Forum> findAllForumByUserId(Integer userId) {
        return forumMapper.findAllForumByUserId(userId);
    }
}
