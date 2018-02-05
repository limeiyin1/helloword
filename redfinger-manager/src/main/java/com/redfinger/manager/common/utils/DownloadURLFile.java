package com.redfinger.manager.common.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.domain.MarketGamePackage;
/**
 * 
* @ClassName: DownloadURLFile
* @Description: 根据URL下载文件工具类
* @author tluo
* @date 2015年10月13日 下午5:43:07
*
 */
public class DownloadURLFile { 
	
	public static MarketGamePackage  download(MarketGamePackage marketGamePackage) throws Exception{
			String url_download = marketGamePackage.getDownload();
			String url_icon = marketGamePackage.getIcon();
			String url_screenshot = marketGamePackage .getScreenshot();
			
			//下载APK
			String apkName = marketGamePackage.getPackageName()+".apk";
			String local_durl = ConfigConstantsDb.configDownloadLocal()+apkName;
			downloadFromUrl(url_download,local_durl);
			
			marketGamePackage.setDownloadLocal(local_durl);   //设置本地APK下载地址
			//下载logo图片
			String iconName = getFileNameFromUrl(url_icon, ".jpg");
			String local_iurl = ConfigConstantsDb.configDownloadLocal()+iconName;
			downloadFromUrl(url_icon, local_iurl);
			marketGamePackage.setIconLocal(local_iurl);   //设置本地LOGO下载地址
			//下载游戏图片	
			String[] arr_screenshot = url_screenshot.split(",");
			StringBuffer sb = new StringBuffer();
			for(String url : arr_screenshot){
				String screenshotName = getFileNameFromUrl(url, ".jpg");
				String local_surl = ConfigConstantsDb.configDownloadLocal()+screenshotName;
				downloadFromUrl(url, local_surl);
				sb.append(local_surl);
				sb.append(",");
			}
			marketGamePackage.setScreenshotLocal(sb.toString());   //设置本地图片下载地址
			return marketGamePackage;
	}
	 /**
	  * 
	 * @Title: downloadFromUrl 
	 * @Description: 根据URL下载
	 * @param @param url
	 * 				url地址
	 * @param @param dir
	 * 				文件存放地址
	 * @param @return
	 * @return String
	 * @throws
	  */
    public static String downloadFromUrl(String url,String localUrl) { 
	    try {		
	    	int byteread = 0;
	    	URL downloadUrl = new URL(url);
	        URLConnection conn = downloadUrl.openConnection();
	        InputStream inStream = conn.getInputStream();
	        FileOutputStream fs = new FileOutputStream(localUrl);
	        //一次写入的大小
	        byte[] buffer = new byte[1204];
	        while ((byteread = inStream.read(buffer)) != -1) {
	            fs.write(buffer, 0, byteread);
	        }
	        fs.close();
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Fault!";  
        }   
        return "Successful!";  
    }  
      
    public static String getFileNameFromUrl(String url,String fileType){  
        String name = new Long(System.currentTimeMillis()).toString() + fileType;  
        int index = url.lastIndexOf("/");  
        if(index > 0){  
            name = url.substring(index + 1)+ fileType;  
            if(name.trim().length()>0){  
                return name;  
            }  
        }  
        return name;  
    }  
}  