package com.sgaop.web.frame.server.mvc;

import com.google.gson.Gson;
import com.sgaop.web.frame.server.constant.Constant;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by HuangChuan on 2016/5/10 0010.
 */
public class ViewsRender {

    private static final Logger logger = Logger.getRootLogger();

    public static void RenderJSON(HttpServletResponse response, Object resultObj) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding(Constant.utf8);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(new Gson().toJson(resultObj));
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            logger.error("返回客户端JSON数据出错", e);
            throw new RuntimeException(e);
        }
    }
}
