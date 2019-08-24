<%--
  Created by IntelliJ IDEA.
  User: qq108
  Date: 2019-08-18
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>欢迎来到信息管理系统</title>
  </head>
  <style>
      body{
          text-align: center;
      }
      p{
          color: tomato;
      }
      td{
          text-align: center;
      }
  </style>
  <body>
    <h2>欢迎来到学生信息管理系统</h2>
  <br>
  <table align="center" width="500px" border="1px">
      <form action="action.do?method=login" method="post">
          <tr>
              <td>ID</td>
              <td>
                  <input type="text" name="id" >
              </td>
          </tr>
          <tr>
              <td>密码</td>
              <td>
                  <input type="text" name="password" >
              </td>
          </tr>
          <tr>
              <td>学生or管理员</td>
              <td>
                  <p>自动识别学生or管理员</p>
              </td>
          </tr>
          <tr>
              <td></td>
              <td>
                  <input type="submit" value="登陆">
                  <input type="reset" value="重置">
              </td>
          </tr>
      </form>

  </table>

    <p align="center">${msg}</p>
  <br><br><br>
  <br><br>

  <a href="register.jsp">注册新用户</a>
  </body>
</html>
