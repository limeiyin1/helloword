package com.red.Pojo;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {



	/*
	 * 将一个字符串MD5加密，方式很多，我们使用的是Spring包下
	 */
	public static String getMd5Simple(String password){


		String md502 =DigestUtils.md5Hex(password.getBytes());

		return md502;

	}

	
}
