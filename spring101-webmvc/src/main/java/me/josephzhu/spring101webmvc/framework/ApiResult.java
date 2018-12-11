package me.josephzhu.spring101webmvc.framework;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult<T> {
    T data;
    boolean success;
    String code;
    String message;
    String path;
}
