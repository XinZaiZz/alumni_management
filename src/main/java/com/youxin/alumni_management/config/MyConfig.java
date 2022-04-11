package com.youxin.alumni_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * @author youxin
 * @program youxinblog
 * @description 自定义配置
 * @date 2022-02-05 23:05
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

    //自定义国际化组件就生效了
    @Bean
    public LocaleResolver localeResolver(){
        return  new LocaleResolverConfig();
    }

    @Bean
    public ViewResolver MyViewResolver(){
        return new MyViewResolver();
    }

    public static class MyViewResolver implements ViewResolver {

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

}
