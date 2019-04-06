package com.dao.impl;

import com.dao.UserDao;
import com.domain.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    UserDao userDao=new UserDaoImpl();

    @Test
    public void getUser() {
        User user = userDao.getUser("杨沐");
        System.out.println(user);
    }
}