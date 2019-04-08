package com.testDao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBUtilsTest {
    QueryRunner queryRunner=new QueryRunner();

    private class MyResultSetHandler implements ResultSetHandler{

        public Object handle(ResultSet resultSet) throws SQLException {
            //System.out.println("Hello!");
            List<Person> list=new ArrayList<Person>();

            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                String sex=resultSet.getString(3);
                Person person=new Person(id,name,sex);

                list.add(person);
            }

            return list;
        }
    }


    /**
     * QueryRunner的update()方法：增，删，改
     */
    @Test
    public void testUpdate(){
        String sql="delete from person where id=?";
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            int i=queryRunner.update(connection,sql,3);
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    /**
     * QueryRunner的query方法的返回值取决于
     * ResuletSetHandler参数的 handle 方法的返回值
     */
    @Test
    public void testQuery(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select * from person";
            Object o=queryRunner.query(connection,sql,new MyResultSetHandler());
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    /**
     * BeanHandler：把结果集的第一条记录
     * 转为创建BeanHandler对象时传入的Class参数对应的对象
     */
    @Test
    public void testBeanHandler(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select * from person";
            Object o=queryRunner.query(connection,sql,new BeanHandler(Person.class));
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    /**
     * BeanListHandler：把结果集转为一个List,
     * 该List不为null,但可能为空集合(size()方法返回0)
     *
     */
    @Test
    public void testBeanListHandler(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select * from person";
            Object o=queryRunner.query(connection,sql,new BeanListHandler(Person.class));
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    /**
     * MapHandler:返回sql对应的第一条记录对应的Map对象
     * 键：sql查询的列名（不是列的别名）　值：列的值
     */
    @Test
    public void testMapHandler(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select * from person";
            Map<String,Object> map =queryRunner.query(connection,sql,new MapHandler());
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    /**
     * MapListHandler:将结果集转为一个Map的List,
     * 一个Map对应结果集到一条记录：键：sql查询的列名（不是列的别名）　值：列的值
     */
    @Test
    public void testMapListHandler(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select * from person";
            List<Map<String,Object>> result =queryRunner.query(connection,sql,new MapListHandler());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    /**
     * ScalarHandler:把结果集转为一个数值（可以是任意基本数据类型和字符串，Date等）返回
     */
    @Test
    public void testScalarHandler(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            //String sql="select name from person where id=?";
            String sql="select count(id) from person";
            Object result =queryRunner.query(connection,sql,new ScalarHandler());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }
}
