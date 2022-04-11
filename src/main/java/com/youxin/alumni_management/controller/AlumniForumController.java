package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.youxin.alumni_management.pojo.Forum;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.ForumService;
import com.youxin.alumni_management.utils.BuildArticleTabloidUtil;
import com.youxin.alumni_management.utils.DateUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 校友论坛控制层
 * @date 2022-04-04 23:01
 */
@Controller
public class AlumniForumController {

    @Autowired
    DateUtil dateUtil;

    @Autowired
    ForumService forumService;


    //跳转校友论坛页面
    @GetMapping("/toAlumniForum")
    public String toAlumniForumPage(HttpServletRequest request) {
        //查询所有论坛文章
        List<Forum> allForum = forumService.findAllForum();
        request.setAttribute("forumList", allForum);
        return "forum/alumni_forum";
    }

    @GetMapping("/toWriteForumPage")
    public String toWriteForumPage() {
        return "forum/forum_write";
    }

    @PostMapping("/publishForum")
    @ResponseBody
    public String publishForum(HttpServletRequest request, Forum forum) {
        HashMap<String, Object> mapStatus = new HashMap<>();
        //获得文章html代码并生成摘要
        BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
        String forumHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
        //设置文章摘要
        forum.setForumTabloid(forumHtmlContent + "...");
        //获取当前登录信息
        User currentUser = (User)request.getSession().getAttribute("user");
        //如果用户未登录
        if (currentUser == null) {
            mapStatus.clear();
            mapStatus.put("status", 2);
            mapStatus.put("message", "客官您还未登录呢♡o(╥﹏╥)o ♥♡");
            return JSON.toJSONString(mapStatus);
        }
        //设置文章属性
        forum.setUserId(currentUser.getUserId());
        forum.setDepartmentId(currentUser.getDepartmentId());
        forum.setForumContent(request.getParameter("articleContent"));
        forum.setForumHTMLContent(request.getParameter("articleHtmlContent"));
        forum.setForumTitle(request.getParameter("articleTitle"));
        //用dateUtil工具类设置系统时间
        forum.setForumDate(dateUtil.getSystemDateToString());
//        System.out.println(newsArticle);

        //插入状态
        int data = -1;
        mapStatus.clear();
        if ((data = forumService.insForum(forum)) > 0 && currentUser != null) {
            mapStatus.clear();
            mapStatus.put("status", 1);
            mapStatus.put("message", "发布成功哦\\\\*^o^*//");
        }else {
            mapStatus.clear();
            mapStatus.put("status", 0);
            mapStatus.put("message", "发布失败，客官检查网络或者练习管理员解决哦๐·°(৹˃̵﹏˂̵৹)°·๐");
        }
        return JSON.toJSONString(mapStatus);
    }

}
