package com.dao.impl;

import org.junit.Test;

public class BaseDaoTest {
    private BookDaoImpl bookDdao=new BookDaoImpl();

    @Test
    public void insert() {
        String sql="insert into person values(null,?,?)";
        long id = bookDdao.insert(sql, "jay", "men");
        System.out.println(id);

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