package com.sgaop.web.frame.server.mvc;

import com.sgaop.web.frame.server.cache.CacheManager;
import com.sgaop.web.frame.server.error.WebErrorMessage;
import com.sgaop.web.frame.server.mvc.annotation.WebParam;
import com.sgaop.web.frame.server.util.ClassTool;
import com.sgaop.web.frame.server.util.ParameterConverter;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HuangChuan on 2016/5/9 0009.
 */
public class ActionHandler {
    private static final Logger logger = Logger.getRootLogger();

    public static ActionResult invokeAction(String servletPath, String methodType, HttpServletRequest request, HttpServletResponse response) {
        WebErrorMessage webErrorMessage = new WebErrorMessage();
        webErrorMessage.setCode(200);
        ActionResult actionResult = new ActionResult();
        try {
            ActionMethod actionMethod = (ActionMethod) CacheManager.getCacheObj(servletPath);
            if (actionMethod != null) {
                actionResult.setResultType(actionMethod.getOK());
                if (methodType.equals(actionMethod.getMethod())) {
                    Class<?> actionClass = actionMethod.getActionClass();
                    Method handlerMethod = actionMethod.getActionMethod();
                    Object beanInstance = actionClass.newInstance();
                    handlerMethod.setAccessible(true);
                    Class<?>[] actionParamTypes = actionMethod.getActionMethod().getParameterTypes();

                    List<Object> actionParamList = new ArrayList<Object>();
                    Annotation[][] annotations = handlerMethod.getParameterAnnotations();


                    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

                    Map<String, ?>  requestParameterMap = request.getParameterMap();
                    if (isMultipart) {
                        requestParameterMap = ParameterConverter.bulidMultipartMap(request);
                    }
                    for (int i = 0; i < annotations.length; i++) {
                        Annotation[] annotation = annotations[i];
                        Class anClass = actionParamTypes[i];
                        if (annotation.length > 0) {
                            for (Annotation anno : annotation) {
                                if (anno instanceof WebParam) {
                                    String webParamKeyName = ((WebParam) anno).value();
                                    if (webParamKeyName.startsWith(">>")) {
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
                            webErrorMessage.setMessage("Action的参数,除HttpServletRequest,HttpServletResponse外必须使用@WebParam注解");
                            logger.warn(webErrorMessage.getMessage());
                        }
                    }
                    handlerMethod.setAccessible(true);
                    actionResult.setResultData(handlerMethod.invoke(beanInstance, actionParamList.toArray()));
                } else {
                    webErrorMessage.setCode(404);
                }
            } else {
                webErrorMessage.setCode(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            webErrorMessage.setCode(500);
            webErrorMessage.setException(e);
            logger.warn(webErrorMessage.getMessage());
        }
        if (webErrorMessage.getCode() == 404) {
            webErrorMessage.setMessage(" Not Found [" + methodType + "] URI=" + servletPath);
            logger.warn(webErrorMessage.getMessage());
        }
        actionResult.setWebErrorMessage(webErrorMessage);
        return actionResult;
    }
}
