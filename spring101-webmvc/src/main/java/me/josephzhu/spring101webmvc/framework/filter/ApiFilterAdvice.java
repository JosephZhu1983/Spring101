package me.josephzhu.spring101webmvc.framework.filter;

import me.josephzhu.spring101webmvc.framework.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author zhuye
 * @date 2018/12/14
 */
@ControllerAdvice
public class ApiFilterAdvice implements ResponseBodyAdvice {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getDeclaringClass().getAnnotationsByType(ApiController.class) != null &&
                returnType.getMethod().getAnnotationsByType(ApiFilter.class).length>0;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        List<AbstractApiFilter> filterInstances = ApiFilterUtil.getFilters(applicationContext, returnType.getMethod(), false);
        for (AbstractApiFilter filterInstance : filterInstances) {
            body = filterInstance.beforeReturnHandler(((ServletServerHttpRequest) request).getServletRequest(),
                    ((ServletServerHttpResponse) response).getServletResponse(), returnType.getMethod(), body);
        }
        return body;
    }
}
