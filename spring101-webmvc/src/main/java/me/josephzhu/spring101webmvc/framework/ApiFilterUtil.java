package me.josephzhu.spring101webmvc.framework;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ApiFilterUtil {

    public static List<AbstractApiFilter> getFilters(ApplicationContext applicationContext, Method method, boolean reversed) {
        ApiFilter[] apiFilters = method.getAnnotationsByType(ApiFilter.class);
        List<ApiFilter> filters = new ArrayList<>();
        if (apiFilters.length>0) {
            for (ApiFilter apiFilter : apiFilters) {
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
