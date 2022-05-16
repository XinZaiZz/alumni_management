package com.youxin.alumni_management.controller;

import com.youxin.alumni_management.pojo.*;
import com.youxin.alumni_management.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 登录控制层
 * @date 2022-02-08 22:49
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    Logger logger = Logger.getLogger(this.getClass());

    private final RegisterUserService registerUserService;

    private final ArticleService articleService;

    private final AlumniHelpService alumniHelpService;

    private final ForumService forumService;

    private final LoginService loginService;

    @GetMapping("/toindex1")
    public String toIndex_1() {
        return "index1";
    }

    @GetMapping("/toLogin")
    public String toLoginPage(HttpServletRequest request, Model model) {
        //是否进入未授权请求
        String unAuthorized = (String) request.getAttribute("unAuthorized");
        if (unAuthorized != null) {
            model.addAttribute("unAuthorized", unAuthorized);
        }
//        System.out.println("unAuthorized==>" + unAuthorized);
        request.setAttribute("msg","请先登陆！");
        return "login/login";
    }

    /**
     * @author youxin
     * @date 2022-02-08 22:32
     * @param username
     * @param password
     * @param rememberMe
     * @param model
     * @param session
     * @return java.lang.String
     * @throws
     * @since
     */
    @PostMapping("/user/login")
    public String userLogin(@RequestParam("username") String username, String password, String rememberMe, Model model, HttpSession session, HttpServletRequest request) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        User userByName = loginService.findUserByName(username);
        if (userByName == null) {
            model.addAttribute("msg","用户名错误！");
            return "login/login";
        }
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user",user);
            logger.info("普通用户:" + user.getUserName() + "登录");
//            model.addAttribute("user",user);
            return "redirect:/toIndex";
        }catch (UnknownAccountException uae) {
            model.addAttribute("msg","用户名错误！");
            return "login/login";
        }catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg","密码错误！");
            return "login/login";
        }
    }

    @GetMapping(value = {"/toIndex","/"})
    public String toIndex(HttpServletRequest request, Model model){
        //获取当前用户
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user",user);
        //获取所有文章
        List<NewsArticle> newsArticleList = articleService.findAllNewsArticle();
        List<NewsArticle> top3Article = articleService.findTop3Article();
        List<AlumniHelp> top2AlumniHelp = alumniHelpService.findTop2AlumniHelp();
        List<Forum> top8Forum = forumService.findTop8Forum();
        model.addAttribute("newsArticleList", newsArticleList);
        model.addAttribute("top2Article", top3Article.subList(0,2));
        model.addAttribute("lastArticle", top3Article.get(2));
        model.addAttribute("top2AlumniHelp", top2AlumniHelp);
        model.addAttribute("top8Forum", top8Forum);
        return "index";
    }

    /**
     * @author youxin
     * @date 2022-02-09 `
     * @param
     * @return java.lang.String
     * @throws
     * @since
     */
    //登出
    @RequestMapping("/loginout")
    public String logout() {
        Subject currentSubject = SecurityUtils.getSubject();
        currentSubject.logout();
//        return "forward:/toLogin";
        return "redirect:/toIndex";
    }

    @GetMapping("/admin/logout")
    public String adminLogout() {
        Subject currentSubject = SecurityUtils.getSubject();
        currentSubject.logout();
//        return "forward:/toLogin";
        return "redirect:/toAdminLogin";
    }


    @GetMapping("/toAdminLogin")
    public String toAdminLoginPage() {
        return "login/admin_login";
    }

    /**
     *
     * @param adminName
     * @param password
     * @param rememberMe
     * @param model
     * @param session
     * @param request
     * @return String
     */
    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam("username") String adminName, String password, String rememberMe, Model model, HttpSession session, HttpServletRequest request) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(adminName, password);
        if (loginService.findAdminByName(adminName) == null) {
            model.addAttribute("msg","用户名错误！");
            return "login/admin_login";
        }
        try {
            subject.login(token);
            Admin admin = (Admin) subject.getPrincipal();
            session.setAttribute("admin",admin);
            model.addAttribute("admin",admin);
            logger.info("管理员用户:" + admin.getAdminName() + "登录");
            return "redirect:/toDashboard";
        }catch (UnknownAccountException uae) {
            model.addAttribute("msg","用户名错误！");
            return "login/admin_login";
        }catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg","密码错误！");
            return "login/admin_login";
        }
    }

    @GetMapping(value = {"/toDashboard"})
    public String toDashboard(Model model, HttpServletRequest request){
        //获取当前用户
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //如果用户未登录
        if (admin == null) {
            return "redirect:/unAuthorized";
        }
        model.addAttribute("admin",admin);
        //如果当前用户已经登录

        //获取注册用户信息
        List<RegisterUser> allRegisteringUser = registerUserService.findAllRegisterUser(admin.getDepartmentId());
        //将当前登陆管理员所管理的注册用户信息存入model
        model.addAttribute("allRegisteringUser", allRegisteringUser);

        return "back_management/dashboard";
    }


}
