package com.system.servlet;

import com.system.dao.StudentDao;
import com.system.entity.Student;
import com.system.utils.BaseServlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * 学生添加头像专门弄的servlet
 * @Author: 枠成
 * @Data:2019-08-21
 * @Description:com.system.servlet
 * @version:1.0
 */
@WebServlet("/addHeadPicture.do")
public class addHeadPictureServlet extends BaseServlet {

    private static StudentDao studentDao = StudentDao.getInstance();

    public void addHeadPicture(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvocationTargetException, IllegalAccessException, SQLException {

        //三件套，先放着，怕出事
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");

        //1,创建对象仓库
        DiskFileItemFactory factory=new DiskFileItemFactory();

        ServletFileUpload upload=new ServletFileUpload(factory);

        //2,根据ID找出对应的Student对象，保存到request域对象中
        Integer id =Integer.valueOf(request.getParameter("id"));
        Student student = studentDao.findById(id);

        //拼接新文件名
        String newFileName = null;

        //3,从字符串获取内容，分两步, 普通键值对和文件信息
        try {
            List<FileItem> items = upload.parseRequest(request);
            //4,遍历集合,获取普通键值对信息

            for(FileItem item :items) {
                //获取键名
                String fileName=item.getFieldName();

                //判断是普通键值对信息，但这里不需要判断,因为项目方案设置上传头像为修改信息时 单独上传
                if(item.isFormField()) {
                    System.out.println("上传头像中");
                }else {

                    //5,解决重名问题,生成随机字符串,采用 随机字符+文件原名=新文件名
                    String uuid=UUID.randomUUID().toString();

                    //去掉uuid生成的随机字符中的 -, 以空串代替,实现去除
                    uuid=uuid.replace("-", "");

                    //获取原文件名,
                    String oldFileName=item.getName();

                    //判断是否是图片
                    if (!isPictuure(oldFileName)) {
                        request.setAttribute("msg", "图片格式不对，上传失败");
                        request.setAttribute("student", student);
                        request.getRequestDispatcher("student.jsp").forward(request, response);
                        return;
                    }

                    //考虑带有路径的问题,做判断,有则截取,这里\需要转义,所以用\\
                    int index= oldFileName.lastIndexOf("\\");

                    //判断\路径分隔符的位置,如果为-1,则没有,如果有,则截取后面的文件名部分
                    if(index!=-1){
                        //有路径,则从后一位截取,获取文件名和后缀名
                        oldFileName.substring(index+1);
                    }

                    newFileName = uuid+"-"+oldFileName;

                    //6,动态获取headPictures的文件夹路径
                    String path = request.getSession().getServletContext().getRealPath("headPictures");

                    //创建文件对象,指定文件夹下,文件名
                    File file = new File(path,newFileName);

                    //7,把文件信息写入到指定文件
                    item.write(file);

                    System.out.println(newFileName+"  上传成功");
                    System.out.println("文件地址为"+file.getPath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //更新头像地址信息, 只保存图片文件名，网页获取文件时必须动态获取
        student.setHeadPath(newFileName);
        try {
            studentDao.update(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("student", student);
        request.getRequestDispatcher("student.jsp").forward(request, response);
    }

    /**
     * 判断是否是图片的方法，用于过滤
     * @param oldFileName 传入的旧文件名
     * @return 返回true则是图片
     */
    public boolean isPictuure(String oldFileName) {
        //截取文件名后面的文件格式字符串
        String substring = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
        String str = substring.toLowerCase();
        System.out.println(str);
        //Object的equals方法容易抛空指针异常，应使用常量或确定有值的对象来调用equals
        if (!("png".equals(str)) && !("gif".equals(str)) && !("jpg".equals(str))){
            System.out.println("上传图片类型有误");
            return false;
        }
        return true;
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
            request.getRequestDispatcher("addHeadPicture.jsp").forward(request, response);
        }
    }
}
