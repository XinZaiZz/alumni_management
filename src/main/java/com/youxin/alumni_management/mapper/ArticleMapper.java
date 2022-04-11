package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.NewsArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //插入新闻类文章
    int insNewsArticle(NewsArticle newsArticle);

    //查询所有新闻文章
    List<NewsArticle> findAllNewsArticle();

    //通过Id查找新闻文章
    NewsArticle findArticleById(Integer newArticleId);
}
