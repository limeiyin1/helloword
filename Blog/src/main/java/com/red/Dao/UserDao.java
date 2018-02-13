package com.red.Dao;

import com.red.Pojo.User;

public interface UserDao {
public User selectByUsername(User user);
public void insertUser(User user);
}
