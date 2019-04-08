package com.testDao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
    private static ComboPooledDataSource dataSource;

    static {
        dataSource=new ComboPooledDataSource("mysql");
    }

    public static Connection getConnection() throws Exception {
        /*InputStream in=JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(in);

        String driverClass=properties.getProperty("driver");
        String url=properties.getProperty("jdbcurl");
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");

        Class.forName(driverClass);

        Connection connection= DriverManager.getConnection(url,user,password);
        System.out.println(connection);
        return connection;*/
        Connection connection=dataSource.getConnection();
        return connection;
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
