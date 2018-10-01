package me.josephzhu.spring101beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(initMethod = "init")
    public MyService helloService() {
        MyService myService = new MyService();
        myService.increaseCounter();
        return myService;
    }

}
