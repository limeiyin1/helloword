/*
 * com.toone.framework.utils  2015-4-14
 *
 * Copyright 2010 Shenzhen TYDIC Information Technology Co.,Ltd.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.redfinger.manager.common.utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/** 
 * @ClassName: RandomUtils 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson yuchanghong@tydic.com 
 * @date 2015-4-14 下午2:27:21 
 *  
 */
public class RandomUtils {
	/**
	 * 
	 * @Title: randomUUID 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String randomUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("ABCDEFGHJKLMNPQRSTUVWXYZ0123456789".length());
		String str = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int num = random.nextInt(34);
			buf.append(str.charAt(num));
		}
		System.out.println(buf.toString());
		
		System.out.println(randomString(4));
	}
	
	/** 产生一个随机的字符串，排除难分辨的I/l、O/0 */
	public static String randomString2(int length) {
		String str = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(str.length());
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/** 产生一个随机的字符串,排除难分辨的I/l、O/0*/
	public static String randomString(int length) {
		String str = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(str.length());
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/** 产生一个随机的数字*/
	public static String randomStringOnlyNumber(int length) {
		String str = "0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(10);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	
	/** 产生一个随机的字符串，适用于JDK 1.7 */
	public static String randomString17(int length) {
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append((char) (ThreadLocalRandom.current().nextInt(33, 128)));
		}
		return builder.toString();
	}
}
