package com.sgaop.web.frame.server.scanner;


import com.sgaop.web.frame.server.cache.CacheManager;
import com.sgaop.web.frame.server.cache.StaticCacheManager;
import com.sgaop.web.frame.server.constant.Constant;
import com.sgaop.web.frame.server.mvc.ActionMethod;
import com.sgaop.web.frame.server.mvc.annotation.*;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/8 0008
 * To change this template use File | Settings | File Templates.
 */
public class ClassScanner {

    public static void ScannerAllClass() {
        String baseScannerPackage = StaticCacheManager.getCache(Constant.BASE_SCANNER_PACKAGE).toString();
        Set<Class<?>> classes = ClassScannerHelper.getClasses(baseScannerPackage);
        for (Class<?> ks : classes) {
            String classKey = ks.getName();
            WebController webController = ks.getAnnotation(WebController.class);
            Setup setup = ks.getAnnotation(Setup.class);
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
            } else if (setup != null) {
                Method[] methods = ks.getMethods();
                for (Method method : methods) {
                    if ("init".equals(method.getName())) {
                        CacheManager.putCache(Constant.WEB_SETUP_INIT, new ActionMethod("init", classKey, ks, method, ""));
                    } else if ("destroy".equals(method.getName())) {
                        CacheManager.putCache(Constant.WEB_SETUP_DESTROY, new ActionMethod("destroy", classKey, ks, method, ""));
                    }
                }
            }
        }
    }
}
