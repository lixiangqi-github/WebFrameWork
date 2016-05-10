package com.web.action;

import com.sgaop.web.frame.server.mvc.annotation.*;
import com.web.action.pojo.AjaxRsult;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by 30695 on 2016/5/8 0008.
 */
@WebController(path = "/mainController")
public class MainController {


//    @OK("json")
    @OK("jsp:testpage.jsp")
    @GET
    @WebAction(path = "/index")
    public AjaxRsult index(
            @WebParam("id") int id,
            @WebParam("name") String name,
            @WebParam("age") int age,
            @WebParam("doubleNum") double doubleNum,
            @WebParam("flag") boolean flag,
            @WebParam("ids") String[] ids,
            HttpServletRequest request) {
        System.out.println("----" + id + "----" + name + "----" + age);
        System.out.println("mian index");
        return new AjaxRsult(true,"呵呵呵","json哦");
    }

}
