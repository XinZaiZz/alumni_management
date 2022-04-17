package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.NewsArticle;
import com.youxin.alumni_management.service.ArticleService;
import com.youxin.alumni_management.utils.BuildArticleTabloidUtil;
import com.youxin.alumni_management.utils.DateUtil;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 文章控制类
 * @date 2022-03-27 16:08
 */

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    DateUtil dateUtil;

    @GetMapping("/toWritePage")
    public String toWritePage() {
        return "article/write";
    }

    @PostMapping("/publishArticle/newsArticle")
    @ResponseBody
    public String publishNewsArticle(HttpServletRequest request, NewsArticle newsArticle) {
        //获得文章html代码并生成摘要
        BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
        String articleHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
        //设置文章摘要
        newsArticle.setNewArticleTabloid(articleHtmlContent + "...");

        Admin admin = (Admin) request.getSession().getAttribute("admin");
//        System.out.println(admin);
        newsArticle.setNewArticleHTMLContent(request.getParameter("articleHtmlContent"));
        newsArticle.setAdminId(admin.getAdminId());
        newsArticle.setDepartmentId(admin.getDepartmentId());
        newsArticle.setNewArticleContent(request.getParameter("articleContent"));
        newsArticle.setNewArticleTitle(request.getParameter("articleTitle"));
        //用dateUtil工具类设置系统时间
        newsArticle.setNewArticleDate(dateUtil.getSystemDateToString());
//        System.out.println(newsArticle);
        HashMap<String, Integer> mapStatus = new HashMap<>();
        //插入状态
        int data = -1;
        //如果保存成功
        if ((data = articleService.insNewsArticle(newsArticle)) > 0) {
            //清空状态map
            mapStatus.clear();
            //状态：1成功
            mapStatus.put("status", 1);
        }else {
            //清空状态map
            mapStatus.clear();
            //状态：0失败
            mapStatus.put("status", 0);
        }
        //将map对象转化为json对象并返回
        return JSON.toJSONString(mapStatus);
    }


    @GetMapping("/findAllNewsArticle")
    @ResponseBody
    public String findAllNewsArticle(HttpServletRequest request) throws JsonProcessingException {
        //将List转为json对象
        //创建jackson的核心对象:ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        List<NewsArticle> newsArticleList;
        newsArticleList = articleService.findAllNewsArticle();
//        HashMap<String, Object> mapStatus = new HashMap<>();
//        if (articleList != null){
//            mapStatus.clear();
//            //如果查询数据为空
//            if (articleList.isEmpty()) {
//                mapStatus.put("flag", false);
//            }
//            mapStatus.put("flag", true);
//            //继续将查询状态和查询值传入
//            mapStatus.put("status", 1);
//            mapStatus.put("articleList", articleList);
//        }else {
//            mapStatus.clear();
//            mapStatus.put("flag", false);
//            mapStatus.put("status", 0);
//            mapStatus.put("articleList", articleList);
//        }
        String result = objectMapper.writeValueAsString(newsArticleList);
//        request.setAttribute("newsArticleList", newsArticleList);
        return result;
    }

    @GetMapping("/article/show/{id}")
    public String findArticleById(@PathVariable("id") Integer newArticleId, Model model) {
        NewsArticle currentArticle  = articleService.findArticleById(newArticleId);
        model.addAttribute("currentArticle", currentArticle);
        return "article/show_article";
    }


}
