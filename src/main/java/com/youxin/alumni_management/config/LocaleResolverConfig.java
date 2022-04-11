package com.youxin.alumni_management.config;

/**
 * @author youxin
 * @program youxinblog
 * @description 本地国际化配置
 * @date 2022-02-05 23:04
 */

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class LocaleResolverConfig implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        Locale locale = Locale.getDefault();

        //如果请求的链接携带了国际化参数
        if(!StringUtils.isEmpty(lang)){
            String[] split = lang.split("_");
            locale= new Locale(split[0],split[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}

