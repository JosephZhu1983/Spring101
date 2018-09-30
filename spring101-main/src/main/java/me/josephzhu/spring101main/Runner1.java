package me.josephzhu.spring101main;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101customstarter.AbstractMyService;
import me.josephzhu.spring101customstarter.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Runner1 implements CommandLineRunner {

    @Autowired
    private AbstractMyService service;

    @Override
    public void run(String... args) {
        log.info(service.hello());
    }
}
