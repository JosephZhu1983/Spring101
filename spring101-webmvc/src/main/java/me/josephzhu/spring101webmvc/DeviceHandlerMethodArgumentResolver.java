package me.josephzhu.spring101webmvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DeviceHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(MyDevice.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        MyDevice myDevice = new MyDevice();
        myDevice.setType(nativeWebRequest.getHeader("device.type"));
        myDevice.setVersion(nativeWebRequest.getHeader("device.version"));
        myDevice.setScreen(nativeWebRequest.getHeader("device.screen"));
        return myDevice;
    }
}
