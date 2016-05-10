package com.sgaop.web.frame.server.mvc;

import com.sgaop.web.frame.server.error.WebErrorMessage;

/**
 * Created by HuangChuan on 2016/5/10 0010.
 */
public class ActionResult {
    private WebErrorMessage webErrorMessage;
    private Object resultData;
    private String resultType;

    public WebErrorMessage getWebErrorMessage() {
        return webErrorMessage;
    }

    public void setWebErrorMessage(WebErrorMessage webErrorMessage) {
        this.webErrorMessage = webErrorMessage;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
