package com.sgaop.web.frame.server.mvc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/8 0008
 * To change this template use File | Settings | File Templates.
 */
public class Mvcs {

    private static ThreadLocal<FrameRequest> local = new ThreadLocal();

    public static void initLocal(ServletRequest servletRequest, ServletResponse servletResponse, Map<String, ?> reqMap) {
        /*设置项目根路径*/
        servletRequest.setAttribute("base", servletRequest.getServletContext().getContextPath());
        local.set(new FrameRequest((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, reqMap));
    }

    public static HttpServletRequest getReq() {
        return local.get().getRequest();
    }

    public static HttpSession getSession() {
        return local.get().getRequest().getSession();
    }

    public static HttpServletResponse getResp() {
        return local.get().getResponse();
    }

    public static Map<String, ?> getReqMap() {
        return local.get().getReqMap();
    }
}
