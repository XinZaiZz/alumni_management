package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youxin.alumni_management.pojo.Comment;
import com.youxin.alumni_management.pojo.Forum;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.ForumService;
import com.youxin.alumni_management.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 校友论坛控制层
 * @date 2022-04-04 23:01
 */
@Controller
@RequiredArgsConstructor
public class AlumniForumController {

    //获取logger对象
    Logger logger = Logger.getLogger(this.getClass());

    //获取ObjectMapper  json对象
    ObjectMapper objectMapper = new ObjectMapper();

    private final RedisUtils redisUtils;

//    @Autowired
    private final DateUtil dateUtil;

//    @Autowired
    private final ForumService forumService;


    //跳转校友论坛页面
    @GetMapping("/toAlumniForum")
    public String toAlumniForumPage(Model model) {
        //查询所有论坛文章
        List<Forum> allForum = forumService.findAllForum();
        model.addAttribute("forumList", allForum);
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

    @GetMapping("/forum/show/{forumId}")
    public String findForumById(@PathVariable("forumId") Integer forumId, Model model) {
        Forum forum_article = forumService.findForumById(forumId);
        if (forum_article != null) {
            //传递论坛文章
            model.addAttribute("forum_article", forum_article);
            return "forum/show_alumni";
        }
        return "500";
    }

    @PostMapping(value = "/forum/saveComment", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object saveComment(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        //设置跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        Comment comment = new Comment();
        try {
            comment.setForumId(Integer.parseInt(request.getParameter("forumId")));
            comment.setCommentUserId(Integer.parseInt(request.getParameter("userId")));
        }catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
        }
        comment.setCommentNickName(request.getParameter("userNickName"));
        comment.setCommentContent(request.getParameter("commentContent"));
        comment.setCommentUserImgUri(request.getParameter("commentUserImgUri"));
        comment.setCommentDate(dateUtil.getSystemDateToString());
//        System.out.println("comment-->" + comment.toString());
//        System.out.println("id-->" + comment.getForumId().toString());

        //将当前文章Id作为key,文章类作为value存储进redis
        redisUtils.lSet(comment.getForumId().toString(), objectMapper.writeValueAsString(comment));
        //取出当前添加的文章Id作为key的评论类,取出后为字符串类型，先要转为Comment才可以
        //获取list的size
        long getListSize = redisUtils.lGetListSize(comment.getForumId().toString());
        //获取最后一个元素，即刚才插入的元素
        Object commentObject = redisUtils.lGetIndex(comment.getForumId().toString(), getListSize - 1);
        //将获取的json转换为comment对象
        Comment jsonToComment = objectMapper.readValue((String) commentObject, Comment.class);
        //日志记录
        logger.info("用户" +
                jsonToComment.getCommentUserId() + "对id为" +
                jsonToComment.getForumId() + "的文章发布评论-->" +
                jsonToComment.getCommentContent());
//        System.out.println(Result.success(ResultCode.SUCCESS, jsonToComment));
        //返回json对象，其中为什么是json对象，因为在Result工具类中进行了json转换
        return Result.success(ResultCode.SUCCESS, jsonToComment);
    }

    //查询所有评论
    @GetMapping("/forum/findAllForums/{forumId}")
    @SneakyThrows
    @ResponseBody
    public Object findAllForums(@PathVariable("forumId") Integer forumId) {
        //取出所有文章Id作为key的评论类,取出后为字符串类型，先要转为Comment才可以
        List<Object> commonList = redisUtils.lGet(forumId.toString(), 0, -1);
        //将json字符串转为对象
        Comment jsonToComments;
        List<Comment> commentListJsons = new ArrayList<>();
        for (Object o : commonList) {
            jsonToComments = objectMapper.readValue((String) o, Comment.class);
            //将comment对象放入链表中
            commentListJsons.add(jsonToComments);
        }
//                System.out.println(commonList);
//        System.out.println(Result.success(ResultCode.SUCCESS, commentListJsons));
        return Result.success(ResultCode.SUCCESS, commentListJsons);
    }

}
