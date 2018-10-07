package me.josephzhu.spring101aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@Slf4j
@Order(1)
public class MetricsAspect {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Object getDefaultValue(String clazz) {
        if (clazz.equals("boolean")) {
            return false;
        } else if (clazz.equals("char")) {
            return '\u0000';
        } else if (clazz.equals("byte")) {
            return 0;
        } else if (clazz.equals("short")) {
            return 0;
        } else if (clazz.equals("int")) {
            return 0;
        } else if (clazz.equals("long")) {
            return 0L;
        } else if (clazz.equals("flat")) {
            return 0.0F;
        } else if (clazz.equals("double")) {
            return 0.0D;
        } else {
            return null;
        }
    }

    @Around("@annotation(me.josephzhu.spring101aop.Metrics) || @within(org.springframework.stereotype.Controller)")
    public Object metrics(ProceedingJoinPoint pjp) throws Throwable {
        //1
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Metrics metrics;
        String name;
        if (signature.getDeclaringType().isInterface()) {
            Class implClass = pjp.getTarget().getClass();
            Method method = implClass.getMethod(signature.getName(), signature.getParameterTypes());
            metrics = method.getDeclaredAnnotation(Metrics.class);
            name = String.format("【%s】【%s】", implClass.toString(), method.toString());
        } else {
            metrics = signature.getMethod().getAnnotation(Metrics.class);
            name = String.format("【%s】【%s】", signature.getDeclaringType().toString(), signature.toLongString());
        }
        //2
        if (metrics == null)
            metrics = new Metrics() {
                @Override
                public boolean logException() {
                    return true;
                }

                @Override
                public boolean logParameters() {
                    return true;
                }

                @Override
                public boolean logReturn() {
                    return true;
                }

                @Override
                public boolean recordFailMetrics() {
                    return true;
                }

                @Override
                public boolean recordSuccessMetrics() {
                    return true;
                }

                @Override
                public boolean ignoreException() {
                    return false;
                }

                @Override
                public Class<? extends Annotation> annotationType() {
                    return Metrics.class;
                }
            };
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null)
                name += String.format("【%s】", request.getRequestURL().toString());
        }
        //3
        if (metrics.logParameters())
            log.info(String.format("【入参日志】调用 %s 的参数是：【%s】", name, objectMapper.writeValueAsString(pjp.getArgs())));
        //4
        Object returnValue;
        Instant start = Instant.now();
        try {
            returnValue = pjp.proceed();
            if (metrics.recordSuccessMetrics())
                log.info(String.format("【成功打点】调用 %s 成功，耗时：%s", name, Duration.between(Instant.now(), start).toString()));
        } catch (Exception ex) {
            if (metrics.recordFailMetrics())
                log.info(String.format("【失败打点】调用 %s 失败，耗时：%s", name, Duration.between(Instant.now(), start).toString()));

            if (metrics.logException())
                log.error(String.format("【异常日志】调用 %s 出现异常！", name), ex);

            if (metrics.ignoreException())
                returnValue = getDefaultValue(signature.getReturnType().toString());
            else
                throw ex;
        }
        //5
        if (metrics.logReturn())
            log.info(String.format("【出参日志】调用 %s 的返回是：【%s】", name, returnValue));
        return returnValue;
    }

}
