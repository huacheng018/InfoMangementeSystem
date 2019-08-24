<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: qq108
  Date: 2019-08-19
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改员工信息</title>
    <style>
        span{
            color: palevioletred;
        }
        td{
            text-align: center;
        }
    </style>
</head>
<body>
<table border="1px" align="center">
<form action="action.do?method=modify&id=${student.id} " method="post">
    <tr>
        <td>ID</td>
        <td>
            ${student.id}
        </td>
    </tr>
    <tr>
        <td>姓名</td>
        <td>
            <input type="text" name="name" value="${student.name}">
        </td>
    </tr>
    <tr>
        <td>密码</td>
        <td>
            <input type="password" name="password" value="${student.password}">
        </td>
    </tr>
    <tr>
        <td>年龄</td>
        <td>
            <input type="text" name="age" value="${student.age}">
        </td>
    </tr>
    <tr>
        <td>性别</td>
        <td>
            <c:choose>
                <c:when test="${student.gender == false}">
                    <input type="radio" name="gender" value="0" checked>男
                    <input type="radio" name="gender" value="1" >女
                </c:when>
                <c:when test="${student.gender == true}">
                    <input type="radio" name="gender" value="0" >男
                    <input type="radio" name="gender" value="1" checked> 女
                </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>专业</td>
        <td>
            <input type="text" name="major" value="${student.major}">
        </td>
    </tr>
    <tr>
        <td>学院</td>
        <td>
            <select name="academy">
                <option value="互联网学院">互联网学院</option>
                <option value="外文学院">外文学院</option>
                <option value="金投院">金投院</option>
                <option value="工管院">工管院</option>
            </select>
            当前学院为<span>${student.academy}</span>
        </td>
    </tr>
    <tr>
        <td>权限</td>
        <td>
            <%--学生不能变管理,管理可以变学生--%>
            <c:choose>
                <c:when test="${student.authority == false}">
                    <input type="radio" name="authority" value="0" checked>学生
                    <input type="radio" name="authority" value="1" > 管理员
                </c:when>
                <c:when test="${student.authority == true}">
                    <input type="radio" name="authority" value="0" >学生
                    <input type="radio" name="authority" value="1" checked> 管理员
                </c:when>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <input type="submit" value="提交">
            <input type="reset" value="重置">
        </td>
    </tr>
</form>
</table>
</body>
</html>
