package com.kalin.config;

import com.kalin.interceptors.LoginInterceptors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录不拦截和测试接口不拦截
        registry.addInterceptor(loginInterceptors).excludePathPatterns("/my/login","/my/test","/index/showFood","/index/showSport");
    }
}
