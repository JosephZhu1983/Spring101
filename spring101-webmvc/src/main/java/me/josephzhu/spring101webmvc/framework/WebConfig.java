package me.josephzhu.spring101webmvc.framework;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandlerMapping("v");
    }
}
