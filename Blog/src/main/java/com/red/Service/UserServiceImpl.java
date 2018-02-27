package com.red.Service;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.red.Dao.UserDao;
import com.red.Pojo.User;
@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	public User selectByUsername(String username) {
		System.out.println(username+"------------------");
		User user=new User();
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
