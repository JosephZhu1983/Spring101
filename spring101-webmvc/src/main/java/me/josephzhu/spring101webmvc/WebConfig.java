package me.josephzhu.spring101webmvc;

import me.josephzhu.spring101webmvc.framework.WebApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class WebConfig extends WebApiConfig {
    @Autowired
    TestHandlerInterceptor testHandlerInterceptor;

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/foo", "/rest/item");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(testHandlerInterceptor).addPathPatterns("/**");
    }
}
