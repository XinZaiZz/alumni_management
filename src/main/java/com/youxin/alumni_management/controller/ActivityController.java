package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.youxin.alumni_management.pojo.Activity;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.ActivityService;
import com.youxin.alumni_management.utils.BuildArticleTabloidUtil;
import com.youxin.alumni_management.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author youxin
 * @program alumni_management
 * @description 校友活动控制层
 * @date 2022-04-20 22:15
 */
@Controller
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    private final DateUtil dateUtil;

    @GetMapping("/toActivityPage")
    public String toShowActivityPage(Model model) {
        //查询所有活动
        List<Activity> activities = activityService.selAllActivity();
        model.addAttribute("activityList", activities);
        return "activity/activity";
    }

    @GetMapping("/activity/toActivityWritePage")
    public String toActivityWritePage() {
        return "activity/activity_write";
    }

    @SneakyThrows
    @PostMapping("/activity/publishActivity")
    @ResponseBody
    public String insActivity(HttpServletRequest request, Activity activity, MultipartFile uploadFile) {
        HashMap<String, Object> mapStatus = new HashMap<>();
        //每次添加前先对map清空操作
        mapStatus.clear();
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            mapStatus.put("status", 2);
            mapStatus.put("message", "客官您还未登录呢♡o(╥﹏╥)o ♥♡");
        } else {
            //获得文章html代码并生成摘要
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String forumHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
            //设置文章摘要
            activity.setActivityTabloid(forumHtmlContent + "...");
            activity.setUserId(currentUser.getUserId());
            activity.setDepartmentId(currentUser.getDepartmentId());
            activity.setActivityContent(request.getParameter("articleContent"));
            activity.setActivityHTMLContent(request.getParameter("articleHtmlContent"));
            activity.setActivityTitle(request.getParameter("articleTitle"));
            activity.setActivityDate(dateUtil.getSystemDateToString());
//            System.out.println(request.getParameter("uploadFile"));
            int data = -1;
            //上传文件不为空时才进行上传操作
            if (uploadFile != null) {
                //获取文件名
                String filename = uploadFile.getOriginalFilename();


                //获取文件格式
                assert filename != null;
                //获取文件名
                String prefix = filename.substring(0, filename.lastIndexOf("."));
                //获取文件类型
                String suffix = filename.substring(filename.lastIndexOf("."));
                //模拟存储路径
                String realPath = "F:/Test/activity files";
                String uuid = UUID.randomUUID().toString();
                //服务器保存路径为files目录下的uuid加suffix
                String filePath = realPath + "/" + prefix + uuid + suffix;
                System.out.println(filePath);
                activity.setActivityFileName(prefix + uuid + suffix);

                data = activityService.insActivity(activity);
                if (data > 0) {
                    //保存文件
                    FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(filePath));
                    mapStatus.put("status", 1);
                    mapStatus.put("message", "发布成功哦\\\\*^o^*//");
                } else {
                    //插入失败
                    mapStatus.put("status", 0);
                    mapStatus.put("message", "发布失败，客官检查网络或者联系管理员解决哦๐·°(৹˃̵﹏˂̵৹)°·๐");
                }
            } else {//上传文件为空就将文件名属性置为空，并进行插入操作
                activity.setActivityFileName("");
                data = activityService.insActivity(activity);
                if (data > 0) {
                    mapStatus.put("status", 1);
                    mapStatus.put("message", "发布成功哦\\\\*^o^*//，静静等待管理员审核吧！");
                } else {
                    //插入失败
                    mapStatus.put("status", 0);
                    mapStatus.put("message", "发布失败，客官检查网络或者联系管理员解决哦๐·°(৹˃̵﹏˂̵৹)°·๐");
                }
            }
        }
        return JSON.toJSONString(mapStatus);
    }

    @GetMapping("/activity/show/{activityId}")
    public String selActivityById(@PathVariable("activityId") Integer activityId, Model model) {
        //根据活动id查找活动
        Activity activity = activityService.selActivityById(activityId);
        //如果查询到了活动内容，返回活动内容页
        if (activity != null) {
            model.addAttribute("activity", activity);
            return "activity/show_activity";
        }
        //如果没有查询到活动内容就返回500错误页面
        else return "500";
    }

    @GetMapping("/downloadFile/{activityFileName}")
    @SneakyThrows
    @ResponseBody
    public void downloadActivityFile(@PathVariable("activityFileName") String activityFileName, HttpServletResponse response) {
        //设置响应流中文件下载
        response.setHeader("Content-Disposition","attachment;filename="+activityFileName);
        //把二进制流放入到响应体中.
        ServletOutputStream os  = null;
        os = response.getOutputStream();
//        String path = request.getServletContext().getRealPath("files");
        //模拟存储路径
        String path = "F:/Test/activity files";

//        String path = "/www/wwwroot/mytest/files";
//        System.out.println(path);

        String prefix = activityFileName.substring(0, activityFileName.lastIndexOf("."));
        //获取文件类型
        String suffix = activityFileName.substring(activityFileName.lastIndexOf("."));
        //最后保存的文件名,从下标为0开始到uuid截止为原来的文件名
        String fileName = prefix.substring(0, prefix.length() - 36);
        //创建文件对象
        File file  =  new File(path, fileName + suffix);

        File file1 = new File(path, activityFileName);
        byte[]  bytes  = new byte[1024];
        bytes = FileUtils.readFileToByteArray(file1);
        //写入
        os.write(bytes);
        //刷新容器
        os.flush();
        os.close();
    }
}
