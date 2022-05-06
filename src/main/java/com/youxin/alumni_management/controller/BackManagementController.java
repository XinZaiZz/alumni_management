package com.youxin.alumni_management.controller;

import com.sun.xml.internal.stream.StaxErrorReporter;
import com.youxin.alumni_management.pojo.*;
import com.youxin.alumni_management.service.ArticleService;
import com.youxin.alumni_management.service.BackManagementService;
import com.youxin.alumni_management.service.RegisterUserService;
import com.youxin.alumni_management.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author youxin
 * @program alumni_management
 * @description 后台管理controller
 * @date 2022-02-08 23:49
 */
@Controller
@RequiredArgsConstructor
public class BackManagementController {

//    @Autowired
    private final RegisterUserService registerUserService;

    private final BackManagementService backManagementService;

    private final DateUtil dateUtil;

//    private final ArticleService articleService;

    @GetMapping("/dashboard")
    public String toDashboard(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        return "/back_management/dashboard";
    }

    @GetMapping("/toHomePage")
    public String toHomePage(HttpServletRequest request) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        return "back_management/home";
    }

    @GetMapping("/toUserInfoPage")
    public String toUserInfoPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<User> allUser = backManagementService.findAllUserByAdminDepartmentId(admin.getDepartmentId());
        if (allUser != null) {
            model.addAttribute("allUser", allUser);
            return "back_management/user_info";
        }else
            return "500";
    }

    @GetMapping("/toNewsManagementPage")
    public String toNewsManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<NewsArticle> allNewsArticle = backManagementService.findAllNewsArticleByAdminId(admin.getAdminId());
        if (allNewsArticle != null) {
            model.addAttribute("allNewsArticle", allNewsArticle);
            return "back_management/news_management";
        }else
            return "500";
    }

    @PostMapping("/insUserByAdmin")
    public String insUserByAdmin(HttpServletRequest request) {
        User insUser = new User();
        insUser.setUserName(request.getParameter("userName"));
        insUser.setPassword(request.getParameter("password"));
        insUser.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
        insUser.setNickName(request.getParameter("nickName"));
        int data = -1;
        if ((data = backManagementService.insUserByAdmin(insUser)) > 0) {
            //新增成功
            return "redirect:/toUserInfoPage";
        }
        return "500";
    }

    @PostMapping("/updUserByAdmin")
    public String updUserByAdmin(HttpServletRequest request) {
        User updUser = new User();
        updUser.setUserId(Integer.parseInt(request.getParameter("userId")));
        updUser.setUserName(request.getParameter("userName"));
        updUser.setPassword(request.getParameter("password"));
        updUser.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
        updUser.setNickName(request.getParameter("nickName"));
        int data = -1;
        if ((data = backManagementService.updUserByAdmin(updUser)) > 0) {
            //修改成功
            return "redirect:/toUserInfoPage";
        }
        return "500";
    }

    @PostMapping("/delUserByAdmin")
    public String delUserByAdmin(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int data = -1;
        if ((data = backManagementService.delUserByAdmin(userId)) > 0) {
            //删除成功
            return "redirect:/toUserInfoPage";
        }
        return "500";
    }

    @PostMapping("/delNewsArticleByAdmin")
    public String delNewsArticleByAdmin(HttpServletRequest request) {
        int newArticleId = Integer.parseInt(request.getParameter("newArticleId"));
        int data = -1;
        if ((data = backManagementService.delNewsArticleByNewArticleId(newArticleId)) > 0) {
            //删除成功
            return "redirect:/toNewsManagementPage";
        }
        return "500";
    }

    @GetMapping("/toArtivityManagementPage")
    public String toArtivityManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<Activity> allActivity = backManagementService.findAllActivityByAdminDepartmentId(admin.getDepartmentId());
        System.out.println(allActivity);
        if (allActivity != null) {
            model.addAttribute("allActivity", allActivity);
            return "back_management/activity_management";
        }
        return "500";
    }

    @PostMapping("/delActivityByActivityId")
    public String delActivityByActivityId(HttpServletRequest request) {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int data = -1;
        if (( data = backManagementService.delActivityByActivityId(activityId) ) > 0) {
            //删除成功
            return "redirect:/toArtivityManagementPage";
        }
        return "500";
    }

    @GetMapping("/toForumManagementPage")
    public String toForumManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<Forum> allForum = backManagementService.findAllForumByAdminDepartmentId(admin.getDepartmentId());
        if (allForum != null) {
            model.addAttribute("allForum", allForum);
            return "back_management/forum_management";
        }
        else
            return "500";
    }

    @PostMapping("/delForumByForumId")
    public String delForumByForumId(HttpServletRequest request) {
        int forumId = Integer.parseInt(request.getParameter("forumId"));
        int data = -1;
        if ((data = backManagementService.delForumByForumId(forumId)) > 0) {
            return "redirect:/toForumManagementPage";
        }
        return "500";
    }

    @GetMapping("/toAlumniHelpManagementPage")
    public String toAlumniHelpManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<AlumniHelp> allAlumniHelp = backManagementService.findAllAlumniHelpByAdminId(admin.getAdminId());
        if (allAlumniHelp != null) {
            model.addAttribute("allAlumniHelp", allAlumniHelp);
            return "back_management/alumni_help_management";
        }
        else
            return "500";
    }

    @PostMapping("/delAlumniHelpByHelpId")
    public String delAlumniHelpByHelpId(HttpServletRequest request) {
        int helpId = Integer.parseInt(request.getParameter("helpId"));
        int data = -1;
        if ((data = backManagementService.delAlumniHelpByHelpId(helpId)) > 0) {
            return "redirect:/toForumManagementPage";
        }
        return "500";
    }

    @GetMapping("/toAlumniPhotoManagementPage")
    public String toAlumniPhotoManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<AlumniPhoto> allAlumniPhoto = backManagementService.findAllAlumniPhotoByAdminDepartmentId(admin.getDepartmentId());
        if (allAlumniPhoto != null) {
            model.addAttribute("allAlumniPhoto", allAlumniPhoto);
            return "back_management/alumni_photo_management";
        }
        else
            return "500";
    }

    @PostMapping("/delAlumniPhotoByPhotoId")
    public String delAlumniPhotoByPhotoId(HttpServletRequest request) {
        int photoId = Integer.parseInt(request.getParameter("photoId"));
        int data = -1;
        if ((data = backManagementService.delAlumniPhotoByPhotoId(photoId)) > 0) {
            return "redirect:/toAlumniPhotoManagementPage";
        }
        return "500";
    }

    @PostMapping("/updAlumniPhoto")
    public String updAlumniPhoto(HttpServletRequest request, AlumniPhoto alumniPhoto) {
        alumniPhoto.setPhotoId(Integer.parseInt(request.getParameter("photoId")));
        alumniPhoto.setPhotoTitle(request.getParameter("photoTitle"));
        alumniPhoto.setPhotoImageName(request.getParameter("photoImageName"));
        alumniPhoto.setPhotoDate(dateUtil.getSystemDateToString());
        int data = -1;
        if ((data = backManagementService.updAlumniPhoto(alumniPhoto)) > 0) {
            return "redirect:/toAlumniPhotoManagementPage";
        }
        return "500";
    }

}
