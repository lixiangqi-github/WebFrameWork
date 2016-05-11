package com.sgaop.web.frame.server.core;

import com.sgaop.web.frame.server.cache.StaticCacheManager;
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

    private static final String STATIC_PATH = StaticCacheManager.getCache(Constant.STATIC_PATH_KEY).toString();

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器启动");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {

        try {
            servletRequest.setCharacterEncoding(Constant.utf8);
            servletResponse.setCharacterEncoding(Constant.utf8);
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Mvcs.initLocal(servletRequest, servletResponse);
            String reqMethod = request.getMethod();
            String servletPath = request.getServletPath();
            logger.debug("通过 [" + reqMethod + "] 访问 [" + servletPath + "] 地址");
            if (servletPath.endsWith(".jsp")) {
                ViewsRender.RenderJSP(servletPath, request, response);
                return;
            }
            if (STATIC_PATH != null) {
                boolean isStatic = false;
                String staticPaths[] = STATIC_PATH.split(",");
                out:
                for (String staticPath : staticPaths) {
                    if (servletPath.startsWith(staticPath)) {
                        isStatic = true;
                        break out;
                    }
                }
                if (!isStatic) {
                    ActionResult actionResult = ActionHandler.invokeAction(servletPath, reqMethod, request, response);
                    String resultType = actionResult.getResultType();
                    boolean error = true;
                    if (actionResult.getWebErrorMessage().getCode() != 500 && actionResult.getWebErrorMessage().getCode() != 404) {
                        if (resultType != null) {
                            if (resultType.equals("json")) {
                                error = false;
                                ViewsRender.RenderJSON(response, actionResult.getResultData());
                            } else if (resultType.startsWith("jsp:")) {
                                error = false;
                                String path[] = resultType.split(":");
                                ViewsRender.RenderJSP("/WEB-INF/" + path[1], request, response);
                                return;
                            }
                            if (resultType.startsWith("file")) {
                                ViewsRender.RenderFile(response, actionResult.getResultData());
                                return;
                            } else {
                                actionResult.getWebErrorMessage().setMessage("没有设置返回类型 [" + servletPath + "]");
                                logger.warn(actionResult.getWebErrorMessage().getMessage());
                            }
                        }
                    }
                    if (error) {
                        ViewsRender.RenderErrorPage(response, actionResult.getWebErrorMessage());
                        return;
                    }
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            } else {
                logger.error("没有设置静态WEB资源文件文件目录，可能会导致无法访问WEB资源文件");
            }
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        logger.info("过滤器销毁");
    }
}
