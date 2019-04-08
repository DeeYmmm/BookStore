package com.dao.impl;

import com.dao.UserDao;
import com.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public User getUser(String username) {
        String sql="select userId,username,accountId from userinfo where username=?";
        return get(sql,username);
    }
}
