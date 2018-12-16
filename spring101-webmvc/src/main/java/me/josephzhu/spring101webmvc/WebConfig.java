package me.josephzhu.spring101webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Autowired
    TestHandlerInterceptor testHandlerInterceptor;

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/foo", "/rest/item");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testHandlerInterceptor).addPathPatterns("/**");
    }
}
