package com.youxin.alumni_management.config;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${addr.alumni-photo-image}")
    private String alumniPhotoImages;

    @Value("${addr.alumni-help-image}")
    private String alumniHelpImages;

    @Value("${addr.new-article-image}")
    private String newsArticleImages;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/photos/**", "/helpPhotos/**", "/newsArticle/**").addResourceLocations("file:F://Test/alumniPhoto images/", "file:F://Test/alumniHelp images/", "file:F://Test/newsArticle images/");
        registry.addResourceHandler("/photos/**", "/helpPhotos/**", "/newsArticle/**").addResourceLocations(alumniPhotoImages, alumniHelpImages, newsArticleImages);
    }
}
