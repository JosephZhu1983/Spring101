package me.josephzhu.spring101webmvc.framework;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@AllArgsConstructor
@Builder
@Data
public class ApiExceptionMessage {
    private Map<String,String> headers;
    private String url;
    private String exception;
}
