<%--
  Created by IntelliJ IDEA.
  User: qq108
  Date: 2019-08-21
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加头像</title>
    <style>
        p{
            align: center;
        }
    </style>
</head>
<body>
<!-- 测试文件上传和下载 -->
<form action="addHeadPicture.do?method=addHeadPicture&id=${student.id}" method="post" enctype="multipart/form-data">
    <p align="center">
        照片:<input type="file" name="photo" />
    </p>
    <p align="center"> <input type="submit" value="提交"> </p>
</form>
</body>
</html>
