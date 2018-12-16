package me.josephzhu.spring101webmvc.framework.filter;

import java.lang.annotation.*;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiFiltersExclude.class)
public @interface ApiFilterExclude {
    Class<? extends AbstractApiFilter> value();
}
