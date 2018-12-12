package me.josephzhu.spring101webmvc.framework;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiResultAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getDeclaringClass().isAnnotationPresent(ApiController.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(true)
                .code("200")
                .data(body)
                .error("")
                .message("OK")
                .path(request.getURI().getPath())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handleException(HttpServletRequest request, Exception ex) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(false)
                .code("500")
                .data(null)
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult handle404Exception(HttpServletRequest request, Exception ex) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(false)
                .code("404")
                .data(null)
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(ApiException.class)
    public ApiResult handleApiException(HttpServletRequest request, ApiException ex) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(false)
                .code(ex.getErrorCode())
                .data(null)
                .error(ex.getClass().getName())
                .message(ex.getErrorMessage())
                .path(request.getRequestURI())
                .build();
    }
}
