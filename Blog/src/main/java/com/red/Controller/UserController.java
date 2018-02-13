package com.red.Controller;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.red.Pojo.Result;
import com.red.Pojo.User;
import com.red.Service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	@ResponseBody
	public Result regist(User user) {
		Result result = null;
		User user2 = userService.selectByUsername(user.getUsername());
		if (user2 == null) {
			userService.insertUser(user);
			result.setStatus(200);
			result.setMessage("你已注册成功!");
			return result;
		} else {
			result.setStatus(500);
			result.setMessage("该账号已被注册了，请重新注册！");
			return result;
		}
	}

	public Result login(User user) {
		Result result = null;
		User user2 = userService.selectByUsername(user.getUsername());
		if(user2==null) {
			result.setMessage("该账号没有注册，请重新登录");
			result.setStatus(500);
		}
		if (user.getPassword() != null) {
			String md5Hex = DigestUtils.md5Hex(user.getPassword().getBytes());
			if (user2.getPassword().equals(md5Hex)) {
				result.setStatus(200);
				result.setMessage("登录成功");
				
			}
		}

		return result;
	}
}
