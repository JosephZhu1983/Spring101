package me.josephzhu.spring101webmvc.framework.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@Slf4j
public abstract class AbstractApiFilter {

    public final boolean preActionHandler(HttpServletRequest request, HttpServletResponse response, Method method) {
        long begin = System.currentTimeMillis();
        boolean c = false;
        try {
            c = preAction(request, response, method);
        } finally {
            try {
                if (Arrays.asList(getClass().getDeclaredMethods()).stream().filter(m->m.getName().equals("preAction")).findAny().isPresent())
                    log.info(String.format("Took [%s] to execute filter [%s:%s] phase [%s] on %s (method:[%s]) => %s",
                            (System.currentTimeMillis() - begin) + "ms",
                            this.getClass().toString(),
                            getDescription(),
                            "preAction",
                            request.getRequestURI(),
                            method.toGenericString(),
                            c ? "Continue" : "Break"));
            } catch (Exception ex) {}
        }
        return c;

    }

    public final Object beforeReturnHandler(HttpServletRequest request, HttpServletResponse response, Method method, Object object) {
        long begin = System.currentTimeMillis();
        Object o = beforeReturn(request, response, method, object);
        try {
            if (Arrays.asList(getClass().getDeclaredMethods()).stream().filter(m->m.getName().equals("beforeReturn")).findAny().isPresent())
                log.info(String.format("Took [%s] to execute filter [%s:%s] phase [%s] on %s (method:[%s])",
                            (System.currentTimeMillis() - begin) + "ms",
                            this.getClass().toString(),
                            getDescription(),
                            "beforeReturn",
                            request.getRequestURI(),
                            method.toGenericString()));
        } catch (Exception ex) {}
        return o;
    }

    public final void postActionHandler(HttpServletRequest request, HttpServletResponse response, Method method) {
        long begin = System.currentTimeMillis();
        postAction(request, response, method);
        try {
            if (Arrays.asList(getClass().getDeclaredMethods()).stream().filter(m->m.getName().equals("postAction")).findAny().isPresent())
                log.info(String.format("Took [%s] to execute filter [%s:%s] phase [%s] on %s (method:[%s])",
                        (System.currentTimeMillis() - begin) + "ms",
                        this.getClass().toString(),
                        getDescription(),
                        "postAction",
                        request.getRequestURI(),
                        method.toGenericString()));
        } catch (Exception ex) {}
    }

    protected abstract String getDescription();
    protected boolean preAction(HttpServletRequest request, HttpServletResponse response, Method method) { return true; }
    protected Object beforeReturn(HttpServletRequest request, HttpServletResponse response, Method method, Object object) {return object;}
    protected void postAction(HttpServletRequest request, HttpServletResponse response, Method method) {}
}
