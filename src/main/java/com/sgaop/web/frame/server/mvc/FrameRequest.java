package com.sgaop.web.frame.server.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/8 0008
 * To change this template use File | Settings | File Templates.
 */
public class FrameRequest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, ?> reqMap;


    public FrameRequest(HttpServletRequest request, HttpServletResponse response, Map<String, ?> reqMap) {
        this.request = request;
        this.response = response;
        this.reqMap = reqMap;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Map<String, ?> getReqMap() {
        return reqMap;
    }


}
