package com.sgaop.web.frame.server.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/10 0010
 * To change this template use File | Settings | File Templates.
 */
public class AjaxRsult<T> {

    private boolean ok;
    private String msg;
    private T data;

    public AjaxRsult(boolean ok, String msg) {
        this.ok = ok;
        this.msg = msg;
    }

    public AjaxRsult(boolean ok, String msg, T data) {
        this.ok = ok;
        this.msg = msg;
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
