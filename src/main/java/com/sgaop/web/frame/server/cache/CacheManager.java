package com.sgaop.web.frame.server.cache;


import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/4 0005
 * To change this template use File | Settings | File Templates.
 */
public class CacheManager {

    private static HashMap cacheUrlClassMap = new HashMap();

    /**
     * 单实例构造方法
     */
    private CacheManager() {
        super();
    }

    /**
     * 得到缓存。同步静态方法
     *
     * @param key
     * @return
     */
    public synchronized static Object getCache(String key) {
        return cacheUrlClassMap.get(key);
    }

    /**
     * 得到缓存。同步静态方法
     *
     * @param key
     * @return
     */
    public static Object getCacheObj(String key) {
        return cacheUrlClassMap.get(key);
    }

    /**
     * 得到缓存
     *
     * @param key
     * @return
     */
    public static String getCacheStr(String key) {
        return cacheUrlClassMap.get(key).toString();
    }


    /**
     * 判断是否存在一个缓存
     *
     * @param key
     * @return
     */
    public synchronized static boolean hasCache(String key) {
        return cacheUrlClassMap.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public synchronized static void clearAll() {
        cacheUrlClassMap.clear();
    }

    /**
     * 清除指定的缓存
     *
     * @param key
     */
    public synchronized static void clearOnly(String key) {
        cacheUrlClassMap.remove(key);
    }

    /**
     * 载入缓存
     *
     * @param key
     * @param obj
     */
    public synchronized static void putCache(String key, Object obj) {
        cacheUrlClassMap.put(key, obj);
    }
} 
 