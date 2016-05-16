package com.web.action;

import com.sgaop.web.frame.server.mvc.annotation.WebAction;
import com.sgaop.web.frame.server.mvc.annotation.WebController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/8 0008
 * To change this template use File | Settings | File Templates.
 */
@WebController(path = "/testController")
public class TestController {

    @WebAction(path = "/3")
    public void testAction(HttpServletRequest request) {
        System.out.println("xxxx1");
    }

    @WebAction
    public void test() {
        System.out.println("test");
    }
}
