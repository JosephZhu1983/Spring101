package me.josephzhu.spring101beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication
public class Spring101BeansApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    private MyService helloService;
    @Autowired
    @Qualifier("helloService")
    private MyService service;

    public static void main(String[] args) {
        SpringApplication.run(Spring101BeansApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====================");
        applicationContext.getBeansOfType(MyService.class).forEach((name, service) -> {
            System.out.println(name + ":" + service);
        });

        System.out.println("====================");
        System.out.println(helloService.hello());
        System.out.println(service.hello());

    }
}
