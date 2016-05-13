/**
 * Created by HuangChuan on 2016/5/13 0013.
 */
package com.sgaop.web.frame.server.util;
//
///**
// * 保存对象，并返回主键
// *
// * @param dbConn
// * @param t
// * @return
// */
//public static <T> int updataSingle(Connection conn, Class cls, T t,
//        String whereSql) {
//        int rs = 0;
//        PreparedStatement pstm = null;
//        String sql = "UPDATE   " + cls.getSimpleName()
//        + " set  @updatelist where " + whereSql;
//        try {
//        String cloum = "";
//        Object obj = cls.newInstance();
//
//        Field[] fieldArray = cls.getDeclaredFields();
//        for (int i = 0; i < fieldArray.length; i++) {
//        Field field = fieldArray[i];
//        String fieldName = field.getName();
//        // 普通字段情况
//        String methodName = "get"
//        + fieldName.substring(0, 1).toUpperCase()
//        + fieldName.substring(1);
//        Object val = cls.getMethod(methodName).invoke(t);
//        if (val != null) {
//        sql = sql.replaceFirst("@updatelist", fieldName
//        + "=? ,@updatelist");
//        }
//        }
//        sql = sql.replaceFirst(",@updatelist", "");
//        pstm = conn.prepareStatement(sql);
//
//        int num = 1;
//        for (int i = 0; i < fieldArray.length; i++) {
//        Field field = fieldArray[i];
//        Class fieldType = field.getType();
//        String fieldName = field.getName();
//        // 普通字段情况
//        String methodName = "get"
//        + fieldName.substring(0, 1).toUpperCase()
//        + fieldName.substring(1);
//        Object val = cls.getMethod(methodName).invoke(t);
//        if (val != null) {
//        if (fieldType.equals(int.class)) {
//        pstm.setInt(num, (int) val);
//        } else if (fieldType.equals(Integer.class)) {
//        pstm.setInt(num, (int) val);
//        } else if (fieldType.equals(long.class)) {
//        pstm.setLong(num, (long) val);
//        } else if (fieldType.equals(Long.class)) {
//        pstm.setLong(num, (long) val);
//        } else if (fieldType.equals(double.class)) {
//        pstm.setDouble(num, (double) val);
//        } else if (fieldType.equals(Double.class)) {
//        pstm.setDouble(num, (double) val);
//        } else if (fieldType.equals(String.class)) {
//        pstm.setString(num, (String) val);
//        } else if (fieldType.equals(java.util.Date.class)) {
//        pstm.setTimestamp(num, new java.sql.Timestamp(
//        (((java.util.Date) val).getTime())));
//        } else if (fieldType.equals(java.sql.Date.class)) {
//        pstm.setDate(num, (java.sql.Date) val);
//        } else if (fieldType.equals(boolean.class)) {
//        pstm.setBoolean(num, (boolean) val);
//        } else if (fieldType.equals(Boolean.class)) {
//        pstm.setBoolean(num, (boolean) val);
//        } else {
//        pstm.setString(num, String.valueOf(val));
//        }
//        num++;
//        }
//        }
//        System.out.println("sql:" + pstm.toString());
//        rs = pstm.executeUpdate();
//        } catch (Exception e) {
//        e.printStackTrace();
//        } finally {
//        YHDBUtility.close(pstm, null, null);
//        }
//        return rs;
//        }
//
///**
// * 查询单个对象
// *
// * @param dbConn
// * @param t
// * @return
// */
//public static <T> T loadSingle(Connection conn, Class cls, String whereSql, Object[] par) {
//        Object pojo=null;
//        try {
//        pojo = cls.newInstance();
//        } catch (InstantiationException e1) {
//        e1.printStackTrace();
//        } catch (IllegalAccessException e1) {
//        e1.printStackTrace();
//        }
//        PreparedStatement pstm = null;
//        String sql = "select * from  " + cls.getSimpleName().toLowerCase() + " where "+ whereSql;
//        int i = 0;
//        String name = null;
//        try {
//        pstm = conn.prepareStatement(sql);
//        for (int x = 1; x <= par.length; x++) {
//        pstm.setObject(x, par[x - 1]);
//        }
//
//        System.out.println(pstm.toString());
//        ResultSet rs = pstm.executeQuery();
//        while(rs.next()){
//        ResultSetMetaData meta = rs.getMetaData();
//        int count = meta.getColumnCount();
//        for (i = 1; i <= count; i++) {
//        name = meta.getColumnLabel(i);
//        int dbType = meta.getColumnType(i);
//        String setMethodName = "set"+ name.substring(0, 1).toUpperCase()+ name.substring(1);
//        Method method = null;
//        if (dbType == Types.TINYINT) {
//        method = cls.getMethod(setMethodName, byte.class);
//        method.invoke(pojo, rs.getByte(i));
//        } else if (dbType == Types.SMALLINT) {
//        method = cls.getMethod(setMethodName, short.class);
//        method.invoke(pojo, rs.getShort(i));
//        } else if (dbType == Types.INTEGER || dbType == Types.NUMERIC) {
//        try {
//        method = cls.getMethod(setMethodName, int.class);
//        method.invoke(pojo, rs.getInt(i));
//        } catch (NoSuchMethodException e) {
//        method = cls.getMethod(setMethodName, double.class);
//        method.invoke(pojo, rs.getDouble(i));
//        }
//        } else if (dbType == Types.BIGINT) {
//        method = cls.getMethod(setMethodName, long.class);
//        method.invoke(pojo, rs.getLong(i));
//        } else if (dbType == Types.FLOAT || dbType == Types.REAL) {
//        method = cls.getMethod(setMethodName, float.class);
//        method.invoke(pojo, rs.getFloat(i));
//        } else if (dbType == Types.DOUBLE) {
//        method = cls.getMethod(setMethodName, double.class);
//        method.invoke(pojo, rs.getDouble(i));
//        } else if (dbType == Types.DECIMAL) {
//        method = cls.getMethod(setMethodName, double.class);
//        method.invoke(pojo, rs.getDouble(i));
//        } else if (dbType == Types.BIT) {
//        method = cls.getMethod(setMethodName, boolean.class);
//        method.invoke(pojo, rs.getBoolean(i));
//        } else if (dbType == Types.CHAR || dbType == Types.VARCHAR
//        || dbType == Types.LONGVARCHAR) {
//        method = cls.getMethod(setMethodName, String.class);
//        method.invoke(pojo, rs.getString(i));
//        } else if (dbType == Types.CLOB) {
//        String clobStr = "";
//        Clob cl = rs.getClob(i);
//        method = cls.getMethod(setMethodName, String.class);
//        method.invoke(pojo, clobToString(cl));
//        } else if (dbType == Types.DATE) { // 继承于 java.util.Date 类
//        method = cls.getMethod(setMethodName, java.util.Date.class);
//        java.util.Date date = rs.getDate(i);
//        method.invoke(pojo, date);
//        } else if (dbType == Types.TIME) { // 继承于 java.util.Date 类
//        method = cls.getMethod(setMethodName, java.util.Date.class);
//        method.invoke(pojo, rs.getTime(i));
//        } else if (dbType == Types.TIMESTAMP) { // 继承于 java.util.Date 类
//        method = cls.getMethod(setMethodName, java.util.Date.class);
//        if (rs.getTimestamp(i) != null) {
//        method.invoke(pojo, rs.getTimestamp(i));
//        }
//        } else if (dbType == Types.BINARY || dbType == Types.VARBINARY || dbType == Types.LONGVARBINARY || dbType == Types.BLOB) {
//        method = cls.getMethod(setMethodName, byte[].class);
//        method.invoke(pojo, rs.getBytes(i));
//        } else {
//        throw new Exception("数据库中包含不支持的自动映射数据类型：" + dbType);
//        }
//
//        }
//        }
//        } catch (Exception e) {
//        e.printStackTrace();
//        } finally {
//        YHDBUtility.close(pstm, null, null);
//        }
//        return  (T) pojo;
//        }
//
//
///**
// * 查询单个对象
// *
// * @param dbConn
// * @param t
// * @return
// */
//public static <T> T loadList(Connection conn, Class cls, String whereSql, Object[] par) {
//        List<T> list=new ArrayList<>();
//        PreparedStatement pstm = null;
//        String sql = "select * from  " + cls.getSimpleName().toLowerCase() + " where "+ whereSql;
//        int i = 0;
//        String name = null;
//        try {
//        pstm = conn.prepareStatement(sql);
//        for (int x = 1; x <= par.length; x++) {
//        pstm.setObject(x, par[x - 1]);
//        }
//        System.out.println(pstm.toString());
//        ResultSet rs = pstm.executeQuery();
//        while(rs.next()){
//        Object pojo = cls.newInstance();
//        ResultSetMetaData meta = rs.getMetaData();
//        int count = meta.getColumnCount();
//        for (i = 1; i <= count; i++) {
//        name = meta.getColumnLabel(i);
//        int dbType = meta.getColumnType(i);
//        String setMethodName = "set"+ name.substring(0, 1).toUpperCase()+ name.substring(1);
//        Method method = null;
//        if (dbType == Types.TINYINT) {
//        method = cls.getMethod(setMethodName, byte.class);
//        method.invoke(pojo, rs.getByte(i));
//        } else if (dbType == Types.SMALLINT) {
//        method = cls.getMethod(setMethodName, short.class);
//        method.invoke(pojo, rs.getShort(i));
//        } else if (dbType == Types.INTEGER || dbType == Types.NUMERIC) {
//        try {
//        method = cls.getMethod(setMethodName, int.class);
//        method.invoke(pojo, rs.getInt(i));
//        } catch (NoSuchMethodException e) {
//        method = cls.getMethod(setMethodName, double.class);
//        method.invoke(pojo, rs.getDouble(i));
//        }
//        } else if (dbType == Types.BIGINT) {
//        method = cls.getMethod(setMethodName, long.class);
//        method.invoke(pojo, rs.getLong(i));
//        } else if (dbType == Types.FLOAT || dbType == Types.REAL) {
//        method = cls.getMethod(setMethodName, float.class);
//        method.invoke(pojo, rs.getFloat(i));
//        } else if (dbType == Types.DOUBLE) {
//        method = cls.getMethod(setMethodName, double.class);
//        method.invoke(pojo, rs.getDouble(i));
//        } else if (dbType == Types.DECIMAL) {
//        method = cls.getMethod(setMethodName, double.class);
//        method.invoke(pojo, rs.getDouble(i));
//        } else if (dbType == Types.BIT) {
//        method = cls.getMethod(setMethodName, boolean.class);
//        method.invoke(pojo, rs.getBoolean(i));
//        } else if (dbType == Types.CHAR || dbType == Types.VARCHAR
//        || dbType == Types.LONGVARCHAR) {
//        method = cls.getMethod(setMethodName, String.class);
//        method.invoke(pojo, rs.getString(i));
//        } else if (dbType == Types.CLOB) {
//        String clobStr = "";
//        Clob cl = rs.getClob(i);
//        method = cls.getMethod(setMethodName, String.class);
//        method.invoke(pojo, clobToString(cl));
//        } else if (dbType == Types.DATE) { // 继承于 java.util.Date 类
//        method = cls.getMethod(setMethodName, java.util.Date.class);
//        java.util.Date date = rs.getDate(i);
//        method.invoke(pojo, date);
//        } else if (dbType == Types.TIME) { // 继承于 java.util.Date 类
//        method = cls.getMethod(setMethodName, java.util.Date.class);
//        method.invoke(pojo, rs.getTime(i));
//        } else if (dbType == Types.TIMESTAMP) { // 继承于 java.util.Date 类
//        method = cls.getMethod(setMethodName, java.util.Date.class);
//        if (rs.getTimestamp(i) != null) {
//        method.invoke(pojo, rs.getTimestamp(i));
//        }
//        } else if (dbType == Types.BINARY || dbType == Types.VARBINARY || dbType == Types.LONGVARBINARY || dbType == Types.BLOB) {
//        method = cls.getMethod(setMethodName, byte[].class);
//        method.invoke(pojo, rs.getBytes(i));
//        } else {
//        throw new Exception("数据库中包含不支持的自动映射数据类型：" + dbType);
//        }
//        }
//        list.add((T) pojo);
//        }
//        } catch (Exception e) {
//        e.printStackTrace();
//        } finally {
//        YHDBUtility.close(pstm, null, null);
//        }
//        T list2 = (T) list;
//        return list2;
//        }
//
//public static String clobToString(Clob cl) throws Exception {
//        String res = "";
//        Reader is = null;
//        if (cl == null) {
//        return "";
//        }
//        try {
//        is = cl.getCharacterStream();// 得到流
//        BufferedReader br = new BufferedReader(is);
//        String s = br.readLine();
//        StringBuffer sb = new StringBuffer();
//        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
//        sb.append(s);
//        s = br.readLine();
//        if (s != null) {
//        sb.append("\r\n");
//        }
//        }
//        res = sb.toString();
//        // System.out.println(res);
//        return res;
//        } catch (Exception e) {
//        e.printStackTrace();
//        throw e;
//        } finally {
//        is.close();
//        }
//        }
//
//
///**
// * 保存对象，并返回主键
// *
// * @param dbConn
// * @param t
// * @return
// */
//public static <T> long saveSingle(Connection conn, Class cls, T t) {
//        long id = 0;
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        String sql = "insert into " + cls.getSimpleName()
//        + " (@keyname) values(@keyvalargs)";
//        try {
//        String cloum = "";
//        Object obj = cls.newInstance();
//
//        Field[] fieldArray = cls.getDeclaredFields();
//        for (int i = 0; i < fieldArray.length; i++) {
//        Field field = fieldArray[i];
//        String fieldName = field.getName();
//        // 普通字段情况
//        String methodName = "get"
//        + fieldName.substring(0, 1).toUpperCase()
//        + fieldName.substring(1);
//        Object val = cls.getMethod(methodName).invoke(t);
//        if (val != null) {
//        sql = sql.replaceFirst("@keyname", fieldName + ",@keyname");
//        sql = sql.replaceFirst("@keyvalargs", "?,@keyvalargs");
//        }
//        }
//        sql = sql.replaceFirst(",@keyname", "");
//        sql = sql.replaceFirst(",@keyvalargs", "");
//        // 指定返回生成的主键
//        pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        int num = 1;
//        for (int i = 0; i < fieldArray.length; i++) {
//        Field field = fieldArray[i];
//        Class fieldType = field.getType();
//        String fieldName = field.getName();
//        // 普通字段情况
//        String methodName = "get"
//        + fieldName.substring(0, 1).toUpperCase()
//        + fieldName.substring(1);
//        Object val = cls.getMethod(methodName).invoke(t);
//        if (val != null) {
//        if (fieldType.equals(int.class)) {
//        pstm.setInt(num, (int) val);
//        } else if (fieldType.equals(Integer.class)) {
//        pstm.setInt(num, (int) val);
//        } else if (fieldType.equals(long.class)) {
//        pstm.setLong(num, (long) val);
//        } else if (fieldType.equals(Long.class)) {
//        pstm.setLong(num, (long) val);
//        } else if (fieldType.equals(double.class)) {
//        pstm.setDouble(num, (double) val);
//        } else if (fieldType.equals(Double.class)) {
//        pstm.setDouble(num, (double) val);
//        } else if (fieldType.equals(String.class)) {
//        pstm.setString(num, (String) val);
//        } else if (fieldType.equals(java.util.Date.class)) {
//        pstm.setTimestamp(num, new java.sql.Timestamp(
//        (((java.util.Date) val).getTime())));
//        } else if (fieldType.equals(java.sql.Date.class)) {
//        pstm.setDate(num, (java.sql.Date) val);
//        } else if (fieldType.equals(boolean.class)) {
//        pstm.setBoolean(num, (boolean) val);
//        } else if (fieldType.equals(Boolean.class)) {
//        pstm.setBoolean(num, (boolean) val);
//        }else if (fieldType.equals(String[].class)) {
//        pstm.setString(num, arryToString((String[])val));
//        } else {
//        pstm.setObject(num, val);
//        }
//        num++;
//        }
//        }
//        pstm.executeUpdate();
//        rs = pstm.getGeneratedKeys();
//        if (rs.next()) {
//        id = rs.getLong(1);
//        }
//        } catch (Exception e) {
//        e.printStackTrace();
//        } finally {
//        YHDBUtility.close(pstm, rs, null);
//        }
//        return id;
//        }