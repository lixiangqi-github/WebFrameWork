package com.sgaop.web.frame.server.scanner;


import com.sgaop.web.frame.server.cache.CacheManager;
import com.sgaop.web.frame.server.mvc.ActionMethod;
import com.sgaop.web.frame.server.mvc.annotation.*;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by 30695 on 2016/5/8 0008.
 */
public class ClassScanner {

    public static void ScannerAllController() {
        Set<Class<?>> classes = ClassScannerHelper.getClasses("com.web.action");
        for (Class<?> ks : classes) {
            String classKey = ks.getName();
            WebController webController = ks.getAnnotation(WebController.class);
            if (webController != null) {
                Method[] methods = ks.getMethods();
                for (Method method : methods) {
                    WebAction webAction = method.getAnnotation(WebAction.class);
                    OK ok = method.getAnnotation(OK.class);
                    String relPaht = "";
                    if (webAction != null) {
                        if (webAction.path().length == 0) {
                            relPaht = webController.path() + "/" + method.getName();
                        } else {
                            for (String path : webAction.path()) {
                                relPaht = webController.path() + path;
                            }
                        }
                        String okVal = "";
                        if (ok != null) {
                            okVal = ok.value();
                        }
                        POST post = method.getAnnotation(POST.class);
                        if (post != null) {
                            CacheManager.putCache(relPaht, new ActionMethod("POST", classKey, ks, method, okVal));
                        }
                        DELETE delete = method.getAnnotation(DELETE.class);
                        if (delete != null) {
                            CacheManager.putCache(relPaht, new ActionMethod("DELETE", classKey, ks, method, okVal));
                        }
                        PUT put = method.getAnnotation(PUT.class);
                        if (put != null) {
                            CacheManager.putCache(relPaht, new ActionMethod("PUT", classKey, ks, method, okVal));
                        }
                        GET get = method.getAnnotation(GET.class);
                        if (get != null || post == null && get == null && delete == null && put == null) {
                            CacheManager.putCache(relPaht, new ActionMethod("GET", classKey, ks, method, okVal));
                        }
                    }
                }
            }
        }
    }

}
