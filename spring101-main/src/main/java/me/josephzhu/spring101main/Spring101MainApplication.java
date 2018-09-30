package me.josephzhu.spring101main;

import me.josephzhu.spring101customstarter.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Spring101MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring101MainApplication.class, args);
    }

}
