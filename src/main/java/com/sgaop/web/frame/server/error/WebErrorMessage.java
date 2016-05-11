package com.sgaop.web.frame.server.error;

/**
 * Created by 30695 on 2016/5/10 0010.
 */
public class WebErrorMessage {

    private int code;
    private String message;

    private Exception exception;

    public WebErrorMessage() {

    }

    public WebErrorMessage(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public WebErrorMessage(int code, String message, Exception exception) {
        this.code = code;
        this.message = message;
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
