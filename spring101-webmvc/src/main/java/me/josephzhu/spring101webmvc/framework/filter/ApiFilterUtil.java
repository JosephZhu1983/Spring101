package me.josephzhu.spring101webmvc.framework.filter;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuye
 * @date 2018/12/14
 */
public class ApiFilterUtil {

    public static List<AbstractApiFilter> getFilters(ApplicationContext applicationContext, Method method, boolean reversed) {

        List<ApiFilter> filters = new ArrayList<>();

        ApiFilter[] apiFiltersOnClass = method.getDeclaringClass().getAnnotationsByType(ApiFilter.class);
        if (apiFiltersOnClass.length>0) {
            for (ApiFilter apiFilter : apiFiltersOnClass) {
                filters.add(apiFilter);
            }
        }

        ApiFilter[] apiFiltersOnMethod = method.getAnnotationsByType(ApiFilter.class);
        if (apiFiltersOnMethod.length>0) {
            for (ApiFilter apiFilter : apiFiltersOnMethod) {
                filters.add(apiFilter);
            }
        }

        Comparator comparator = Comparator.comparing(ApiFilter::order);
        if (reversed)
            comparator = comparator.reversed();
        filters.sort(comparator);
        List<AbstractApiFilter> filterInstances = filters
                .stream()
                .map(filter -> applicationContext.getBean(filter.value()))
                .collect(Collectors.toList());
        return filterInstances;
    }
}
