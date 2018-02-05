/**
 *
 * com.toone.web.authfinger.controller.notice  2015-8-31
 *
 */
package com.redfinger.manager.modules.notice.dto;

/**
 * @ClassName: NoticeDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-8-31 上午11:49:42
 * 
 */
public class NoticeDto {
	public static final String UNREAD = "0";
	private Integer noticeId;
	private Integer userNoticeId;// 用户公告ID
	private String noticeTitle;// 公告标题
	private String noticeContent;// 公告内容
	private String popStatus;// 是否弹出
	private String userNoticeStatus;// 阅读状态
	private String createTime;// 创建事件

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return userNoticeId
	 */
	public Integer getUserNoticeId() {
		return userNoticeId;
	}

	/**
	 * @param userNoticeId
	 *            要设置的 userNoticeId
	 */
	public void setUserNoticeId(Integer userNoticeId) {
		this.userNoticeId = userNoticeId;
	}

	/**
	 * @return noticeTitle
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * @param noticeTitle
	 *            要设置的 noticeTitle
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * @return noticeContent
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * @param noticeContent
	 *            要设置的 noticeContent
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * @return popStatus
	 */
	public String getPopStatus() {
		return popStatus;
	}

	/**
	 * @param popStatus
	 *            要设置的 popStatus
	 */
	public void setPopStatus(String popStatus) {
		this.popStatus = popStatus;
	}

	/**
	 * @return userNoticeStatus
	 */
	public String getUserNoticeStatus() {
		return userNoticeStatus;
	}

	/**
	 * @param userNoticeStatus
	 *            要设置的 userNoticeStatus
	 */
	public void setUserNoticeStatus(String userNoticeStatus) {
		this.userNoticeStatus = userNoticeStatus;
	}

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	@Override
	public String toString() {
		return "NoticeDto [noticeId=" + noticeId + ", userNoticeId=" + userNoticeId + ", noticeTitle=" + noticeTitle
				+ ", noticeContent=" + noticeContent + ", popStatus=" + popStatus + ", userNoticeStatus="
				+ userNoticeStatus + ", createTime=" + createTime + "]";
	}

}
