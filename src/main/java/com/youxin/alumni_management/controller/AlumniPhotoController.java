package com.youxin.alumni_management.controller;

import com.alibaba.fastjson.JSON;
import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.AlumniPhoto;
import com.youxin.alumni_management.service.AlumniPhotoService;
import com.youxin.alumni_management.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
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
 * @description 照片墙控制层
 * @date 2022-05-02 13:08
 */
@Controller
@RequiredArgsConstructor
public class AlumniPhotoController {

    private final DateUtil dateUtil;

    private final AlumniPhotoService alumniPhotoService;

    @GetMapping("/toPhotoPage")
    public String toPhotoPage(Model model) {
        List<AlumniPhoto> top12AlumniPhotos = alumniPhotoService.findTop12AlumniPhotos();
        model.addAttribute("alumniPhotos", top12AlumniPhotos);
        return "alumni_show/alumni_show_photo";
    }//笨比廖友鑫

    @GetMapping("/toAlumniPhotoWritePage")
    public String toPhotoWritePage(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        //如果管理员用户已经登录
        return "alumni_show/alumni_show_write";
    }

    @PostMapping("/publishAlumniPhoto")
    @ResponseBody
    @SneakyThrows
    public String publishAlumniPhoto(MultipartFile uploadImage, AlumniPhoto alumniPhoto, HttpServletRequest request) {
        HashMap<String, Object> mapStatus = new HashMap<>();
        //获取当前登录管理员用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        alumniPhoto.setAdminId(admin.getAdminId());
        alumniPhoto.setDepartmentId(admin.getDepartmentId());
        alumniPhoto.setPhotoContent(request.getParameter("articleContent"));
        alumniPhoto.setPhotoHTMLContent(request.getParameter("articleHtmlContent"));
        alumniPhoto.setPhotoTitle(request.getParameter("articleTitle"));
        alumniPhoto.setPhotoDate(dateUtil.getSystemDateToString());
        //插入记录
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
            String realPath = "F:/Test/alumniPhoto images";
            String uuid = UUID.randomUUID().toString();
            //服务器保存路径为files目录下的uuid加suffix
            String filePath = realPath + "/" + prefix + uuid + suffix;
//                    System.out.println(filePath);
            //如果封面图不为空，则将封面图路径设置到alumniHelp对象中
            alumniPhoto.setPhotoImageName(prefix + uuid + suffix);
            //保存文件
            FileUtils.copyInputStreamToFile(uploadImage.getInputStream(), new File(filePath));
            data = alumniPhotoService.insAlumniPhoto(alumniPhoto);
        }else {
            //为空则置为空
            alumniPhoto.setPhotoImageName("");
            data = alumniPhotoService.insAlumniPhotoNoImage(alumniPhoto);
        }

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

    @GetMapping("/toAlumniShowPage/{photoId}")
    public String toAlumniShowPage(@PathVariable("photoId") Integer photoId, Model model) {
        AlumniPhoto alumniPhoto = alumniPhotoService.findAlumniPhotoById(photoId);
        if (alumniPhoto != null) {
            model.addAttribute("alumniPhoto", alumniPhoto);
            return "alumni_show/alumni_show_show";
        }else {
            return "500";
        }
    }
}
