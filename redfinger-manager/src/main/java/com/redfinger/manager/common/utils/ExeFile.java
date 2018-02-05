package com.redfinger.manager.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExeFile {
	public static void main(String args[]){  
		String path = "C://Users//liulu//Downloads//RedFingerPlayer_Prod_Install_v201018.exe";
		String writePath = "C://Users//liulu//Downloads//temp//channel.txt";
		String sourceFilePath = "C://Users//liulu//Downloads//temp";
		String zipFilePath = "C://Users//liulu//Downloads";
		String fileName = "RedFingerPlayer_Prod_Install_v201018_hsz";
		try {
			//ExeFile.unzip(path, exePath);
			//ExeFile.writeFile(writePath, "{channelId:com.redfinger.app.hsz,channeName:红手指}");
			//ExeFile.unZip(path);
			ExeFile.fileToZip(sourceFilePath, zipFilePath, fileName);
			//ExeFile.copyExe();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
	
	/**
	 * 写文件
	 * @param filePath
	 * @param writeStr
	 */
	public static void writeFile(String filePath,String writeStr){
		try{
	        File file=new File(filePath);
	        if(!file.exists()){
	            file.mkdir();
	        }
	        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true 
	        out.write("".getBytes("utf-8"));
	        out.write(writeStr.getBytes("utf-8"));//注意需要转换对应的字符集
	        out.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
	}
	
	/**
	 * 解压文件
	 * @param path 解压文件路径
	 * @param exePath 解压之后保存的路劲
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void unzip(String path,String exePath) throws IOException, InterruptedException{
		Process process = null;  
        File file =new File(path);  
          
        //process = Runtime.getRuntime().exec("cmd.exe /c apktool d "+path+" "+exePath+ " " ,null,file); 
        //process = Runtime.getRuntime().exec("cmd /c "+path+" x -y d:\\auto.rar "+exePath);
        process = Runtime.getRuntime().exec("unzip "+path+" "+exePath);
        if(process.waitFor()!=0){
        	System.out.println("解压失败。。。");  
        }else{
        	System.out.println("解压成功。。。");  
        }
	}
	
	public static void copyExe(){ 
		Runtime rn=Runtime.getRuntime();   
		Process p= null;   
		try {   
		    //p = rn.exec( "cmd   /k   dir ");   
		    p = rn.exec( "cp C://Users//liulu//Downloads//RedFingerPlayer_Prod_Install_v201018.exe C:\\Users\\liulu\\Downloads\\asd.txt");   
		 
			InputStream   in   =p.getInputStream();   
			BufferedReader   br   =   new   BufferedReader(new   InputStreamReader(in));   
			String   str   =   null;   
			while((str=br.readLine())!= null){   
				System.out.println(str);   
			}   
			br.close();   
		}catch(Exception   e){   
			e.printStackTrace();
		}   
	}
	
	public static void unZip(String filename) throws Exception {  
		File zipFile = new File(filename);  
        if (!zipFile.exists()) {  
            return;  
        }  
  
        File zipExeFile = new File("C:\\Program Files (x86)\\7-Zip\\7z.exe");  
        String exec = String.format("%s x -aoa \"%s\" -o\"%s\"", zipExeFile.getAbsolutePath(), zipFile.getAbsolutePath(), zipFile.getParent()+"\\temp");  
        Runtime runtime = Runtime.getRuntime();  
        runtime.exec(exec);  
	}  
	
	/** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".exe");  
                if(zipFile.exists()){  
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".exe" +"打包文件.");  
                }else{  
                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();    
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    }  
}
