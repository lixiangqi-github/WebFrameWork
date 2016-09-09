package com.sgaop.web.frame.server.mvc;

import com.sgaop.web.frame.server.cache.CacheManager;
import com.sgaop.web.frame.server.constant.Constant;
import com.sgaop.web.frame.server.error.WebErrorMessage;
import com.sgaop.web.frame.server.mvc.annotation.Parameter;
import com.sgaop.web.frame.server.util.ClassTool;
import com.sgaop.web.frame.server.util.ParameterConverter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/9 0009
 * To change this template use File | Settings | File Templates.
 */
public class ActionHandler {
    private static final Logger logger = Logger.getRootLogger();

    public static ActionResult invokeAction(String servletPath, String methodType, HttpServletRequest request, HttpServletResponse response) {
        WebErrorMessage webErrorMessage = new WebErrorMessage();
        webErrorMessage.setCode(200);
        ActionResult actionResult = new ActionResult();
        try {
            ActionMethod actionMethod = (ActionMethod) CacheManager.getUrlCache(servletPath);
            if (actionMethod != null) {
                actionResult.setResultType(actionMethod.getOK());
                if (methodType.equals(actionMethod.getMethod())) {
                    Class<?> actionClass = actionMethod.getActionClass();
                    Method handlerMethod = actionMethod.getActionMethod();
                    Object beanInstance = actionClass.newInstance();
                    Class<?>[] actionParamTypes = actionMethod.getActionMethod().getParameterTypes();
                    List<Object> actionParamList = new ArrayList<Object>();
                    Annotation[][] annotations = handlerMethod.getParameterAnnotations();
                    Map<String, ?> requestParameterMap = Mvcs.getReqMap();
                    for (int i = 0; i < annotations.length; i++) {
                        Annotation[] annotation = annotations[i];
                        Class anClass = actionParamTypes[i];
                        if (annotation.length > 0) {
                            for (Annotation anno : annotation) {
                                if (anno instanceof Parameter) {
                                    String webParamKeyName = ((Parameter) anno).value();
                                    if (webParamKeyName.endsWith(">>")) {
                                        webParamKeyName = webParamKeyName.replace(">>", "");
                                        actionParamList.add(ParameterConverter.bulid(anClass, webParamKeyName, requestParameterMap));
                                    } else {
                                        Object ParamValuesObject = requestParameterMap.get(webParamKeyName);
                                        Object val = ClassTool.ParamCast(anClass, ParamValuesObject);
                                        actionParamList.add(val);
                                    }
                                }
                            }
                        } else if (anClass.equals(HttpServletRequest.class)) {
                            actionParamList.add(request);
                        } else if (anClass.equals(HttpServletResponse.class)) {
                            actionParamList.add(response);
                        } else {
                            webErrorMessage.setCode(500);
                            webErrorMessage.setMessage("Action的参数,除HttpServletRequest,HttpServletResponse外必须使用@" + Parameter.class + "注解");
                            logger.warn(webErrorMessage.getMessage());
                        }
                    }
                    handlerMethod.setAccessible(true);
                    actionResult.setResultData(handlerMethod.invoke(beanInstance, actionParamList.toArray()));
                } else {
                    webErrorMessage.setCode(404);
                }
            } else if (servletPath.endsWith(Constant.PAGE_SUFFIX)) {
                /**
                 * 在没有找到注解的情况下，并且访问的是jsp文件
                 */
                webErrorMessage.setJsp(true);
            } else {
                webErrorMessage.setCode(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            webErrorMessage.setCode(500);
            webErrorMessage.setException(e);
            logger.debug(e.getMessage());
        }
        if (webErrorMessage.getCode() == 404) {
            webErrorMessage.setMessage("  [" + methodType + "] Not Found URI=" + servletPath);
            logger.debug(webErrorMessage.getMessage());
        }
        actionResult.setWebErrorMessage(webErrorMessage);
        return actionResult;
    }
}
