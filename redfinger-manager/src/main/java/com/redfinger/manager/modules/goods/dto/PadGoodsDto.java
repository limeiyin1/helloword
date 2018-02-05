package com.redfinger.manager.modules.goods.dto;

public class PadGoodsDto {
	
	private Integer goodsId;//产品id
	private String goodsName;//产品名称
	private Integer padCount;//设备数量
	
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getPadCount() {
		return padCount;
	}
	public void setPadCount(Integer padCount) {
		this.padCount = padCount;
	}

}
