package me.josephzhu.spring101webmvc.framework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ApiFilterInterceptor implements HandlerInterceptor {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            for (AbstractApiFilter filterInstance : ApiFilterUtil.getFilters(applicationContext, handlerMethod.getMethod(), true)) {
                filterInstance.postAction(request, response, handlerMethod.getMethod());
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            for (AbstractApiFilter filterInstance : ApiFilterUtil.getFilters(applicationContext, handlerMethod.getMethod(), false)) {
                if (!filterInstance.preAction(request, response, handlerMethod.getMethod()))
                    return false;
            }
        }

        return true;
    }
}
