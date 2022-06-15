package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.NewsArticle;
import com.youxin.alumni_management.service.ArticleService;
import com.youxin.alumni_management.utils.BuildArticleTabloidUtil;
import com.youxin.alumni_management.utils.DateUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    //新闻文件存储路径
    @Value("${addr.new-article-image}")
    private String newArticleImage;

    @GetMapping("/toWritePage")
    public String toWritePage(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        return "article/write";
    }

    @PostMapping("/publishArticle")
    @ResponseBody
    @SneakyThrows
    public String publishNewsArticle(MultipartFile uploadImage, HttpServletRequest request, NewsArticle newsArticle) {
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
        HashMap<String, Object> mapStatus = new HashMap<>();
        //插入状态
        int data = -1;

        //当上传图片不为空时才进行上传操作
        if (uploadImage != null) {
            //获取文件名
            String imageFilename = uploadImage.getOriginalFilename();
            //获取文件格式
            assert imageFilename != null;
            //获取文件名
            String prefix = imageFilename.substring(0, imageFilename.lastIndexOf("."));
            //获取文件类型
            String suffix = imageFilename.substring(imageFilename.lastIndexOf("."));
            //模拟存储路径
            String realPath = newArticleImage;
            String uuid = UUID.randomUUID().toString();
            //服务器保存路径为files目录下的uuid加suffix
            String filePath = realPath + "/" + prefix + uuid + suffix;
//                    System.out.println(filePath);
            //如果封面图不为空，则将封面图路径设置到alumniHelp对象中
            newsArticle.setNewImageName(prefix + uuid + suffix);
            //保存文件
            FileUtils.copyInputStreamToFile(uploadImage.getInputStream(), new File(filePath));
        }else {
            //为空则置为空
            newsArticle.setNewImageName("");
        }
        data = articleService.insNewsArticle(newsArticle);
        if (data > 0) {
            mapStatus.put("status", 1);
            mapStatus.put("message", "发布成功哦\\\\*^o^*//");
        } else {
            //插入失败
            mapStatus.put("status", 0);
            mapStatus.put("message", "发布失败，客官检查网络或者联系管理员解决哦๐·°(৹˃̵﹏˂̵৹)°·๐");
        }
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
