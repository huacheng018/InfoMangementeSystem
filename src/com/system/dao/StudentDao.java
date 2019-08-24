package com.system.dao;

import com.system.entity.Student;
import com.system.utils.BaseDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDao继承于BaseDao
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.dao
 * @version:1.0
 */
public class StudentDao extends BaseDao {

    // volatile声明作用即是内存变量共享的作用
    private static volatile StudentDao studentDao;

    private StudentDao(){}

    //双重上锁
    public static  StudentDao getInstance(){
        if (studentDao == null){
            synchronized (StudentDao.class){
                if (studentDao == null){
                    studentDao = new StudentDao();
                }
            }
        }
        return studentDao;
    }

    /**
     * 添加学生or管理员
     * 传入的是一个Student类对象，保存到MySQL数据库中
     * @param student Student类对象
     * @return MySQL数据库收到影响的行数
     * @throws SQLException SQL异常
     */
    public int add(Student student) throws SQLException {
        String sql = "insert into studentsystem.student(name, age, gender, major, academy, authority, password) values(?,?,?,?,?,?,?)";
        Object[] parameters = {student.getName(), student.getAge(), student.isGender(), student.getMajor(), student.getAcademy(), student.getAuthority(), student.getPassword()};
        return super.update(sql,parameters);
    }

    /**
     * 更新信息
     * 修改Student类对象的方法，需要的参数是一个Student类对象
     * @param student Student类对象
     * @return MySQL数据库收到影响的行数
     * @throws SQLException SQL异常
     */
    public int update(Student student) throws SQLException {
        String sql = "update studentsystem.student set name = ?, age = ?, gender = ?, major = ?, academy = ?, headPath = ? where id =?";
        Object[] parameters = {student.getName(), student.getAge(), student.isGender(), student.getMajor(), student.getAcademy(), student.getHeadPath(), student.getId()};
        return super.update(sql, parameters);
    }

    /**
     * 根据Student类对象的ID删除对应的Student数据
     * @param studentID Student ID唯一
     * @return MySQL数据库受影响的行数
     * @throws SQLException SQL异常
     */
    public int delete(int studentID) throws SQLException {
        String sql = "delete from studentsystem.student where id = "+studentID;
        return super.update(sql, null);
    }

    /**
     * 根据用户ID找出对应ID student对象
     * @param studentID 学生的ID号
     * @return Student类对象，如果没有找到，返回null
     * @throws SQLException SQL异常
     */
    public Student findById(int studentID) throws SQLException {
        String sql = "select * from studentsystem.student where id = "+studentID;
        List<Student> query = super.query(sql, null, Student.class);
        return query != null ? query.get(0) : null;
    }

    /**
     * 找出最大ID的student对象
     * @return Student类对象，如果没有找到，返回null
     * @throws SQLException SQL异常
     */
    public Student findMaxId() throws SQLException {
        String sql = "select * from studentsystem.student where id = (SELECT MAX(id) FROM studentsystem.student)";
        List<Student> query = super.query(sql, null, Student.class);
        return query != null ? query.get(0) : null;
    }

    /**
     * 查询所有的Student信息，返回List<Student> 集合
     * @return List集合
     * @throws SQLException SQL异常
     */
    public List<Student> findAll() throws SQLException {
        String sql = "select * from studentsystem.student";
        return super.query(sql, null, Student.class);
    }

        /*
        物理分页用的limit查询语句
        */
//     查询当前页数的Student信息，返回List<Student> 集合
//     @return List集合
//     @throws SQLException SQL异常
//
//    public List<Student> findPage(int start, int pageSize) throws SQLException {
//        String sql = "select * from studentsystem.student limit "+start+","+pageSize;
//        return super.query(sql, null, Student.class);
//    }

    /**
     * 条件查询Student信息，返回List<Student> 集合
     * 方案：字符串拼接手法，用判断参数是否为空，不为空则sql语句加？，且参数存入object数组做预处理
     * 虽然是字符串拼接，但能防sql注入
     * @return List集合
     * @throws SQLException SQL异常
     */
    public List<Student> findQuery(Student student) throws SQLException {
        //创建参数object的List
        ArrayList<Object> objects = new ArrayList<>();
        //扔进确定的值
        objects.add(student.isGender());
        objects.add(student.getAcademy());
        objects.add(student.getAuthority());

        /*
        因为gender和authority默认为boolean值，无赋值情况下默认为false，所以再jsp中只能设置为必须选项
        而academy为默认选第一个
        */
        String sql = "select * from studentsystem.student where gender = ? and academy = ? and authority = ? ";
        //判断其他值是否为空
        if (!"".equals(student.getName())) {
            sql += "and name = ? ";
            objects.add(student.getName());
        }
        if (0 != student.getAge()) {
            sql += "and age = ? ";
            objects.add(student.getAge());
        }
        if (!("".equals(student.getMajor()) || student.getMajor() == null)) {
            sql += "and major = ? ";
            objects.add(student.getMajor());
        }

        //转为object类型的数组
        Object[] parameters = objects.toArray();

        return super.query(sql, parameters, Student.class);
    }
}
