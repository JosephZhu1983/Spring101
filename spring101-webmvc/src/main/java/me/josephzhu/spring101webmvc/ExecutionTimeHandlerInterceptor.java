package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExecutionTimeHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME_ATTR_NAME = "startTime";
    private static final String EXECUTION_TIME_ATTR_NAME = "executionTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTR_NAME, startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute(START_TIME_ATTR_NAME);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String time = "[" + handler + "] executeTime : " + executionTime + "ms";

        if (modelAndView != null) {
            modelAndView.addObject(EXECUTION_TIME_ATTR_NAME, time);
        }

        log.debug(time);
    }
}
