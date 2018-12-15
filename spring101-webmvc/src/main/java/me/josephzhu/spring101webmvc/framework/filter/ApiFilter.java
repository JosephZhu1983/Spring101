package me.josephzhu.spring101webmvc.framework.filter;

import java.lang.annotation.*;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiFilters.class)
public @interface ApiFilter {
    String name() default "";
    int order() default 0;
    Class<? extends AbstractApiFilter> value();
}
