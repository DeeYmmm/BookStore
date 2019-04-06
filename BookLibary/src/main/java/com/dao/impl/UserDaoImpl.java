package com.dao.impl;

import com.dao.UserDao;
import com.domain.User;
import com.testDao.JDBCUtils;

import java.sql.Connection;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public User getUser(String username) {
        Connection connection=null;
        User user=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select userId,username,accountId from userinfo where username=?";
            user=get(connection,sql,username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
        return user;
    }
}
