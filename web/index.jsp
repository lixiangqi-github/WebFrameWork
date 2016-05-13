<%--
  Created by IntelliJ IDEA.
  User: 30695
  Date: 2016/5/8 0008
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body> ${base}<br>
文件上传
<form action="${base}/mainController/buildBeanFile" method="POST" enctype="multipart/form-data">
    <input type="text" name="data.name">
    <input type="text" name="data.age">
    <input type="file" name="docName">
    <input type="submit" value="提交">
</form>
非文件上传
<form action="${base}/mainController/buildBean" method="POST" >
    <input type="text" name="data.name">
    <input type="text" name="data.age">
    <input type="submit" value="提交">
</form>
</body>
</html>
