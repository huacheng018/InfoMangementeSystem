package com.system.servlet;

import com.system.dao.StudentDao;
import com.system.entity.Student;
import com.system.utils.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.system.utils.MD5Utils.stringMD5;

/**
 * 学生专属servlet
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.servlet
 * @version:1.0
 */
@WebServlet("/Student.do")
public class StudentServlet extends BaseServlet {

    private static StudentDao studentDao = StudentDao.getInstance();

    /**
     * 注册处理,注册成功跳转至用户信息页面
     */
    public void register(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, IllegalAccessException, InvocationTargetException, ServletException {

        //获取所有参数的Map双边队列，参数的名字是key，值是value
        Map<String, String[]> parameterMap = request.getParameterMap();

        //创建一个没有任何数据的对象，实体
        Student student = new Student();

        //使用BeanUtils方法populate，需要的参数是符合JavaBean规范的类对象和对应的Map队列
        BeanUtils.populate(student, parameterMap);

        //设置 MD5 密码转码
        student.setPassword(stringMD5(student.getPassword()));

        //打印到控制台debug
        System.out.println(student.toString());

        //调用StudentDao添加数据到数据库.
        studentDao.add(student);

        /*
        因为id值为自增长，注册是不需要输入id，而id号默认为空，即为0
        所以只需查找最大的id号返回，重新录入student的id
        */
        student = studentDao.findMaxId();

        //跳转至student.jsp
        request.setAttribute("student", student);
        request.getRequestDispatcher("student.jsp").forward(request, response);
    }

    /**
     * 学生端注销操作,删除后跳回登陆页面
     */
    public void deleteByStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDao.delete(id);

        request.setAttribute("msg","注销成功");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * 学生端修改文字信息的方法，不修改头像
     */
    public void modify(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException, ServletException {

        //获取修改后的信息扔进去
        Map<String, String[]> parameterMap = request.getParameterMap();
        Student student = new Student();
        BeanUtils.populate(student, parameterMap);

        //根据ID找出对应的Student对象，保存到request域对象中
        Integer id =Integer.valueOf(request.getParameter("id"));
        Student temp = studentDao.findById(id);

        //因为头像单独保存，地址不一样，所以提取头像的地址, 扔进新的Student类，再更新
        student.setHeadPath(temp.getHeadPath());
        //更新
        studentDao.update(student);

        //跳转至student.jsp
        request.setAttribute("student", student);
        request.getRequestDispatcher("student.jsp").forward(request, response);
    }

    /**
     * 根据ID查找对应的student
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        //根据ID找出对应的Student对象，保存到request域对象中
        Integer id =Integer.valueOf(request.getParameter("id"));
        Student student = studentDao.findById(id);

        if (student == null){
            request.setAttribute("msg","对应学生不存在");
            request.getRequestDispatcher("action.do?method=listAll").forward(request, response);
        }else {
            request.setAttribute("student", student);
            request.getRequestDispatcher("modifyByStudent.jsp").forward(request, response);
        }
    }

}
