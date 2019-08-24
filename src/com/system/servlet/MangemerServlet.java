package com.system.servlet;

import com.system.dao.StudentDao;
import com.system.entity.PageBean;
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
import java.util.Objects;

import static com.system.utils.MD5Utils.stringMD5;

/**
 * 管理员专属的servlet
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.servlet
 * @version:1.0
 */
@WebServlet("/action.do")
public class MangemerServlet extends BaseServlet {

    private static StudentDao studentDao = StudentDao.getInstance();

    /**
     * 登陆处理
     * @param request 登陆请求
     * @param response 登陆响应
     */
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, InvocationTargetException, IllegalAccessException {
        //根据ID找出对应的Student对象，保存到request域对象中
        Integer id =Integer.valueOf(request.getParameter("id"));
        Student student = studentDao.findById(id);

        //先判定是否存在用户, 不存在直接回到登陆页面
        if (student == null){
            request.setAttribute("msg","用户名或密码错误");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //获取登陆的信息扔进temp
        Map<String, String[]> parameterMap = request.getParameterMap();
        Student temp = new Student();
        BeanUtils.populate(temp, parameterMap);

        //比较找出的student.password和temp.password，注意这里取出密码要通过 MD5 转码再进行比较
        if (!Objects.equals(stringMD5(temp.getPassword()), student.getPassword())){
            request.setAttribute("msg","用户名或密码错误");
            request.getRequestDispatcher("index.jsp").forward(request, response);

            //学生权限为 0 ,即为学生时,则跳转到student.jsp,否则跳转到ListAll.jsp, 显示所有用户数据
        }else if (!student.getAuthority()){
            request.setAttribute("student", student);
            request.getRequestDispatcher("student.jsp").forward(request, response);
        }else if (student.getAuthority()){
            response.sendRedirect("page.do?method=changePage&currentPage=1");
        }
    }

    /**
     * 管理端添加用户处理, 添加完跳转至分页方法changePage，再跳转至lisp.jsp分页显示
     * @param request 管理端添加用户请求
     * @param response 管理端添加用户响应
     */
    public void add(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {

        //esponse.setContentType("text/html;charset=utf-8");

        //获取所有参数的Map双边队列，参数的名字是key，值是value
        Map<String, String[]> parameterMap = request.getParameterMap();

        //创建一个没有任何数据的对象，实体
        Student student = new Student();

        //使用BeanUtils方法populate，需要的参数是符合JavaBean规范的类对象和对应的Map队列
        BeanUtils.populate(student, parameterMap);

        //设置 MD5 密码转码
        student.setPassword(stringMD5(student.getPassword()));

        //后台打印验证
        System.out.println(student.toString());

        //调用StudentDao添加数据到数据库
        studentDao.add(student);

        //使用重定向，操作连接到分页查询，然后再回到lisp.jsp分页显示
        response.sendRedirect("page.do?method=changePage&currentPage=1");
    }

    /**
     * 管理端删除操作,删除后重定向回完跳转至分页方法changePage，再跳转至lisp.jsp分页显示
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDao.delete(id);
        response.sendRedirect("page.do?method=changePage&currentPage=1");
    }

    public void modify(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        //获取修改后的信息扔进去
        Map<String, String[]> parameterMap = request.getParameterMap();
        Student student = new Student();
        BeanUtils.populate(student, parameterMap);

        //根据ID找出对应的Student对象，保存到request域对象中
        Integer id =Integer.valueOf(request.getParameter("id"));
        Student temp = studentDao.findById(id);

        //因为头像单独页面保存，地址不一样，所以提取头像的地址, 扔进新的Student对象，再更新
        student.setHeadPath(temp.getHeadPath());

        studentDao.update(student);

        response.sendRedirect("page.do?method=changePage&currentPage=1");
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        //根据ID找出对应的Student对象，保存到request域对象中
        Integer id =Integer.valueOf(request.getParameter("id"));
        Student student = studentDao.findById(id);

        if (student == null){
            request.setAttribute("msg","对应学生不存在");
            request.getRequestDispatcher("page.do?method=changePage&currentPage=1").forward(request, response);
        }else {
            request.setAttribute("student", student);
            request.getRequestDispatcher("modify.jsp").forward(request, response);
        }
    }

    /**
     * 条件查询
     */
    public void listQuery(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, InvocationTargetException, IllegalAccessException {
        //获取查询的信息扔进去
        Map<String, String[]> parameterMap = request.getParameterMap();
        Student student = new Student();
        BeanUtils.populate(student, parameterMap);

        //查询数据库，获取到List<Student>集合
        List<Student> studentList = studentDao.findQuery(student);

        //创建一个page对象，赋值两个page参数，以防list.jsp下面翻页处乱码
        PageBean<Object> page = new PageBean<>();
        page.setCurrentPage(1);
        page.setToatalPage(1);

        //在request域对象中，保存员工信息对象List集合
        request.setAttribute("studentList", studentList);
        request.setAttribute("page", page);
        //转发到List.jsp页面中
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}
