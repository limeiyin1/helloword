package com.redfinger.manager.common.domain;

import com.redfinger.manager.common.base.BaseDomain;
import java.io.Serializable;
import java.util.Date;

public class AgentSellerBusiness extends BaseDomain implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_ID = "business_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer businessId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.seller_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_SELLER_ID = "seller_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.seller_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String sellerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_TYPE = "business_type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String businessType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_title
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_TITLE = "business_title";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_title
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String businessTitle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_bewrite
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_BEWRITE = "business_bewrite";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_bewrite
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String businessBewrite;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_cost
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_COST = "business_cost";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_cost
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer businessCost;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.legalize_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_LEGALIZE_STATUS = "legalize_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.legalize_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String legalizeStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_score
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_SCORE = "business_score";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_score
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String businessScore;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_cycle
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_BUSINESS_CYCLE = "business_cycle";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.business_cycle
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer businessCycle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_GAME_ID = "game_id";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer gameId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_GAME_TYPE = "game_type";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String gameType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_GAME_NAME = "game_name";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String gameName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_channel
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_GAME_CHANNEL = "game_channel";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.game_channel
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String gameChannel;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.card_payer
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_CARD_PAYER = "card_payer";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.card_payer
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String cardPayer;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.security_gold
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_SECURITY_GOLD = "security_gold";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.security_gold
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Integer securityGold;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_CREATER = "creater";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String creater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_CREATE_TIME = "create_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_MODIFIER = "modifier";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String modifier;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_MODIFY_TIME = "modify_time";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private Date modifyTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_STATUS = "status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_ENABLE_STATUS = "enable_status";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String enableStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public static final String FD_REMARK = "remark";
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column agent_seller_business.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private String remark;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_id
	 * @return  the value of agent_seller_business.business_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getBusinessId() {
		return businessId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_id
	 * @param businessId  the value for agent_seller_business.business_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.seller_id
	 * @return  the value of agent_seller_business.seller_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getSellerId() {
		return sellerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.seller_id
	 * @param sellerId  the value for agent_seller_business.seller_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId == null ? null : sellerId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_type
	 * @return  the value of agent_seller_business.business_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_type
	 * @param businessType  the value for agent_seller_business.business_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType == null ? null : businessType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_title
	 * @return  the value of agent_seller_business.business_title
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getBusinessTitle() {
		return businessTitle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_title
	 * @param businessTitle  the value for agent_seller_business.business_title
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle == null ? null : businessTitle.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_bewrite
	 * @return  the value of agent_seller_business.business_bewrite
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getBusinessBewrite() {
		return businessBewrite;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_bewrite
	 * @param businessBewrite  the value for agent_seller_business.business_bewrite
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessBewrite(String businessBewrite) {
		this.businessBewrite = businessBewrite == null ? null : businessBewrite.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_cost
	 * @return  the value of agent_seller_business.business_cost
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getBusinessCost() {
		return businessCost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_cost
	 * @param businessCost  the value for agent_seller_business.business_cost
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessCost(Integer businessCost) {
		this.businessCost = businessCost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.legalize_status
	 * @return  the value of agent_seller_business.legalize_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getLegalizeStatus() {
		return legalizeStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.legalize_status
	 * @param legalizeStatus  the value for agent_seller_business.legalize_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setLegalizeStatus(String legalizeStatus) {
		this.legalizeStatus = legalizeStatus == null ? null : legalizeStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_score
	 * @return  the value of agent_seller_business.business_score
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getBusinessScore() {
		return businessScore;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_score
	 * @param businessScore  the value for agent_seller_business.business_score
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessScore(String businessScore) {
		this.businessScore = businessScore == null ? null : businessScore.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.business_cycle
	 * @return  the value of agent_seller_business.business_cycle
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getBusinessCycle() {
		return businessCycle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.business_cycle
	 * @param businessCycle  the value for agent_seller_business.business_cycle
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setBusinessCycle(Integer businessCycle) {
		this.businessCycle = businessCycle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.game_id
	 * @return  the value of agent_seller_business.game_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.game_id
	 * @param gameId  the value for agent_seller_business.game_id
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.game_type
	 * @return  the value of agent_seller_business.game_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getGameType() {
		return gameType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.game_type
	 * @param gameType  the value for agent_seller_business.game_type
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setGameType(String gameType) {
		this.gameType = gameType == null ? null : gameType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.game_name
	 * @return  the value of agent_seller_business.game_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.game_name
	 * @param gameName  the value for agent_seller_business.game_name
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName == null ? null : gameName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.game_channel
	 * @return  the value of agent_seller_business.game_channel
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getGameChannel() {
		return gameChannel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.game_channel
	 * @param gameChannel  the value for agent_seller_business.game_channel
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setGameChannel(String gameChannel) {
		this.gameChannel = gameChannel == null ? null : gameChannel.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.card_payer
	 * @return  the value of agent_seller_business.card_payer
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getCardPayer() {
		return cardPayer;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.card_payer
	 * @param cardPayer  the value for agent_seller_business.card_payer
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setCardPayer(String cardPayer) {
		this.cardPayer = cardPayer == null ? null : cardPayer.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.security_gold
	 * @return  the value of agent_seller_business.security_gold
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Integer getSecurityGold() {
		return securityGold;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.security_gold
	 * @param securityGold  the value for agent_seller_business.security_gold
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setSecurityGold(Integer securityGold) {
		this.securityGold = securityGold;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.creater
	 * @return  the value of agent_seller_business.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.creater
	 * @param creater  the value for agent_seller_business.creater
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.create_time
	 * @return  the value of agent_seller_business.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.create_time
	 * @param createTime  the value for agent_seller_business.create_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.modifier
	 * @return  the value of agent_seller_business.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.modifier
	 * @param modifier  the value for agent_seller_business.modifier
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.modify_time
	 * @return  the value of agent_seller_business.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.modify_time
	 * @param modifyTime  the value for agent_seller_business.modify_time
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.status
	 * @return  the value of agent_seller_business.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.status
	 * @param status  the value for agent_seller_business.status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.enable_status
	 * @return  the value of agent_seller_business.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getEnableStatus() {
		return enableStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.enable_status
	 * @param enableStatus  the value for agent_seller_business.enable_status
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus == null ? null : enableStatus.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column agent_seller_business.remark
	 * @return  the value of agent_seller_business.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column agent_seller_business.remark
	 * @param remark  the value for agent_seller_business.remark
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}