package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.AbstractApiFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Slf4j
public class TestApiFilter2 extends AbstractApiFilter {
    @Override
    public boolean preAction(HttpServletRequest request, HttpServletResponse response, Method method) {
        log.info("pre2:" + request.getRequestURI());
        return true;
    }

    @Override
    public void postAction(HttpServletRequest request, HttpServletResponse response, Method method) {
        log.info("post2:" + request.getRequestURI());
    }

    @Override
    public Object beforeReturn(HttpServletRequest request, HttpServletResponse response, Method method, Object object) {
        log.info("beforeReturn2:" + request.getRequestURI() + "/" + method.toString());
        if (object instanceof MyItem) {
            MyItem myItem = (MyItem) object;
            myItem.setName("updated by TestApiFilter2 / " + myItem.getName());
            return myItem;
        }
        return object;
    }
}
