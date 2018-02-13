package com.red.Service;

import javax.annotation.Resource;

import com.red.Dao.UserDao;
import com.red.Pojo.User;

public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	public User selectByUsername(String username) {
		User user=null;
		if(username!=null) {
			user.setUsername(username);
			user = userDao.selectByUsername(user);
		}
		return user;
	}
	public void insertUser(User user) {
		userDao.insertUser(user);
	}
}
