package com.xia.framework.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    //数据库url、用户名和密码
    private static final String driver;//Ctrl+Alt+F抽取全局静态变量
    private static final String url;
    private static final String username;
    private static final String password;


    /*读取属性文件，获取jdbc信息*/
    static{
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("druid.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("drive");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            //1、注册JDBC驱动
            Class.forName(driver);
            /* 2、获取数据库连接 */
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /*关闭结果集、数据库操作对象、数据库连接*/
    public static void release(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        if(resultSet!=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
