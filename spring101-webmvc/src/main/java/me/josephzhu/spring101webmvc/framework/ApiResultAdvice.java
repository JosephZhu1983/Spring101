package me.josephzhu.spring101webmvc.framework;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

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
                .code(String.valueOf(HttpStatus.OK.value()))
                .data(body)
                .error("")
                .message(HttpStatus.OK.getReasonPhrase())
                .path(request.getURI().getPath())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handleException(HttpServletRequest request, Exception ex) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(false)
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .data(null)
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult handleNoHandlerFoundException(HttpServletRequest request, Exception ex) {
        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(false)
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .data(null)
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        String message = "请求参数校验失败 (" + ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("字段：%s 值：%s 原因：%s", fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining( "; " )) + ")";

        return ApiResult.builder()
                .time(System.currentTimeMillis())
                .success(false)
                .code(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .data(null)
                .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(message)
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
