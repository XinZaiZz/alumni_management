package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.NewsArticle;

import java.util.List;

public interface ArticleService {

    int insNewsArticle(NewsArticle newsArticle);

    List<NewsArticle> findAllNewsArticle();

    NewsArticle findArticleById(Integer newArticleId);

    List<NewsArticle> findTop3Article();
}
