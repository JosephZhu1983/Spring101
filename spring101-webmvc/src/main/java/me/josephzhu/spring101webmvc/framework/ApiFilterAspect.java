package me.josephzhu.spring101webmvc.framework;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class ApiFilterAspect {

    @Autowired
    ApplicationContext applicationContext;

    @Around("@within(me.josephzhu.spring101webmvc.framework.ApiController)")
    public Object metrics(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        ApiFilter[] apiFilters = signature.getMethod().getAnnotationsByType(ApiFilter.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        List<ApiFilter> filters = new ArrayList<>();
        if (apiFilters.length>0) {
            for (ApiFilter apiFilter : apiFilters) {
                filters.add(apiFilter);
            }
        }
        filters.sort(Comparator.comparing(ApiFilter::order));
        List<AbstractApiFilter> filterInstances = filters
                .stream()
                .map(filter -> applicationContext.getBean(filter.value()))
                .collect(Collectors.toList());
        filterInstances.forEach(filter->filter.preAction(request,response));
        try {
            return pjp.proceed();
        } finally {
            Collections.reverse(filterInstances);
            filterInstances.forEach(filter->filter.postAction(request,response));
        }
    }
}
