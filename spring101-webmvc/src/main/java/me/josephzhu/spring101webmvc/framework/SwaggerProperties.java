package me.josephzhu.spring101webmvc.framework;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("com.bkjk.platform.springfox")
public class SwaggerProperties {
    private String contextPath;
    private String contact = "****@bkjk.com";
}