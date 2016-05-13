package com.sgaop.web.frame.server.util;

import com.sgaop.web.frame.server.cache.StaticCacheManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ProperScanner {

    private static final Logger logger = Logger.getRootLogger();

    public static void init() {
        InputStream in = null;
        try {
            String path = ProperScanner.class.getClassLoader().getResource("").getPath();
            List<String> listPath = new ArrayList<String>();
            ScannerProperties(path, listPath);
            for (String filePath : listPath) {
                File file = new File(filePath);
                in = new FileInputStream(file);
                Properties props = new Properties();
                props.load(in);
                StaticCacheManager.putCache(props);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("加载配置文件出错", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void ScannerProperties(String path, List<String> listPath) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isDirectory()) || (file.getName().endsWith(".properties"));
            }
        });
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                ScannerProperties(file.getAbsolutePath(), listPath);
            } else {
                listPath.add(file.getAbsolutePath());
            }
        }
    }


}
