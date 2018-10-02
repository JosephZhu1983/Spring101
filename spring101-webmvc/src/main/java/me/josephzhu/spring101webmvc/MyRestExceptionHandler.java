package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class MyRestExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public APIResponse handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        log.error(String.format("访问 %s -> %s 出错了！", req.getRequestURI(), method.toString()), ex);
        return new APIResponse(null, false, ex.getMessage(), "");
    }
}
