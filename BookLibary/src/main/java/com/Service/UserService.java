package com.Service;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.domain.User;

public class UserService {

    private UserDao userDao=new UserDaoImpl();

    public User getUser(String username){
        return userDao.getUser(username);
    }
}
