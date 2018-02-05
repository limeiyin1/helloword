package com.redfinger.manager.modules.wallet.web;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.WalletAccountType;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfWallet;
import com.redfinger.manager.common.domain.RfWalletAccount;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.wallet.service.WalletAccountService;
import com.redfinger.manager.modules.wallet.service.WalletService;

@Controller
@RequestMapping(value="/wallet/userManage")
public class UserWalletController extends BaseController{

	@Autowired
	private WalletAccountService service;
	@Autowired
	private UserService userService;
	@Autowired
	private WalletService walletService;

	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfWallet> list(HttpServletRequest request,
			HttpServletResponse response, Model model, String userMobilePhone,
			Integer accountAmountGte,Integer accountAmountLte,RfWallet rfWallet,Integer externalUserId)
			throws Exception {
		//会员ID查询 yirongze修改于 17-8-22
		if (StringUtils.isNotBlank(userMobilePhone)||externalUserId!=null) {
			RfUser mapper = userService.getUserByExternalUserIdOrUserPhone(userMobilePhone,externalUserId);
			if (null != mapper) {
				rfWallet.setUserId(mapper.getUserId());
			} else {
				rfWallet.setUserId(-1);
			}
		}
		List<RfWallet> list = walletService.initQuery(rfWallet)
				.andEqualTo("userId", rfWallet.getUserId())
				.andGreaterThanOrEqualTo("walletAmount",accountAmountGte!=null?accountAmountGte*100:null)
				.andLessThanOrEqualTo("walletAmount",accountAmountLte!=null?accountAmountLte*100:null)
				.addOrderForce(rfWallet.getSort(), rfWallet.getOrder())
				.pageDelTrue(rfWallet.getPage(), rfWallet.getRows());
		
		if(null != list && list.size() > 0){
			for (RfWallet wallet : list) {
				int walletAccountCount = service.initQuery().andEqualTo("walletId", wallet.getWalletId()).countStopTrue();
				wallet.setWalletAccountCount(walletAccountCount);
				
				if(null != wallet && null != wallet.getUserId()){
					RfUser user = userService.get(wallet.getUserId());
					if(null != user){
						wallet.getMap().put("userMobilePhone", user.getUserMobilePhone());
					}
				}
				
				List<RfWalletAccount> accountList = service.initQuery().andEqualTo("walletId", wallet.getWalletId()).findDelTrue();
				for (RfWalletAccount account : accountList) {
					if(account.getAccountType().equals(WalletAccountType.CASH_ACCOUNT)){
						wallet.getMap().put("cashAccountAmount", account.getAccountAmount());
					}else if(account.getAccountType().equals(WalletAccountType.GIVE_ACCOUNT)){
						
						wallet.getMap().put("giveAccountAmount", account.getAccountAmount());
					}
					
				}
				
				/** 查询客户端ID*/
				if(wallet.getUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(wallet.getUserId());
					/** 根据用户ID查询客户端ID*/
					wallet.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
				
			}
		}
		PageInfo<RfWallet> pageInfo = new PageInfo<RfWallet>(list);
		return pageInfo;
	}
}
