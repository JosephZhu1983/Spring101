package me.josephzhu.spring101webmvc.framework.exception;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.result.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(HttpServletRequest request, Exception ex) {
        logException(request,ex);
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
        logException(request,ex);
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
        String message = "Validating request parameter failed (" + ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("Field：%s Value：%s Reason：%s", fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining( "; " )) + ")";
        logException(request,ex);
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
        logException(request,ex);
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

    private void logException(HttpServletRequest request, Exception ex) {
        ApiExceptionMessage apiExceptionMessage = ApiExceptionMessage.builder()
                .headers(Collections.list(request.getHeaderNames()).stream().collect(Collectors.toMap(h -> h, request::getHeader)))
                .exception(ex.getMessage())
                .url(request.getRequestURL().toString())
                .build();
        log.error("Executing Api occurs error, see below information: ", apiExceptionMessage.toString(), ex);

    }
}
