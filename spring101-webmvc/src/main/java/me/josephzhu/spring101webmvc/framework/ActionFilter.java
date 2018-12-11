package me.josephzhu.spring101webmvc.framework;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ActionFilters.class)
public @interface ActionFilter {
    String name() default "";
    int order() default 0;
    Class<? extends AbstractActionFilter> value();
}
