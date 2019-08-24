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
    <title>条件查询</title>
    <style>
        tr{
            text-align: center;
        }
    </style>
</head>
<body>
    <h2 align="center">条件查询</h2>
    <table align="center" width="350px" border="1px">
        <form action="action.do?method=listQuery" method="post">
            <tr>
                <td>姓名</td>
                <td>
                    <input type="text" name="name" >
                </td>
            </tr>
            <tr>
                <td>年龄</td>
                <td>
                    <input type="text" name="age" >
                </td>
            </tr>
            <tr>
                <td>性别</td>
                <td>
                    <input type="radio" name="gender" value="0" checked>男
                    <input type="radio" name="gender" value="1" >女
                </td>
            </tr>
            <tr>
                <td>专业</td>
                <td>
                    <input type="text" name="major" >
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
                </td>
            </tr>
            <tr>
                <td>权限</td>
                <td>
                    <input type="radio" name="authority" value="0" checked>学生
                    <input type="radio" name="authority" value="1" >管理员
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
    <h5 align="center">删除后跳转回全部成员列表</h5>
</body>
</html>
