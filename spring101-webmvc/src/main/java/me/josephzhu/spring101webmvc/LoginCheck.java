package me.josephzhu.spring101webmvc;

import me.josephzhu.spring101webmvc.framework.filter.AbstractApiFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginCheck extends AbstractApiFilter {

    @Override
    public boolean preAction(HttpServletRequest request, HttpServletResponse response, Method method) {
        if (request.getHeader("token") == null || !request.getHeader("token").equals("1"))
            throw new RuntimeException("请登录！");
        return true;
    }
}
