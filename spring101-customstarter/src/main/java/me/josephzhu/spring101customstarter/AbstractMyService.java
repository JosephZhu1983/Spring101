package me.josephzhu.spring101customstarter;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMyService {

    protected String word;
    public AbstractMyService(String word) {
        this.word = word;
    }

    public AbstractMyService() {
        this ("Hello");
    }

    @Autowired
    protected MyServiceProperties properties;

    public abstract String hello();
}
