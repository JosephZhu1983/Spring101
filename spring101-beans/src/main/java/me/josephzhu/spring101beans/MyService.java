package me.josephzhu.spring101beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MyService implements InitializingBean, DisposableBean {

    private int counter = 0;

    public MyService() {
        counter++;
        System.out.println(this + "#constructor:" + counter);
    }

    public int increaseCounter() {
        this.counter++;
        return counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String hello() {
        return this + "#hello:" + counter;
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println(this + "#preDestroy:" + counter);

    }

    @Override
    public void afterPropertiesSet() {
        counter++;
        System.out.println(this + "#afterPropertiesSet:" + counter);
    }

    @PostConstruct
    public void postConstruct() {
        counter++;
        System.out.println(this + "#postConstruct:" + counter);
    }

    public void init() {
        counter++;
        System.out.println(this + "#init:" + counter);

    }

    @Override
    public void destroy() {
        System.out.println(this + "#destroy:" + counter);

    }
}
