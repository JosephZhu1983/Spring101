package me.josephzhu.spring101webmvc.framework.result;

import me.josephzhu.spring101webmvc.framework.ApiController;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@ControllerAdvice(annotations = ApiController.class)
public class ApiResultAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(true)
                .code(String.valueOf(HttpStatus.OK.value()))
                .data(body)
                .error("")
                .message(HttpStatus.OK.getReasonPhrase())
                .path(request.getURI().getPath())
                .build();
    }
}
