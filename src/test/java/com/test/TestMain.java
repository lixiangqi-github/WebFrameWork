package com.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestMain {
    public static void main(String[] args) {
        Class clsss = TestMain.class;
        Method[] clsssMethods = clsss.getMethods();
        for (Method method : clsssMethods) {
            System.out.println("方法名：" + method.getName());
            Annotation[][] annotations = method.getParameterAnnotations();
            for (Annotation[] annotation : annotations) {
                for (Annotation anno : annotation) {
                    if (anno instanceof Param) {
                        System.out.println(((Param) anno).value());
                    }
                }
            }
        }
    }

    public void MyTestParamAnnotation(@Param("name") String name, int id) {
        System.out.println(name);
    }
}
