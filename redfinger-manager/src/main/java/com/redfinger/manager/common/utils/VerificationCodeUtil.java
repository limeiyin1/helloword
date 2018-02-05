package com.redfinger.manager.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VerificationCodeUtil {
	public static final String Verification = "Verification";
	
	public static void writeRandomImage(HttpServletResponse response,HttpSession session,String sessionKey,Integer width, Integer height) throws IOException{
		response.reset();
		if(null==width||0==width){
	    	// 验证码图片的宽度。
	    	 width = 120;
	    }
	    if(null==height||0==height){
	    	// 验证码图片的宽度。
	    	height = 25;
	    }
		session.removeAttribute(sessionKey);

		// 生成动态验证码
		String imgeCode = RandomUtils.randomString(4);
		
		// 1.在内存中创建一张图片
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 2.得到图片
		Graphics g = bi.getGraphics();
		// 3.设置图片的背影色
		g.setColor(Color.WHITE);
		// 填充区域
		g.fillRect(0, 0, width, height);
		// 4.设置图片的边框
		g.setColor(Color.WHITE);
		// 边框区域
		g.drawRect(1, 1, width - 2, height - 2);
		// 设置线条个数并画干扰线
		for (int i = 0; i < 10; i++) {
			int x1 = new Random().nextInt(width);
			int y1 = new Random().nextInt(height);
			int x2 = new Random().nextInt(width);
			int y2 = new Random().nextInt(height);
		    int red =new Random().nextInt(255);  
            int green = new Random().nextInt(255);  
            int blue = new Random().nextInt(255); 
            g.setColor(new Color(red, green, blue));  
			g.drawLine(x1, y1, x2, y2);
		}
		String random = createRandomChar((Graphics2D) g, imgeCode);// 根据客户端传递的createTypeFlag标识生成验证码图片
		// 8.设置响应头通知浏览器以图片的形式打开
		response.setContentType("image/jpeg");// 等同于response.setHeader("Content-Type","image/jpeg");
		// 9.设置响应头控制浏览器不要缓存
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		// 10.将图片写给浏览器
		ImageIO.write(bi, "jpg", response.getOutputStream());
		session.setAttribute(sessionKey, random);
		//logger.debug("session:[" + session.getId() + "] 图形验证码生成成功："+random);
	}
	
	private static String createRandomChar(Graphics2D g, String baseChar) {
		StringBuffer sb = new StringBuffer();
		int x = 5;
		String ch = "";
		// 控制字数
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18)); 
		for (int i = 0; i < 4; i++) {
			// 设置字体旋转角度
			int degree = new Random().nextInt() % 30;
			ch = baseChar.charAt(new Random().nextInt(baseChar.length())) + "";
			sb.append(ch);
			// 正向角度
			g.rotate(degree * Math.PI / 180, x, 20);
			g.setColor(Color.RED);
			g.drawString(ch, x, 20);
			// 反向角度
			g.rotate(-degree * Math.PI / 180, x, 20);
			x += 30;
		}
		return sb.toString();
	}
	
}
