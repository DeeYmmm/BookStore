package com.dao.impl;

import com.testDao.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

public class BaseDaoTest {
    private BookDaoImpl bookDdao=new BookDaoImpl();

    @Test
    public void insert() {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="insert into person values(null,?,?)";
            long id = bookDdao.insert(connection, sql, "jay", "men");
            System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
    }

    @Test
    public void update() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getForList() {
    }

    @Test
    public void getForValue() {
    }

    @Test
    public void batch() {
    }
}