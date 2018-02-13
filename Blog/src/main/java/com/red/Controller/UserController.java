package com.red.Controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.red.Pojo.User;
import com.red.Service.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	@ResponseBody	
public void login(User user) {
		User user2 = userService.selectByUsername(user.getUsername());
		if (user2==null) {
			userService.insertUser(user);
		}else {
			
		}
			
	}

}
