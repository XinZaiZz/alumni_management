package com.youxin.alumni_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author youxin
 * @program alumni_management
 * @description 设置图片虚拟路径
 * @date 2022-05-03 14:59
 */

@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/photos/**", "/helpPhotos/**", "/newsArticle/**").addResourceLocations("file:F://Test/alumniPhoto images/", "file:F://Test/alumniHelp images/", "file:F://Test/newsArticle images/");
    }
}
