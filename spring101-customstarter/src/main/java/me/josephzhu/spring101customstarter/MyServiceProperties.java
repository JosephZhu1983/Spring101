package me.josephzhu.spring101customstarter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring101")
@Data
public class MyServiceProperties {
    /**
     * user name
     */
    private String name;
    /**
     * user age *Should between 1 and 120
     */
    private Integer age;
    /**
     * determine the service version you want use
     */
    private String version;
}
