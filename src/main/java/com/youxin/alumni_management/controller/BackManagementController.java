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
import java.util.Map;


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
        return "back_management/dashboard";
    }

    @GetMapping("/toHomePage")
    public String toHomePage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        int userCount = backManagementService.selCountUser(admin.getDepartmentId());
        int maleCount = backManagementService.selCountMale(admin.getDepartmentId());
        List<Map<String, Object>> occupationGroups = backManagementService.countByOccupationGroup(admin.getDepartmentId());
        List<Map<String, Object>> majorGroups = backManagementService.countByMajorGroup(admin.getDepartmentId());
        List<Map<String, Object>> gradeGroups = backManagementService.countByGrade(admin.getDepartmentId());
        List<Map<String, Object>> ageGroups = backManagementService.countByAge(admin.getDepartmentId());
        List<Map<String, Object>> instructorNameGroups = backManagementService.countByInstructorName(admin.getDepartmentId());
        model.addAttribute("userCount", userCount);
        model.addAttribute("maleCount", maleCount);
        model.addAttribute("occupationGroups", occupationGroups);
        model.addAttribute("majorGroups", majorGroups);
        model.addAttribute("gradeGroups", gradeGroups);
        model.addAttribute("ageGroups", ageGroups);
        model.addAttribute("instructorNameGroups", instructorNameGroups);
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
        int userCount = backManagementService.selCountUser(admin.getDepartmentId());
        int maleCount = backManagementService.selCountMale(admin.getDepartmentId());

        if (allUser != null) {
            model.addAttribute("userCount", userCount);
            model.addAttribute("maleCount", maleCount);
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

    @GetMapping("/toExamineNewsManagementPage")
    public String toExamineNewsManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户不是学校管理员
        if (admin == null || admin.getDepartmentId() != 1000) {
            return "redirect:/unAuthorized";
        }
        List<NewsArticle> allExamineNewsArticle = backManagementService.findAllExamineNewArticles();
        if (allExamineNewsArticle != null) {
            model.addAttribute("allExamineNewsArticle", allExamineNewsArticle);
            return "back_management/news_admin_examine_management";
        }else
            return "500";
    }

    @GetMapping("/examineNewsArticle/Pass-Or-UnPass/{newArticleId}/{status}")
    public String PassOrUnPassExamineNewsArticle(@PathVariable("newArticleId")Integer newArticleId, @PathVariable("status")Integer status)  {
        //修改新闻表中的状态
        int data = -1;
        //1为设置为通过，2为未通过
        data = backManagementService.updNewsArticleStatus(newArticleId, status);
        if (data > 0) {

            //修改成功
            return "redirect:/toExamineNewsManagementPage";

        }
        //修改失败返回到500错误页面
        return "500";
    }

    @GetMapping("/toActivityManagementPage")
    public String toActivityManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<Activity> allActivity = backManagementService.findAllActivityByAdminDepartmentId(admin.getDepartmentId());
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

    @GetMapping("/toActivityExamineManagementPage")
    public String toActivityExamineManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<Activity> allExamineActivity = backManagementService.findAllExamineActivityByAdminDepartmentId(admin.getDepartmentId());
        if (allExamineActivity != null) {
            model.addAttribute("allExamineActivity", allExamineActivity);
            return "back_management/activity_examine_management";
        }
        return "500";
    }

    @GetMapping("/examineActivity/Pass-Or-UnPass/{activityId}/{status}")
    public String passOrUnPassActivity(@PathVariable("activityId") Integer activityId, @PathVariable("status")Integer status, HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //修改活动表中的状态
        int data = -1;
        //1为设置为通过，3为未通过
        data = backManagementService.updActivityStatus(activityId, status);
        if (data > 0) {

            if (admin == null || admin.getDepartmentId() == 1000) {
                return "redirect:/toActivityExamineAdminManagementPage";
            }
            //修改成功
            return "redirect:/toActivityExamineManagementPage";

        }
        //修改失败返回到500错误页面
        return "500";
    }

    @GetMapping("/toActivityExamineAdminManagementPage")
    public String toActivityExamineAdminManagementPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        List<Activity> allPZHUAdminActivity = backManagementService.findAllExamineActivityByPZHUAdmin();
        if (allPZHUAdminActivity != null) {
            model.addAttribute("allPZHUAdminActivity", allPZHUAdminActivity);
            return "back_management/activity_admin_examine_management";
        }
        return "500";
    }

    @GetMapping("/examineActivityByPZHUAdmin/Pass-Or-UnPass/{activityId}/{status}")
    public String passOrUnPassByPZHUAdminActivity(@PathVariable("activityId") Integer activityId, @PathVariable("status")Integer status) {
        //修改活动表中的状态
        int data = -1;
        //2为发布，3为未通过
        data = backManagementService.updActivityStatus(activityId, status);
        if (data > 0) {
            //修改成功
            return "redirect:/toActivityExamineAdminManagementPage";

        }
        //修改失败返回到500错误页面
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
            return "redirect:/toAlumniHelpManagementPage";
        }
        return "500";
    }

    @GetMapping("/toExamineAlumniHelpPage")
    public String toExamineAlumniHelpPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户不是学校管理员
        if (admin == null || admin.getDepartmentId() != 1000) {
            return "redirect:/unAuthorized";
        }
        List<AlumniHelp> allExamineAlumniHelp = backManagementService.findAllExamineAlumniHelp();
        if (allExamineAlumniHelp != null) {
            model.addAttribute("allExamineAlumniHelp", allExamineAlumniHelp);
            return "back_management/help_admin_examine_management";
        }else
            return "500";
    }

    @GetMapping("/examineAlumniHelp/Pass-Or-UnPass/{helpId}/{status}")
    public String PassOrUnPassExamineAlumniHelp(@PathVariable("helpId")Integer helpId, @PathVariable("status")Integer status)  {
        //修改新闻表中的状态
        int data = -1;
        //1为设置为通过，2为未通过
        data = backManagementService.updAlumniHelpStatus(helpId, status);
        if (data > 0) {

            //修改成功
            return "redirect:/toExamineAlumniHelpPage";

        }
        //修改失败返回到500错误页面
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

    @GetMapping("/toExamineAlumniPhotoPage")
    public String toExamineAlumniPhotoPage(HttpServletRequest request, Model model) {
        //获取当前登录用户信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户不是学校管理员
        if (admin == null || admin.getDepartmentId() != 1000) {
            return "redirect:/unAuthorized";
        }
        List<AlumniPhoto> allExamineAlumniPhoto = backManagementService.findAllExamineAlumniPhoto();
        if (allExamineAlumniPhoto != null) {
            model.addAttribute("allExamineAlumniPhoto", allExamineAlumniPhoto);
            return "back_management/photo_admin_examine_management";
        }else
            return "500";
    }

    @GetMapping("/examineAlumniPhoto/Pass-Or-UnPass/{photoId}/{status}")
    public String PassOrUnPassExamineAlumniPhoto(@PathVariable("photoId")Integer photoId, @PathVariable("status")Integer status)  {
        //修改新闻表中的状态
        int data = -1;
        //1为设置为通过，2为未通过
        data = backManagementService.updAlumniPhotoStatus(photoId, status);
        if (data > 0) {

            //修改成功
            return "redirect:/toExamineAlumniPhotoPage";

        }
        //修改失败返回到500错误页面
        return "500";
    }

    @GetMapping("/toAdminInfoPage")
    public String toAdminInfoPage(HttpServletRequest request, Model model) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null || admin.getDepartmentId() != 1000) {
            return "redirect:/unAuthorized";
        }else if (admin.getDepartmentId() == 1000) {
            List<Admin> allAdmins = backManagementService.findAllAdmins();
            model.addAttribute("allAdmins", allAdmins);
            return "back_management/admin_info";
        }
        return "500";
    }

    @PostMapping("/insAdmin")
    public String insAdmin(HttpServletRequest request) {
        Admin insAdmin = new Admin();
        insAdmin.setAdminName(request.getParameter("adminName"));
        insAdmin.setPassword(request.getParameter("password"));
        insAdmin.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
        insAdmin.setNickName(request.getParameter("nickName"));
        insAdmin.setGender(request.getParameter("gender"));
        int data = -1;
        if ((data = backManagementService.insAdmin(insAdmin)) > 0) {
            //新增成功
            return "redirect:/toAdminInfoPage";
        }
        return "500";
    }

    @PostMapping("/updAdmin")
    public String updAdmin(HttpServletRequest request) {
        Admin updAdmin = new Admin();
        updAdmin.setAdminId(Integer.parseInt(request.getParameter("adminId")));
        updAdmin.setAdminName(request.getParameter("adminName"));
        updAdmin.setPassword(request.getParameter("password"));
        updAdmin.setDepartmentId(Integer.parseInt(request.getParameter("departmentId")));
        updAdmin.setNickName(request.getParameter("nickName"));
        int data = -1;
        if ((data = backManagementService.updAdmin(updAdmin)) > 0) {
            //修改成功
            return "redirect:/toAdminInfoPage";
        }
        return "500";
    }

    @PostMapping("/delAdmin")
    public String delAdmin(HttpServletRequest request) {
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        int data = -1;
        if ((data = backManagementService.delAdmin(adminId)) > 0) {
            //删除成功
            return "redirect:/toAdminInfoPage";
        }
        return "500";
    }

}
