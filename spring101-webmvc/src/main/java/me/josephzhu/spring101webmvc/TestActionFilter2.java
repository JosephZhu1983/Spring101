package me.josephzhu.spring101webmvc;

import me.josephzhu.spring101webmvc.framework.AbstractActionFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TestActionFilter2 extends AbstractActionFilter {
    @Override
    public void preAction(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("pre2:" + request.getRequestURI());
    }

    @Override
    public void postAction(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("post2:" + request.getRequestURI());

    }
}
