package me.josephzhu.spring101webmvc.framework.pojo;

import me.josephzhu.spring101webmvc.framework.ApiController;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author zhuye
 * @date 2018/12/14
 */
public class AutoRequestBodyProcessor extends RequestResponseBodyMethodProcessor {
    public AutoRequestBodyProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), ApiController.class) ||
                parameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        if (AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), ApiController.class)) {
            binder.validate();
        } else{
            Annotation[] annotations = parameter.getParameterAnnotations();
            for (Annotation ann : annotations) {
                Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
                if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                    Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
                    Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                    binder.validate(validationHints);
                    break;
                }
            }
        }
    }

    @Override
    protected boolean checkRequired(MethodParameter parameter) {
        if (AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), ApiController.class))
            return true;
        RequestBody requestBody = parameter.getParameterAnnotation(RequestBody.class);
        return (requestBody != null && requestBody.required() && !parameter.isOptional());
    }
}
