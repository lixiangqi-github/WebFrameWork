package com.sgaop.web.frame.server.core;

import com.sgaop.web.frame.server.constant.Constant;
import com.sgaop.web.frame.server.mvc.ActionHandler;
import com.sgaop.web.frame.server.mvc.ActionResult;
import com.sgaop.web.frame.server.mvc.Mvcs;
import com.sgaop.web.frame.server.mvc.ViewsRender;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 30695 on 2016/5/8 0008.
 */
@WebFilter("/*")
public class FrameWebFilter implements Filter {
    private static final Logger logger = Logger.getRootLogger();

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器启动");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(Constant.utf8);
        servletResponse.setCharacterEncoding(Constant.utf8);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Mvcs.initLocal(servletRequest, servletResponse);
        String reqMethod = request.getMethod();
        String servletPath = request.getServletPath();
        logger.debug("通过 [" + reqMethod + "] 访问 [" + servletPath + "] 地址");
        ActionResult actionResult = ActionHandler.invokeAction(servletPath, reqMethod, request, response);
        String resultType = actionResult.getResultType();
        if (resultType.equals("json")) {
            ViewsRender.RenderJSON(response,actionResult.getResultData());
        } else if (resultType.startsWith("jsp:")) {
            String path[] = resultType.split(":");
            request.getRequestDispatcher("/WEB-INF/"+path[1]).forward(request, response);
        }else{
            logger.warn("没有设置返回类型 ["+servletPath+"]");
        }
        //filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        logger.info("过滤器销毁");
    }
}
