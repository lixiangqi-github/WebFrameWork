package test.web.action;

import com.google.gson.Gson;
import com.sgaop.web.frame.server.dao.DBConn;
import com.sgaop.web.frame.server.mvc.annotation.*;
import com.sgaop.web.frame.server.mvc.upload.TempFile;
import com.sgaop.web.frame.server.pojo.AjaxRsult;
import com.sgaop.web.frame.server.util.IoTool;
import test.web.action.bean.TestbuildBean;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;



/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/8 0008
 * To change this template use File | Settings | File Templates.
 */
@WebController("/mainController")
public class MainController {

    //    @OK("json")
    @OK("jsp:testpage.jsp")
// 或者这样   @OK("fw:testpage.jsp")
    @GET
    @Path
    public AjaxRsult index123(
            @Parameter("id") int id,
            @Parameter("name") String name,
            @Parameter("age") int age,
            @Parameter("doubleNum") double doubleNum,
            @Parameter("flag") boolean flag,
            @Parameter("ids") String[] ids,
            HttpServletRequest request) {
        System.out.println("----" + id + "----" + name + "----" + age);
        System.out.println("mian index");
        request.setAttribute("test", "测试request.setAttribute");
        return new AjaxRsult(true, "呵呵呵", "json哦");
    }

    @OK("rd:testpage.jsp")
    @GET
    @Path("/testpage")
    public void testpage() {
        System.out.println("---testpage");
    }

    @OK("json")
    @POST
    @Path("/buildBeanFile")
    public AjaxRsult buildBeanFile(@Parameter("data>>") TestbuildBean bean, @Parameter("docName")TempFile docName) {
        System.out.println(new Gson().toJson(bean));
        System.out.println(docName.getName());
        try {
            IoTool.writeFile(docName.getInputStream(),"d:\\"+docName.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AjaxRsult(true, "呵呵呵", bean);
    }

    @OK("json")
    @POST
    @Path("/buildBeanFiles")
    public AjaxRsult buildBeanFiles(@Parameter("data>>") TestbuildBean bean, @Parameter("docName")TempFile[] docName) {
        System.out.println(new Gson().toJson(bean));
        for(TempFile file:docName){
            System.out.println(file.getName());
            System.out.println(file.getContentType());
        }
        return new AjaxRsult(true, "批量文件上传", bean);
    }


    @OK("json")
    @POST
    @Path("/buildBean")
    public AjaxRsult buildBean(@Parameter("data>>") TestbuildBean bean) {
        System.out.println(new Gson().toJson(bean));
        Connection connection=DBConn.getDbConn();
        System.out.println(connection);
        return new AjaxRsult(true, "呵呵呵", bean);
    }


    @OK("file")
    @GET
    @Path("/dowload")
    public File dowloadFile() {
        return new File("D:/site-1.8.9.zip");
    }

}
