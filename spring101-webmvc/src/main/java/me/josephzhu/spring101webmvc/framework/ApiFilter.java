package me.josephzhu.spring101webmvc.framework;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiFilters.class)
public @interface ApiFilter {
    String name() default "";
    int order() default 0;
    Class<? extends AbstractApiFilter> value();
}
