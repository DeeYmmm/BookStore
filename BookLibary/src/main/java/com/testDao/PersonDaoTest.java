package com.testDao;

import org.junit.Test;

import java.sql.Connection;

public class PersonDaoTest {
    private PersonDao personDao=new PersonDao();
    @Test
    public void test1(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select * from person";
            Person person=personDao.get(connection,sql);
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    @Test
    public void testBatch(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="insert into person values(null,?,?)";
            personDao.batch(connection,sql,new Object[]{"otto","men"},new Object[]{"kkk","women"});
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    @Test
    public void testConnection(){
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,null,connection);
        }

    }
}