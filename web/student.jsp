<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: qq108
  Date: 2019-08-21
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="referrer" content="never">
    <title>用户信息</title>
    <style>
        td{
            text-align: center;
        }
    </style>
</head>
<body>
<p align="center"><strong>头像</strong></p>
<p align="center"><img src="headPictures/${student.headPath}" alt="头像" width="200px" height="200px"></p>
<br>
<p align="center">${msg}</p>
<br>
<p align="center"><a href="addHeadPicture.do?method=findOne&id=${student.id}">上传头像</a></p>
<p align="center"><strong>tip</strong>：此处只能上传格式为png、gif、jpg的图片<br>否则上传失败跳转回当前页面</p>

<table align="center" width="400px" border="1px">
        <tr>
            <td>ID</td>
            <td>
                ${student.id}
            </td>
        </tr>
        <tr>
            <td>姓名</td>
            <td>
                ${student.name}
            </td>
        </tr>
        <tr>
            <td>年龄</td>
            <td>
                ${student.age}
            </td>
        </tr>
        <tr>
            <td>性别</td>
            <td>
                <c:if test="${!student.gender}">男</c:if>
                <c:if test="${student.gender}">女</c:if>
            </td>
        </tr>
        <tr>
            <td>专业</td>
            <td>
                ${student.major}
            </td>
        </tr>
        <tr>
            <td>学院</td>
            <td>${student.academy}</td>
        </tr>
        <tr>
            <td>权限</td>
            <td>
                <c:if test="${!student.authority}">学生</c:if>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a href="Student.do?method=findOne&id=${student.id}">修改</a>
                <a href="Student.do?method=deleteByStudent&id=${student.id}">注销</a>
            </td>
        </tr>
</table>
</body>
</html>
