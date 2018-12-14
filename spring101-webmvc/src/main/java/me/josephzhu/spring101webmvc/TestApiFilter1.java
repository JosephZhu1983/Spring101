package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.AbstractApiFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Slf4j
public class TestApiFilter1 extends AbstractApiFilter {
    @Override
    public boolean preAction(HttpServletRequest request, HttpServletResponse response, Method method) {
        log.info("pre1:" + request.getRequestURI() + "/" + method.toString());
        return true;
    }

    @Override
    public void postAction(HttpServletRequest request, HttpServletResponse response, Method method) {
        log.info("post1:" + request.getRequestURI() + "/" + method.toString());
    }

    @Override
    public Object beforeReturn(HttpServletRequest request, HttpServletResponse response, Method method, Object object) {
        log.info("beforeReturn1:" + request.getRequestURI() + "/" + method.toString());
        if (object instanceof MyItem) {
            MyItem myItem = (MyItem) object;
            myItem.setName("updated by TestApiFilter1 / " + myItem.getName());
            return myItem;
        }
        return object;
    }
}
