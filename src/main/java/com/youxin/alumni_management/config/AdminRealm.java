package com.youxin.alumni_management.config;

import com.youxin.alumni_management.pojo.Admin;
import com.youxin.alumni_management.pojo.User;
import com.youxin.alumni_management.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-03-26 21:30
 */
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //System.out.println("执行了授权=>doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //普通用户登录不需要授权
//        获取当前登陆的用户
        Subject subject = SecurityUtils.getSubject();
        //因为return new SimpleAuthenticationInfo(user,user.getPassword(),"");中返回了user所以能够获取user
        Admin currentAdmin = (Admin) subject.getPrincipal();
        //获取数据库中的权限
        String permStr = currentAdmin.getPerms();
        List<String> perms_list = Arrays.asList(permStr.split(","));
        //添加权限
        //添加一个权限
//        info.addStringPermission(currentAdmin.getPerms());
        //添加多个权限
        info.addStringPermissions(perms_list);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;

        //数据库中查询对应用户信息
        Admin admin = loginService.findAdminByName(userToken.getUsername());
        //用户名验证
        if (admin == null) {
            return null;
        }
        //获取当前用户
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginAdmin", admin.getAdminName());

        //密码验证由shiro完成
        return new SimpleAuthenticationInfo(admin,admin.getPassword(),"");
    }
}
