package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.ArticleMapper;
import com.youxin.alumni_management.pojo.NewsArticle;
import com.youxin.alumni_management.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 文章服务实现类
 * @date 2022-03-27 17:35
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public int insNewsArticle(NewsArticle newsArticle) {
        return articleMapper.insNewsArticle(newsArticle);
    }

    @Override
    public List<NewsArticle> findAllNewsArticle() {
        return articleMapper.findAllNewsArticle();
    }

    @Override
    public NewsArticle findArticleById(Integer newArticleId) {
        return articleMapper.findArticleById(newArticleId);
    }
}
