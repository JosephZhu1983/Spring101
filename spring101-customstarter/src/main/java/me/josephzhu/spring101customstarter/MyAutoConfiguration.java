package me.josephzhu.spring101customstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyServiceProperties.class)
public class MyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(MyService.class)
    @ConditionalOnProperty(prefix = "spring101", name = "version", havingValue = "v1", matchIfMissing = true)
    MyService getMyService(){
        return new MyService();
    }

    @Bean
    @ConditionalOnMissingBean(MyServiceV2.class)
    @ConditionalOnProperty(prefix = "spring101", name = "version", havingValue = "v2")
    MyServiceV2 getMyServiceV2(){
        return new MyServiceV2();
    }
}
