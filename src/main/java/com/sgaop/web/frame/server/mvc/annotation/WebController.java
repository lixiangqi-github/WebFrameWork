package com.sgaop.web.frame.server.mvc.annotation;

/**
 * Created by 30695 on 2016/5/8 0008.
 */

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface WebController {
    String path() default "";
}
