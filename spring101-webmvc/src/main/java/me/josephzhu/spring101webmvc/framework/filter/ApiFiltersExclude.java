package me.josephzhu.spring101webmvc.framework.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFiltersExclude {
    ApiFilterExclude[] value();
}
