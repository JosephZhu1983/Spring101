package me.josephzhu.spring101beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("helloService");
        if (beanDefinition != null) {
            beanDefinition.setScope("singleton");
            beanDefinition.getPropertyValues().add("counter", 10);
        }

        System.out.println("MyBeanFactoryPostProcessor");
    }
}
