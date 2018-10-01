package me.josephzhu.spring101beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MyService) {
            System.out.println(bean + "#postProcessAfterInitialization:" + ((MyService) bean).increaseCounter());
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MyService) {
            System.out.println(bean + "#postProcessBeforeInitialization:" + ((MyService) bean).increaseCounter());
        }
        return bean;
    }
}
