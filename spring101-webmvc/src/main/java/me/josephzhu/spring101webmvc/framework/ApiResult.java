package me.josephzhu.spring101webmvc.framework;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Data
@Builder
public class ApiResult<T> {
    boolean success;
    String code;
    String error;
    String message;
    String path;
    long time;
    T data;
}
