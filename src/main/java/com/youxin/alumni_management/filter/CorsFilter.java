package com.youxin.alumni_management.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-04-11 14:19
 */

/**
 * 允许跨域请求
 */
@WebFilter(filterName = "corsFilter", urlPatterns = "/*")
public class CorsFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response2 = (HttpServletResponse) response;
        response2.setHeader("Access-Control-Allow-Origin", "*");
        response2.setHeader("Access-Control-Allow-Methods", "*");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}

