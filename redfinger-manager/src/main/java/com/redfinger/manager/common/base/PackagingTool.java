package com.redfinger.manager.common.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redfinger.manager.common.exception.BusinessException;

public class PackagingTool {
	
	public static Logger log = LoggerFactory.getLogger(PackagingTool.class);
	
	private static final String CHANNEL_PREFIX = "/META-INF/";
	private static final String CHANNEL_PATH_MATCHER = "regex:/META-INF/mtchannel_[0-9a-zA-Z]{1,5}";
	private static String source_path;
	// 这个渠道文件名称需要跟装载渠道名称一致
	private static final String channel_file_name = "channel_list.txt";
	private static final String channel_flag = "channel_";

	public static void main(String[] args) throws Exception {
		/*if (args.length <= 1) {
			System.out.println("请输入文件路径作为参数");
			return;
		}
		final String source_apk_path = args[0]; // d:\test.
		
		System.out.println("渠道名称："+args[1]);
		
		System.out.println("source_apk_path:" + source_apk_path);
		int last_index = source_apk_path.lastIndexOf("/") + 1;
		source_path = source_apk_path.substring(0, last_index);
		System.out.println("source_path:" + source_path);
		final String source_apk_name = source_apk_path.substring(last_index,
				source_apk_path.length());
		final String last_name = ".apk";
		String new_apk_path = source_path
				+ source_apk_name.substring(0, source_apk_name.length()
						- last_name.length()) + "_Channel_" + args[1] + last_name;
		String channelFilePath = source_path + "channel";
		System.out.println("读取文件路径");
		String channelContent = readTxtFile(channelFilePath);
		System.out.println("读取文件内容："+channelContent);
		copyFile(source_apk_path, new_apk_path);
		System.out.println("修改渠道");
		changeChannel(new_apk_path, "channel", channelContent);*/
		
		PackagingTool.copyChannelFile("C:/Users/liulu/Desktop/RedFingerClient.apk", "com.redfinger.app.hsz","");
		
	}
	
	/**
	 * 
	 * @param fileLink 文件地址
	 * @param channelName 渠道名称
	 * channelContent json数据格式
	 * @throws IOException 
	 */
	public static long copyChannelFile(String fileLink,String channelName,String channelContent){
		if(StringUtils.isBlank(fileLink)){//文件地址为空
			throw new BusinessException("文件地址不能为空");
		}
		if(StringUtils.isBlank(channelName)){//渠道名称
			throw new BusinessException("渠道不能为空");
		}
		log.info("[fileLink:{},channelName:{}]",new Object[]{fileLink,channelName});
		
		int last_index = fileLink.lastIndexOf("/") + 1;
		source_path = fileLink.substring(0, last_index);
		log.info("apk地址:" + source_path);
		final String source_apk_name = fileLink.substring(last_index,
				fileLink.length());
		final String last_name = ".apk";
		
		String channelNames[] = channelName.split("\\.");
		String channelLastName = channelNames[channelNames.length-1];
		
		String new_apk_path = source_path
				+ source_apk_name.substring(0, source_apk_name.length()
						- last_name.length()) + "_" + channelLastName + last_name;
		
		log.info("新apk地址:"+new_apk_path);
		
		
		/*String channelFilePath = source_path + "channel";
		log.info("读取文件路径");
		String channelContent = readTxtFile(channelFilePath);*/
		/*String channelContent = "{\"channelName\":\"test\",\"channelID\":\"com.redfinger.app.hsz\",\"channelVersion\":\"2.1.15\"}";*/
		log.info("write json ："+channelContent);
		try{
			copyFile(fileLink, new_apk_path);
			changeChannel(new_apk_path, "channel", channelContent);
		}catch(Exception e){
			log.error(channelName+"分包失败:"+e.getMessage(),e);
			throw new BusinessException(channelName+"分包失败");
		}
		return new File(new_apk_path).length();
	}

	public static String readTxtFile(String filePath){
		try{
			StringBuffer buffer = new StringBuffer();
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					buffer.append(lineTxt+"\n");
				}
				if(null != read){
					read.close();
				}
				return buffer.toString();
			} else {
				file.createNewFile();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 修改渠道号，原理是在apk的META-INF下新建一个文件名为渠道号的文件
	 */
	public static boolean changeChannel(final String zipFilename,
			String channel, String jsonstr)  throws Exception {
		try(FileSystem zipfs = createZipFileSystem(zipFilename, false)){
			
			final Path root = zipfs.getPath("/META-INF/");
			ChannelFileVisitor visitor = new ChannelFileVisitor();
			Files.walkFileTree(root, visitor);

			Path existChannel = visitor.getChannelFile();
			Path newChannel = zipfs.getPath(CHANNEL_PREFIX + channel);
			if (existChannel != null) {
				Files.move(existChannel, newChannel,
						StandardCopyOption.ATOMIC_MOVE);
			} else {
				Path path = Files.createFile(newChannel);
				if (path != null) {
					BufferedWriter writer = Files.newBufferedWriter(path,
							StandardCharsets.UTF_8, StandardOpenOption.APPEND); // 追加
					writer.write(jsonstr);
					writer.close();
					log.info("writer success");
				}
			}
		}catch(Exception e){
			log.error("写入channel文件失败"+e.getMessage(), e);
			throw new BusinessException("写入channel文件失败");
		}
		return true;
	}

	private static FileSystem createZipFileSystem(String zipFilename,
			boolean create) throws IOException {
		Path path = Paths.get(zipFilename);
		URI uri = URI.create("jar:file:" + path.toUri().getPath());
		
		final Map<String, String> env = new HashMap<>();
		if (create) {
			env.put("create", "true");
		}
		return FileSystems.newFileSystem(uri, env);
	}

	private static class ChannelFileVisitor extends SimpleFileVisitor<Path> {
		private Path channelFile;
		private PathMatcher matcher = FileSystems.getDefault().getPathMatcher(
				CHANNEL_PATH_MATCHER);

		public Path getChannelFile() {
			return channelFile;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			if (matcher.matches(file)) {
				channelFile = file;
				return FileVisitResult.TERMINATE;
			} else {
				return FileVisitResult.CONTINUE;
			}
		}
	}

	/** 得到渠道列表 
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException */
	private static ArrayList<String> getChannelList(String filePath) throws Exception {
		ArrayList<String> channel_list = new ArrayList<String>();

		String encoding = "UTF-8";
		File file = new File(filePath);
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				// System.out.println(lineTxt);
				if (lineTxt != null && lineTxt.length() > 0) {
					channel_list.add(lineTxt);
				}
			}
			read.close();
		} else {
			log.info("找不到指定的文件");
		}

		return channel_list;
	}

	/** 复制文件 */
	private static void copyFile(final String source_file_path,
			final String target_file_path) throws IOException {

		File sourceFile = new File(source_file_path);
		File targetFile = new File(target_file_path);
		if(targetFile.exists()){
			targetFile.delete();
		}
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} catch (Exception e) {
			log.error("复制文件失败："+e.getMessage(),e);
			e.printStackTrace();
			throw new BusinessException("复制母包apk文件失败");
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
}