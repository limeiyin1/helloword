package com.redfinger.manager.common.base;

public class FilePathUtils {
	private String imageUrl;
	private String imageLinkUrl;
	private String gcUrl;
	
	private String configDownloadUrl;//版本控制存放地址
	private String configDownloadLinkUrl;//版本控制链接地址
	
	private String filePath;			//文件根目录
	private String fileLinkUrl;			//文件链接地址
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}
	public void setImageLinkUrl(String imageLinkUrl) {
		this.imageLinkUrl = imageLinkUrl;
	}
	public String getGcUrl() {
		return gcUrl;
	}
	public void setGcUrl(String gcUrl) {
		this.gcUrl = gcUrl;
	}
	public String getConfigDownloadUrl() {
		return configDownloadUrl;
	}
	public void setConfigDownloadUrl(String configDownloadUrl) {
		this.configDownloadUrl = configDownloadUrl;
	}
	public String getConfigDownloadLinkUrl() {
		return configDownloadLinkUrl;
	}
	public void setConfigDownloadLinkUrl(String configDownloadLinkUrl) {
		this.configDownloadLinkUrl = configDownloadLinkUrl;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileLinkUrl() {
		return fileLinkUrl;
	}
	public void setFileLinkUrl(String fileLinkUrl) {
		this.fileLinkUrl = fileLinkUrl;
	}
}
