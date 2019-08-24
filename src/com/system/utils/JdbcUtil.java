package com.system.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * JdbcUtils 工具类，封装了Jdbc的连接
 * 1、自动完成驱动的加载
 * 2、自动完成必要数据的处理
 * 3、简化connection
 * 4、完成统一 close方法
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.utils
 * @version:1.0
 */
public class JdbcUtil {

    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static String driverClass = null;

    //使用static修饰的静态代码块特征，完成驱动的自动加载
    static {
        try {
//            //需要读取在项目目录src下的一个db.properties文件
//            //1、创建一个properties对象
//            Properties properties = new Properties();
//            //2、使用load方法，加载文件，需要参数是一个FileInputStream
//            properties.load(new FileInputStream("db.properties"));
//
//            //3、从properties对像种读取对应的属性
//            url = properties.getProperty("url");
//            user = properties.getProperty("user");
//            password = properties.getProperty("password");
//            driverClass = properties.getProperty("driver");

            url="jdbc:mysql://localhost:3306/studentsystem?useUnicode=true&characterEncoding=utf-8&useSSL=true";
            user="root";
            password="1234";
            driverClass="com.mysql.jdbc.Driver";

            //4、加载驱动
            Class.forName(driverClass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 简化数据库连接对象java.sql.Connection的获取方式，
     * static修饰的静态成员方法直接通过类名调用
     * @return java.sql.Connection java连接数据库的对象
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //三重close复用

    /**
     * 关闭数据库连接对象
     * @param connection 传入的是java.sql.Connection 数据库连接对象
     */
    public static void close(Connection connection){
        close(connection, null, null);
    }

    /**
     * 关闭数据库连接对象和sql语句搬运工
     * @param connection 传入的是java.sql.Connection 数据库连接对象
     * @param statement statement
     */
    public static void close(Connection connection, Statement statement){
        close(connection, statement, null);
    }

    /**
     * 关闭数据库连接对象、sql语句搬运工 和 结果集对象
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet){

        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

