package com.redfinger.manager.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/** 
 * @ClassName: ShellUtil 
 * @Description: TODO(linux 命令执行类) 
 * @author davis
 * @date 2014-12-11 下午8:03:34 
 *  
 */
public class ShellRemoteUtil {
	private static JSch jsch;  
	private static Session session;
	private static Channel channel;
	
	public final static int timeout = 20000;

	/** 
	 * 连接到指定的IP 
	 *  
	 * @throws JSchException 
	 */  
	public static void connect(String user, String passwd, String host) throws JSchException {  
	    jsch = new JSch();  
	    session = jsch.getSession(user, host, 22);  
	    session.setPassword(passwd);  
	 
	    java.util.Properties config = new java.util.Properties();  
	    config.put("StrictHostKeyChecking", "no");  
	    session.setConfig(config);  

	    session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
	}  
	
	/** 
     * 执行相关的命令 
     * @throws JSchException  
     */  
    public static String execShell(String command, String host, String user, String passwd) throws JSchException {  
       connect(user, passwd, host);  
       BufferedReader reader = null;  
       Channel channel = null;  
       String returnValue = null;
  
       try {
           if(command != null) {
               channel = session.openChannel("exec");  
               ((ChannelExec) channel).setCommand(command);
               command = null;
               channel.setInputStream(null);  
               ((ChannelExec) channel).setErrStream(System.err);  
 
               channel.connect();  
               InputStream in = channel.getInputStream();  
               reader = new BufferedReader(new InputStreamReader(in));  
               String buf = null;  
               while ((buf = reader.readLine()) != null) {
            	   returnValue = buf;  
               }  
           }  
        } catch (IOException e) {  
           e.printStackTrace();  
        	} catch (JSchException e) {  
        		e.printStackTrace();  
	        } finally {  
		        try {  
		             reader.close();  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }  
		        channel.disconnect();  
		        session.disconnect();  
	    }  
       return returnValue;
	}  

    public static void main(String[] args){
        String hostname = "10.0.0.12";
        String username = "gamemanage";
        String password = "fqY@fs92*7af$S";
        String gameShellPath = "/home/gamemanage/script";
        String gameFilePath = "/home/gamemanage/game-server/webapps/ROOT";
        String fileName = "apk/hszyy/com.tencent.mobileqq.apk";
        try {
			
			String command = (StringUtils.isNotBlank(gameShellPath)?("cd " + gameShellPath + ";") : "") + " ./run_file_info.sh " + gameFilePath+"/"+fileName;
			String result = ShellRemoteUtil.execShell(command, hostname, username, password);
			
			System.out.println(result);
		} catch (JSchException e) {
			e.printStackTrace();
		}
    }
            


}
