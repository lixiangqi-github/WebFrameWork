package com.sgaop.web.frame.server.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by 30695 on 2016/5/8 0008.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface WebParam {
    String value();
}
