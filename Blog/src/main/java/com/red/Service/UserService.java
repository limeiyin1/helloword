package com.red.Service;

import com.red.Pojo.User;

public interface UserService {
public User selectByUsername(String username);
public void insertUser(User user);
}
