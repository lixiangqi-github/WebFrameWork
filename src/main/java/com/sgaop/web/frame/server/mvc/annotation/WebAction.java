package com.sgaop.web.frame.server.mvc.annotation;

/**
 * Created by 30695 on 2016/5/8 0008.
 */


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebAction {

    /**
     * 需要映射的路径,可以多个
     */
    String[] path() default {};

}
