package com.red.Controller;
import javax.annotation.Resource;
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
		Result  result=null;
		User user2 = userService.selectByUsername(user.getUsername());
		if (user2==null) {
			userService.insertUser(user);
			result.setStatus(200);
			result.setMessage("你已注册成功!");
			return result;
		}else {
			result.setStatus(500);
			result.setMessage("该账号已被注册了，请重新注册！");
			return  result;
		}
			
	}

}
