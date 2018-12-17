package me.josephzhu.spring101webmvc.framework.version;

import me.josephzhu.spring101webmvc.framework.ApiController;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zhuye
 * @date 2018/12/14
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        Class<?> controllerClass = method.getDeclaringClass();

        if (AnnotatedElementUtils.hasAnnotation(controllerClass, ApiController.class)) {

            ApiVersion apiVersion = controllerClass.getAnnotation(ApiVersion.class);
            if (apiVersion == null)
                throw new RuntimeException("@ApiController class must use @ApiVersion to specify base api version!");

            ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            if (methodAnnotation != null)
                apiVersion = methodAnnotation;

            String controllerName = controllerClass.getSimpleName().toLowerCase();
            final String resourceName = controllerName.contains("controller") ? controllerName.substring(0, controllerName.lastIndexOf("controller")) : "";
            String[] urlPatterns = apiVersion.value();
            if (!StringUtils.isEmpty(resourceName) && !AnnotatedElementUtils.hasAnnotation(controllerClass, RequestMapping.class)) {
                urlPatterns = Arrays.asList(urlPatterns).stream().map(item -> item + "/" + resourceName).toArray(String[]::new);
            }

            PatternsRequestCondition apiPattern = new PatternsRequestCondition(urlPatterns);
            PatternsRequestCondition oldPattern = mapping.getPatternsCondition();
            PatternsRequestCondition updatedFinalPattern = apiPattern.combine(oldPattern);
            mapping = new RequestMappingInfo(
                    mapping.getName(),
                    updatedFinalPattern,
                    mapping.getMethodsCondition(),
                    mapping.getParamsCondition(),
                    mapping.getHeadersCondition(),
                    mapping.getConsumesCondition(),
                    mapping.getProducesCondition(),
                    mapping.getCustomCondition()
            );
        }

        super.registerHandlerMethod(handler, method, mapping);
    }
}