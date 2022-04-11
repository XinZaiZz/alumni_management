package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.ForumMapper;
import com.youxin.alumni_management.pojo.Forum;
import com.youxin.alumni_management.service.ForumService;
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
public class ForumServiceImpl implements ForumService {

    @Autowired
    ForumMapper forumMapper;

    @Override
    public int insForum(Forum forum) {
        return forumMapper.insForum(forum);
    }

    @Override
    public List<Forum> findAllForum() {
        return forumMapper.findAllForum();
    }
}
