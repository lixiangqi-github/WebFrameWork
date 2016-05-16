package test.web.action;

import com.google.gson.Gson;
import com.sgaop.web.frame.server.dao.DBConn;
import com.sgaop.web.frame.server.mvc.annotation.*;
import test.web.action.bean.TestbuildBean;
import test.web.action.pojo.AjaxRsult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;



/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/8 0008
 * To change this template use File | Settings | File Templates.
 */
@WebController(path = "/mainController")
public class MainController {


    //    @OK("json")
    @OK("jsp:testpage.jsp")
// 或者这样   @OK("fw:testpage.jsp")
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

        request.setAttribute("test", "测试request.setAttribute");
        return new AjaxRsult(true, "呵呵呵", "json哦");
    }


    @OK("rd:testpage.jsp")
    @GET
    @WebAction(path = "/testpage")
    public void testpage() {
        System.out.println("---testpage");
    }

    @OK("json")
    @POST
    @WebAction(path = "/buildBeanFile")
    public AjaxRsult buildBean(@WebParam(">>data") TestbuildBean bean,@WebParam("docName")File docName) {
        System.out.println(new Gson().toJson(bean));
        System.out.println(docName.getName());
        return new AjaxRsult(true, "呵呵呵", bean);
    }


    @OK("json")
    @POST
    @WebAction(path = "/buildBean")
    public AjaxRsult buildBean(@WebParam(">>data") TestbuildBean bean) {
        System.out.println(new Gson().toJson(bean));
        Connection connection=DBConn.getDbConn();
        System.out.println(connection);
        return new AjaxRsult(true, "呵呵呵", bean);
    }

//    @OK("json")
//    @POST
//    @WebAction(path = "/buildBean")
//    public AjaxRsult buildBean(@WebParam("docName")File docName,@WebParam("data.name")String name,@WebParam("data.age")int age) {
//        System.out.println(docName.getName());
//        System.out.println(name+"---"+age);
//        return new AjaxRsult(true, "呵呵呵", "");
//    }




    @OK("file")
    @GET
    @WebAction(path = "/dowload")
    public File dowloadFile() {
        return new File("D:/site-1.8.9.zip");
    }

}
