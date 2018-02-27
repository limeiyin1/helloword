package com.red.Dao;

import org.springframework.stereotype.Repository;

import com.red.Pojo.User;
@Repository
public interface UserDao {
public User selectByUsername(User user);
public void insertUser(User user);
}
