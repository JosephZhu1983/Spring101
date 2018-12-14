package me.josephzhu.spring101webmvc.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public abstract class AbstractApiFilter {
    public boolean preAction(HttpServletRequest request, HttpServletResponse response, Method method) { return true; }
    public Object beforeReturn(HttpServletRequest request, HttpServletResponse response, Method method, Object object) {return object;}
    public void postAction(HttpServletRequest request, HttpServletResponse response, Method method) {}
}
