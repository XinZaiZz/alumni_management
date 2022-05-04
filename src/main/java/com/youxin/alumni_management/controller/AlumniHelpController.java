package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.AlumniHelp;
import com.youxin.alumni_management.service.AlumniHelpService;
import com.youxin.alumni_management.utils.BuildArticleTabloidUtil;
import com.youxin.alumni_management.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author youxin
 * @program alumni_management
 * @description 校友帮扶模块controller
 * @date 2022-04-29 15:21
 */

@Controller
@RequestMapping("/help")
@RequiredArgsConstructor
public class AlumniHelpController {

    private final DateUtil dateUtil;

    private final AlumniHelpService alumniHelpService;

    @GetMapping("/toAlumniHelp")
    public String toAlumniHelp(Model model) {
        List<AlumniHelp> alumniHelps = alumniHelpService.findAllAlumniHelp();
        model.addAttribute("alumniHelps", alumniHelps);
        return "alumni_help/alumni_help";
    }

    @GetMapping("/toAlumniHelpWritePage")
    public String toAlumniHelpWritePage(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        return "alumni_help/alumni_help_write";
    }

    @PostMapping("/publishAlumniHelp")
    @SneakyThrows
    @ResponseBody
    public String publishAlumniHelp(MultipartFile uploadFile, MultipartFile uploadImage, HttpServletRequest request, AlumniHelp alumniHelp) {
        HashMap<String, Object> mapStatus = new HashMap<>();

        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            mapStatus.put("status", 2);
            mapStatus.put("message", "客官您还未登录呢♡o(╥﹏╥)o ♥♡");
        }
        else {
            //获得文章html代码并生成摘要
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String alumniHelpHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
            //设置摘要
            alumniHelp.setHelpTabloid(alumniHelpHtmlContent + "...");
            alumniHelp.setAdminId(admin.getAdminId());
            alumniHelp.setDepartmentId(admin.getDepartmentId());
            alumniHelp.setHelpContent(request.getParameter("articleContent"));
            alumniHelp.setHelpHTMLContent(request.getParameter("articleHtmlContent"));
            alumniHelp.setHelpTitle(request.getParameter("articleTitle"));
            alumniHelp.setHelpDate(dateUtil.getSystemDateToString());
            //插入标识
            int data = -1;
            //上传文件不为空时才进行上传操作
            if (uploadFile != null) {
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
                    String realPath = "F:/Test/alumniHelp images";
                    String uuid = UUID.randomUUID().toString();
                    //服务器保存路径为files目录下的uuid加suffix
                    String filePath = realPath + "/" + prefix + uuid + suffix;
//                    System.out.println(filePath);
                    //如果封面图不为空，则将封面图路径设置到alumniHelp对象中
                    alumniHelp.setHelpHeadImage(prefix + uuid + suffix);
                    //保存文件
                    FileUtils.copyInputStreamToFile(uploadImage.getInputStream(), new File(filePath));
                }else {
                    //为空则置为空
                    alumniHelp.setHelpHeadImage("");
                }
                //获取文件名
                String filename = uploadFile.getOriginalFilename();
                //获取文件格式
                assert filename != null;
                //获取文件名
                String prefix = filename.substring(0, filename.lastIndexOf("."));
                //获取文件类型
                String suffix = filename.substring(filename.lastIndexOf("."));
                //模拟存储路径
                String realPath = "F:/Test/alumniHelp files";
                String uuid = UUID.randomUUID().toString();
                //服务器保存路径为files目录下的uuid加suffix
                String filePath = realPath + "/" + prefix + uuid + suffix;
//                System.out.println(filePath);
                alumniHelp.setHelpFileName(prefix + uuid + suffix);
                data = alumniHelpService.insAlumniHelp(alumniHelp);
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
                alumniHelp.setHelpFileName("");
                data = alumniHelpService.insAlumniHelp(alumniHelp);
                if (data > 0) {
                    mapStatus.put("status", 1);
                    mapStatus.put("message", "发布成功哦\\\\*^o^*//");
                } else {
                    //插入失败
                    mapStatus.put("status", 0);
                    mapStatus.put("message", "发布失败，客官检查网络或者联系管理员解决哦๐·°(৹˃̵﹏˂̵৹)°·๐");
                }
            }
        }
        return JSON.toJSONString(mapStatus);
    }

    @GetMapping("/alumniHelp/show/{helpId}")
    public String selAlumniHelpById(@PathVariable("helpId") Integer helpId, Model model) {
        AlumniHelp alumniHelp = alumniHelpService.selAlumniHelpById(helpId);
        if (alumniHelp != null) {
            model.addAttribute("alumniHelp", alumniHelp);
            return "alumni_help/show_alumni_help";
        }else return "500";
    }

    @GetMapping("/downloadFile/{helpFileName}")
    @SneakyThrows
    public void downloadFile(@PathVariable("helpFileName") String helpFileName, HttpServletResponse response) {
        //设置响应流中文件下载
        response.setHeader("Content-Disposition","attachment;filename=" + helpFileName);
        //把二进制流放入到响应体中.
        ServletOutputStream os  = null;
        os = response.getOutputStream();
//        String path = request.getServletContext().getRealPath("files");
        //模拟存储路径
        String path = "F:/Test/alumniHelp files";

//        String path = "/www/wwwroot/mytest/files";
//        System.out.println(path);

        byte[]  bytes  = new byte[1024];
        bytes = FileUtils.readFileToByteArray(new File(path, helpFileName));
        //写入
        os.write(bytes);
        //刷新容器
        os.flush();
        os.close();
    }

}
