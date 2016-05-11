package com.sgaop.web.frame.server.core;


import com.sgaop.web.frame.server.scanner.ClassScanner;
import com.sgaop.web.frame.server.util.ProperScanner;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by 30695 on 2016/5/8 0008.
 */
@WebListener
public class ServletInitListener implements ServletContextListener {
    private static final Logger logger = Logger.getRootLogger();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //TODO 启动连接池
        ProperScanner.init();
        ClassScanner.ScannerAllController();
        logger.info("服务启动");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("服务销毁");
    }
}
