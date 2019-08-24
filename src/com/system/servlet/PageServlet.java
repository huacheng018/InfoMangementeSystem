package com.system.servlet;

import com.system.entity.PageBean;
import com.system.entity.Student;
import com.system.utils.BaseServlet;
import com.system.dao.StudentDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑分页的Servlet
 * 用的是逻辑分页，而非调用的MySQL的物理分页
 * 因为分页显示项目，最终都是需要总数据条数，所以直接查询所有数据然后实行逻辑分页，减少代码冗余
 * 资历尚浅，初始方案为每次换页都取出所有数据（等于集合了原本的逻辑分页和物理分页的弊端，有待优化）
 * @Author: 枠成
 * @Data:2019-08-23
 * @Description:com.system.servlet
 * @version:1.0
 */
@WebServlet("/page.do")
public class PageServlet extends BaseServlet {

    private static StudentDao studentDao = StudentDao.getInstance();

    public void changePage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        //创建page对象
        PageBean page = new PageBean();

        //查询数据库，获取到List<Student>集合
        List<Student> studentList = studentDao.findAll();

        //数据总数
        int count = studentList.size();

        //定义每页数据数为10
        int pageSize = 10;

        //判断是否传值，没有传值默认为首页
        String cPage = request.getParameter("currentPage");
        if (cPage == null || "".equals(cPage)) {
            cPage = "1";
        }
        int currentPage = Integer.parseInt(cPage);
        if (currentPage < 1){
            currentPage = 1;
        }

        //扔进page对象
        page.setCurrentPage(currentPage);
        page.setTotalData(count);
        page.setPageSize(pageSize);
        page.setToatalPage();

        List<Student> students = new ArrayList<Student>();
        for (int i = (currentPage-1)*pageSize ; i < currentPage*pageSize && i < count ;i++){
            students.add(studentList.get(i));
        }

        //转发回list.jsp
        request.setAttribute("page",page);
        request.setAttribute("studentList", students);
        request.getRequestDispatcher("list.jsp").forward(request,response);
    }
}
