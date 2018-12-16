package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.filter.AbstractApiFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Slf4j
public class TestApiFilter3 extends AbstractApiFilter {
    @Override
    protected String getDescription() {
        return "testfilter3";
    }

    @Override
    public Object beforeReturn(HttpServletRequest request, HttpServletResponse response, Method method, Object object) {
        if (object instanceof MyItem) {
            MyItem myItem = (MyItem) object;
            myItem.setName(myItem.getName() + getDescription());
            return myItem;
        }
        return object;
    }
}
