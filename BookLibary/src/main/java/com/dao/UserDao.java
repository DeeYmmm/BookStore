package com.dao;

import com.domain.User;

public interface UserDao {

    User getUser(String username);
}
