package com.youxin.alumni_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youxin
 * @program alumni_management
 * @description 新闻类型文章实体类
 * @date 2022-03-27 16:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsArticle {

    //文章id
    private Integer newArticleId;

    //编写管理员id
    private Integer adminId;

    //文章所属学院id
    private Integer departmentId;

    //新闻类型文章内容
    private String newArticleContent;

    //新闻类型文章的HTML格式内容
    private String newArticleHTMLContent;

    //新闻类型文章标题
    private String newArticleTitle;

    //新闻类型的文章摘要
    private String newArticleTabloid;

    //发布时间
    private String newArticleDate;
}
