package com.sgaop.web.frame.server.util;

import com.sgaop.web.frame.server.cache.StaticCacheManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * TODO 需要优化，批量扫描配置文件，全部加载到StaticCacheManager中
 */
public class ProperScanner {

    private static Properties props = new Properties();

    public static void init() {
        InputStream in = null;
        try {
            ClassLoader classLoader = ProperScanner.class.getClassLoader();
            String path = String.valueOf(classLoader.getResource("/web.properties"));
            File file = new File(path);
            if (file.isFile()) {
                in = new FileInputStream(file);
                props.load(in);
            } else {
                try {
                    path = ProperScanner.class.getResource("/web.properties").toURI().getPath();
                    in = new FileInputStream(new File(path));
                    props.load(in);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            StaticCacheManager.putCache(props);
        } catch (IOException e) {
            e.printStackTrace();
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
}
