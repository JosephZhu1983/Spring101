package me.josephzhu.spring101webmvc.framework;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = ApiController.class)
public class ApiResultAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return new ApiResult<>(body, true, "200", "OK", request.getURI().getPath());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(HttpServletRequest request, Exception ex) {
        return new ApiResult(null, false, "500", ex.getMessage(), request.getRequestURI());
    }

    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public ApiResult handleApiException(HttpServletRequest request, ApiException ex) {
        return new ApiResult(null, false, ex.getErrorCode(), ex.getErrorMessage(), request.getRequestURI());
    }
}
