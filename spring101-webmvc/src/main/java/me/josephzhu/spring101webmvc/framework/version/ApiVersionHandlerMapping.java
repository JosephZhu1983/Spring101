package me.josephzhu.spring101webmvc.framework.version;

import me.josephzhu.spring101webmvc.framework.ApiController;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author zhuye
 * @date 2018/12/14
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);

        if (AnnotatedElementUtils.hasAnnotation(method.getDeclaringClass(), ApiController.class)) {
            if (!AnnotatedElementUtils.hasAnnotation(method.getDeclaringClass(), ApiVersion.class))
                throw new RuntimeException("@ApiController class must use @ApiVersion to specify base api version!");

            ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            if (methodAnnotation != null) {
                RequestCondition<?> methodCondition = getCustomMethodCondition(method);
                return createApiVersionInfo(methodAnnotation, methodCondition).combine(info);
            } else {
                ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
                if (typeAnnotation != null) {
                    RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                    return createApiVersionInfo(typeAnnotation, typeCondition).combine(info);
                }
            }
        }

        return info;
    }

    private RequestMappingInfo createApiVersionInfo(ApiVersion annotation, RequestCondition<?> customCondition) {
        String[] patterns = annotation.value();
        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(),
                new ParamsRequestCondition(),
                new HeadersRequestCondition(),
                new ConsumesRequestCondition(),
                new ProducesRequestCondition(),
                customCondition);
    }

}