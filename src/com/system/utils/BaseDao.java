package com.system.utils;

import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
  基于原数据，BeanUtils还有反射思想，完成的一个统一的Query和Update方法
  Update方法用于处理针对于数据库修改的方法
   insert, update, delete, create, drop
   Query方法用于处理查询语句
       select
 */

/**
 * BaseDao工具类，封装的 增 删 查 改
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.utils
 * @version:1.0
 */
public class BaseDao {

    /*
      完成一个统一的Update方法
      方法分析：
           方法名：update
           形式参数列表：
               String sql 当前方法指定的sql语句
               需要sql语句对应的参数，个数和类型都不确定
               Object[] parameters
               List<Object> parameters
            返回值类型：
               void
               int 返回当前SQL语句执行数据库受影响的行数
            权限修饰：public
     */

    /**
     * 统一修改方法，用于处理所有类型的sql语句
     * @param sql 执行的sql语句
     * @param parameters 对应参数数组，object类型
     * @return 返回当前sql语句执行 数据库受影响行数rows affected
     */
    public int update(String sql, Object[] parameters) throws SQLException {
        //判断是否为空
        if (sql == null){
            throw new SQLException("SQL is null");
        }

        //1、获取数据库连接
        Connection connection = JdbcUtil.getConnection();

        //2、使用sql语句，获取对应的PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //3、处理PreparedStatement参数
        preparedStatement = parametersAssignment(preparedStatement, parameters);

        //4、执行sql语句
        int i = preparedStatement.executeUpdate();

        //5、关闭资源
        JdbcUtil.close(connection,preparedStatement);

        //6、返回受影响的行数
        return i;
    }

    /*
      统一的查询方法
           1、查单个
           2、查多个
           3、查任意类型对象
      方法分析：
           方法名：query
           形式参数列表：
               String sql需要执行的sql语句
               Object[] parameters 对应sql语句的一个参数数组
              Class<T> cls
                   1、第三个参数需要带有泛型，用于约束当前方法种使用的泛型
                   2、这个参数还需要提供一个非常重要的数据，指定查询数据的Class类对象，
                      通过Class类对象借助于反射
           返回值类型：
               List需要使用泛型，不直接使用object，不希望过多数据类型转换
                   避免因为数据类型一致化问题导致代码存在隐患
           权限修饰：public
           带有自定义泛型：<T>
     */

    /**
     * 通用的数据库查询方法，可以查询指定的任意类对象类型
     * @param sql 指定的sql语句，需要select语句的DQL语句
     * @param parameters 对应的sql语句参数数组
     * @param cls 当前查询指定类对象类型的Class对象
     * @param <T> 当前方法中使用的泛型
     * @return 返回包含指定类对象类型的 List集合
     * @throws SQLException 抛出sql异常
     */
    public <T> List<T> query(String sql, Object[] parameters, Class<T> cls) throws SQLException {
        if (sql == null || cls == null){
            throw new NullPointerException();
        }

        //1、获取数据库连接对象
        Connection connection = JdbcUtil.getConnection();

        //2、使用sql预处理，获取PrepardStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //3、处理PreparedStatement参数
        preparedStatement = parametersAssignment(preparedStatement, parameters);

        //5、执行sql语句，获取结果集对象
        ResultSet resultSet = preparedStatement.executeQuery();

        //6、准备list集合
        List<T> list = new ArrayList<>();

        //7、获取结果集元数据
        ResultSetMetaData metaData = resultSet.getMetaData();

        //8、获取结果集元数据中的字段个数
        int columnCount = metaData.getColumnCount();

        //9、遍历结果集
        while (resultSet.next()){
            T t;
            try {
                //10、创建对应数据类型的类对象，使用反射处理
                t = cls.getConstructor(null).newInstance(null);

                //11、用结果集元素中的字段个数，来处理数据行
                for (int i = 1; i<= columnCount; i++){
                    //12、获取字段名
                    String fieldName = metaData.getColumnName(i);

                    //13、获取对应字段名的数据
                    Object value = resultSet.getObject(fieldName);

                    //14、使用BeanUtils给指定字段的数据在类对象中赋值
                    BeanUtils.setProperty(t, fieldName, value);
                }

                //15、存放到list集合中
                list.add(t);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return list.size() != 0 ? list :null;
    }


    /**
     * parametersAssignment 预处理
     * 权限修饰：
     *     private 仅供类内处理当前方法中preparedStatement
     * 方法名：
     *     参数处理 parametersAssignment
     * 形式参数列表：
     *     PreparedStatement 类对象
     *     参数数组 Object[] parameters
     * 返回值类型：
     *     PreparedStatement 对象
     */
    private PreparedStatement parametersAssignment(PreparedStatement preparedStatement, Object[] parameters) throws SQLException {
        //3、获取sql参数个数
        int parameterCount = preparedStatement.getParameterMetaData().getParameterCount();

        //4、给予PrepardStatement预处理sql语句赋值参数
        if (parameters != null && parameters.length == parameterCount){
            for (int i = 1; i<= parameterCount; i++){
                preparedStatement.setObject(i,parameters[i-1]);
            }
        }

        return preparedStatement;
    }

}
