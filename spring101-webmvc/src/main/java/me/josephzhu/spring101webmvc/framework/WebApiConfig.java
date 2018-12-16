package me.josephzhu.spring101webmvc.framework;

import me.josephzhu.spring101webmvc.framework.filter.ApiFilterInterceptor;
import me.josephzhu.spring101webmvc.framework.misc.AutoRequestBodyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Configuration
public class WebApiConfig extends WebMvcConfigurationSupport {

    @Autowired
    List<HttpMessageConverter<?>> httpMessageConverters;
    @Autowired
    ApiFilterInterceptor apiFilterInterceptor;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiFilterInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/favicon.ico");
    }

    @Override
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.requestMappingHandlerAdapter();

        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(new AutoRequestBodyProcessor(httpMessageConverters));
        requestMappingHandlerAdapter.setCustomArgumentResolvers(argumentResolvers);

        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        returnValueHandlers.add(new AutoRequestBodyProcessor(httpMessageConverters));
        requestMappingHandlerAdapter.setCustomReturnValueHandlers(returnValueHandlers);

        return requestMappingHandlerAdapter;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}


