package com.redfinger.manager.common.bean.view;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.common.domain.RfActivityGoods;
import com.redfinger.manager.modules.activity.dto.RfActivityGoodsDto;

public class ViewActivityGoods {

	private List<RfActivityGoods> actRbcList = new ArrayList<RfActivityGoods>();
	private List<RfActivityGoods> actLotteryList = new ArrayList<RfActivityGoods>();
	private List<RfActivityGoods> actGoodsList = new ArrayList<RfActivityGoods>();
	private List<RfActivityGoods> walletGoodsList = new ArrayList<RfActivityGoods>();
	private List<RfActivityGoods> padTimeList = new ArrayList<RfActivityGoods>();
	private List<RfActivityGoods> invitePadTimeList = new ArrayList<RfActivityGoods>();
	private List<RfActivityGoodsDto> actCouponList = new ArrayList<RfActivityGoodsDto>();
	private List<RfActivityGoodsDto> sVipGoodsList = new ArrayList<RfActivityGoodsDto>();
	private List<RfActivityGoodsDto> invitePadTimeCouponList = new ArrayList<RfActivityGoodsDto>();
	private List<RfActivityGoods> inviteLotteryCountList = new ArrayList<RfActivityGoods>();
	
	private List<RfActivityGoodsDto> inviteSignupBoxCouponList = new ArrayList<RfActivityGoodsDto>();
	private List<RfActivityGoodsDto> inviteBuyBoxCouponList = new ArrayList<RfActivityGoodsDto>();
	
	
	public List<RfActivityGoodsDto> getInviteBuyBoxCouponList() {
		return inviteBuyBoxCouponList;
	}
	public void setInviteBuyBoxCouponList(List<RfActivityGoodsDto> inviteBuyBoxCouponList) {
		this.inviteBuyBoxCouponList = inviteBuyBoxCouponList;
	}
	public List<RfActivityGoodsDto> getInviteSignupBoxCouponList() {
		return inviteSignupBoxCouponList;
	}
	public void setInviteSignupBoxCouponList(List<RfActivityGoodsDto> inviteSignupBoxCouponList) {
		this.inviteSignupBoxCouponList = inviteSignupBoxCouponList;
	}
	public List<RfActivityGoods> getActRbcList() {
		return actRbcList;
	}
	public void setActRbcList(List<RfActivityGoods> actRbcList) {
		this.actRbcList = actRbcList;
	}
	public List<RfActivityGoods> getActLotteryList() {
		return actLotteryList;
	}
	public void setActLotteryList(List<RfActivityGoods> actLotteryList) {
		this.actLotteryList = actLotteryList;
	}
	public List<RfActivityGoods> getActGoodsList() {
		return actGoodsList;
	}
	public void setActGoodsList(List<RfActivityGoods> actGoodsList) {
		this.actGoodsList = actGoodsList;
	}
	public List<RfActivityGoodsDto> getActCouponList() {
		return actCouponList;
	}
	public void setActCouponList(List<RfActivityGoodsDto> actCouponList) {
		this.actCouponList = actCouponList;
	}
	public List<RfActivityGoodsDto> getsVipGoodsList() {
		return sVipGoodsList;
	}
	public void setsVipGoodsList(List<RfActivityGoodsDto> sVipGoodsList) {
		this.sVipGoodsList = sVipGoodsList;
	}
	public List<RfActivityGoods> getPadTimeList() {
		return padTimeList;
	}
	public void setPadTimeList(List<RfActivityGoods> padTimeList) {
		this.padTimeList = padTimeList;
	}
	public List<RfActivityGoods> getWalletGoodsList() {
		return walletGoodsList;
	}
	public void setWalletGoodsList(List<RfActivityGoods> walletGoodsList) {
		this.walletGoodsList = walletGoodsList;
	}
	public List<RfActivityGoods> getInvitePadTimeList() {
		return invitePadTimeList;
	}
	public void setInvitePadTimeList(List<RfActivityGoods> invitePadTimeList) {
		this.invitePadTimeList = invitePadTimeList;
	}
	public List<RfActivityGoodsDto> getInvitePadTimeCouponList() {
		return invitePadTimeCouponList;
	}
	public void setInvitePadTimeCouponList(List<RfActivityGoodsDto> invitePadTimeCouponList) {
		this.invitePadTimeCouponList = invitePadTimeCouponList;
	}
	public List<RfActivityGoods> getInviteLotteryCountList() {
		return inviteLotteryCountList;
	}
	public void setInviteLotteryCountList(List<RfActivityGoods> inviteLotteryCountList) {
		this.inviteLotteryCountList = inviteLotteryCountList;
	}
	
	
}
