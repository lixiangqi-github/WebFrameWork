package com.sgaop.web.frame.server.util;

import com.sgaop.web.frame.server.constant.Constant;

import java.io.*;

/**
 * Created by HuangChuan on 2016/5/13 0013.
 */
public class IoTool {


    public static File writeFile(InputStream in, String fileName) throws IOException {
        String pathName = Constant.WEB_TEMP_PATH + "/" + fileName;
        System.out.println(pathName);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[Constant.BASE_BYTE];
        int count = -1;
        while ((count = in.read(data, 0, Constant.BASE_BYTE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        File file = new File(pathName);
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(outStream.toByteArray());
        fout.flush();
        fout.close();
        return file;
    }


    /**
     * 将InputStream转换成String
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[Constant.BASE_BYTE];
        int count = -1;
        while ((count = in.read(data, 0, Constant.BASE_BYTE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(), "UTF-8");

    }
}
