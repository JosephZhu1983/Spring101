package me.josephzhu.spring101webmvc.framework;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface ApiController {
    @AliasFor(annotation = Controller.class)
    String value() default "";
}