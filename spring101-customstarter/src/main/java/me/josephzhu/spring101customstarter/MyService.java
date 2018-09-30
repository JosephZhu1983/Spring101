package me.josephzhu.spring101customstarter;

import org.springframework.stereotype.Service;

@Service
public class MyService extends AbstractMyService {

    public MyService(String word) {
        super(word);
    }

    public MyService(){}

    @Override
    public String hello() {
        return String.format("V1 %s >> %s:%s !!", word, properties.getName(), properties.getAge());
    }

}
