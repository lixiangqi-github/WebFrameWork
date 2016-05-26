package com.sgaop.web.frame.server.util;

import com.sgaop.web.frame.server.mvc.upload.TempFile;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/5/13 0013
 * To change this template use File | Settings | File Templates.
 */
public class ClassTool {

    private static final Logger log = Logger.getRootLogger();

    public static Object ParamCast(Class<?> klazz, Object value) throws ParseException {
        Object val = null;
        if (value == null) {
            if (klazz.equals(String.class)) {
                val = "";
            } else if (klazz.equals(int.class) || klazz.equals(Integer.class) || klazz.equals(Long.class) || klazz.equals(long.class) || klazz.equals(double.class) || klazz.equals(Double.class) || klazz.equals(float.class) || klazz.equals(Float.class)) {
                val = 0;
            } else if (klazz.equals(String[].class)) {
                val = (String[]) new String[]{};
            } else if (klazz.equals(boolean.class) || klazz.equals(Boolean.class)) {
                val = false;
            }
        } else if (value instanceof Object[]) {
            if (klazz.equals(String.class)) {
                val = ((Object[]) value)[0];
            } else if (klazz.equals(String[].class)) {
                val = value;
            } else if (klazz.equals(int[].class)) {
                val = value;
            } else if (klazz.equals(int.class)) {
                val = Integer.valueOf(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(double.class)) {
                val = Double.valueOf(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(long.class)) {
                val = Long.valueOf(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(float.class)) {
                val = Float.valueOf(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(boolean.class)) {
                val = Boolean.valueOf(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(Date.class)) {
                val = DateTool.parseDate(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(java.sql.Date.class)) {
                val = DateTool.parseSqlDate(String.valueOf(((Object[]) value)[0]));
            } else if (klazz.equals(Timestamp.class)) {
                val = new Timestamp(DateTool.parseDate(String.valueOf(((Object[]) value)[0])).getTime());
            } else if (klazz.equals(TempFile.class)) {
                val = ((Object[]) value)[0];
            } else if (klazz.equals(TempFile[].class)) {
                val = value;
            } else {
                log.warn("没有识别到的类型[" + klazz.getName() + "]");
                throw new RuntimeException("没有识别到的类型[" + klazz.getName() + "]");
            }
        } else {
            if (klazz.equals(String.class)) {
                val = ((String[]) value)[0];
            } else if (klazz.equals(String[].class)) {
                val = (String[]) value;
            } else if (klazz.equals(int[].class)) {
                val = (int[]) value;
            } else if (klazz.equals(int.class)) {
                val = Integer.valueOf(((String[]) value)[0]);
            } else if (klazz.equals(double.class)) {
                val = Double.valueOf(((String[]) value)[0]);
            } else if (klazz.equals(long.class)) {
                val = Long.valueOf(((String[]) value)[0]);
            } else if (klazz.equals(float.class)) {
                val = Float.valueOf(((String[]) value)[0]);
            } else if (klazz.equals(boolean.class)) {
                val = Boolean.valueOf(((String[]) value)[0]);
            } else if (klazz.equals(Date.class)) {
                val = DateTool.parseDate(((String[]) value)[0]);
            } else if (klazz.equals(java.sql.Date.class)) {
                val = DateTool.parseSqlDate(((String[]) value)[0]);
            } else if (klazz.equals(Timestamp.class)) {
                val = new Timestamp(DateTool.parseDate(((String[]) value)[0]).getTime());
            } else {
                log.warn("没有识别到的类型[" + klazz.getName() + "]");
                throw new RuntimeException("没有识别到的类型[" + klazz.getName() + "]");
            }
        }
        return val;
    }

}
