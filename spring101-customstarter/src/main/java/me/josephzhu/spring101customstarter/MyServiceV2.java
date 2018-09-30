package me.josephzhu.spring101customstarter;

import org.springframework.stereotype.Service;

@Service
public class MyServiceV2 extends AbstractMyService {

    public MyServiceV2(String word) {
        super(word);
    }

    public MyServiceV2(){}

    @Override
    public String hello() {
        return String.format("V2 %s >> %s:%s !!", word, properties.getName(), properties.getAge());
    }

}
