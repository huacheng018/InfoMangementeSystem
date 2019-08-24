<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: qq108
  Date: 2019-08-19
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生信息管理系统</title>
    <style>
        span{
            color: yellowgreen;
        }
        p{
            text-align: center;
        }
        td{
            text-align: center;
        }
    </style>
</head>
<body>
<h1 align="center">欢迎来到学生信息管理系统</h1>
<h6>
    <span>${msg}</span>
</h6>
<br><br>
<p>
    <a href="add.jsp">添加同学</a>
    <br><br>
    <a href="query.jsp">条件查询</a>
    <br><br>
    <a href="page.do?method=changePage">全部同学</a>
</p>
<br><br>
<table align="center" width="600px" border="1px">
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>年龄</th>
        <th>性别</th>
        <th>专业</th>
        <th>学院</th>.
        <th>权限</th>.
        <th>操作</th>
    </tr>
    <c:if test="${studentList != null}">
        <c:forEach items="${studentList}" var="student">
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.age}</td>
                <td>
                    <c:if test="${!student.gender}">男</c:if>
                    <c:if test="${student.gender}">女</c:if>
                </td>
                <td>${student.major}</td>
                <td>${student.academy}</td>
                <td>
                    <c:if test="${!student.authority}">学生</c:if>
                    <c:if test="${student.authority}">管理员</c:if>
                </td>
                <td>
                    <a href="action.do?method=findOne&id=${student.id}">修改</a>
                    &nbsp;&nbsp;
                    <a href="action.do?method=delete&id=${student.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
    <%--两个判断，判断当前页面和总页面的关系，然后再决定是否显示操作按钮--%>
<p>
    <c:if test="${page.currentPage != 1}">
        <a href="page.do?method=changePage&currentPage=1">首页</a>&nbsp;
        <a href="page.do?method=changePage&currentPage=${page.currentPage-1}">上页</a>
    </c:if>
    &nbsp;
    当前页为第${page.currentPage}页 &nbsp; 共${page.toatalPage}页
    &nbsp;
    <c:if test="${page.currentPage != page.toatalPage}">
        <a href="page.do?method=changePage&currentPage=${page.currentPage+1}">下页</a>&nbsp;
        <a href="page.do?method=changePage&currentPage=${page.toatalPage}">尾页</a>
    </c:if>
</p>
</body>
</html>
