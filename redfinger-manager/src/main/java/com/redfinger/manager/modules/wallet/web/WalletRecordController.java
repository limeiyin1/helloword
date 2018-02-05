package com.redfinger.manager.modules.wallet.web;

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
import com.redfinger.manager.common.domain.RfWalletAccount;
import com.redfinger.manager.common.domain.RfWalletAccountRecord;
import com.redfinger.manager.common.domain.RfWalletRecord;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.wallet.service.WalletAccountRecordService;
import com.redfinger.manager.modules.wallet.service.WalletAccountService;
import com.redfinger.manager.modules.wallet.service.WalletRecordService;

@Controller
@RequestMapping(value="/wallet/recordManage")
public class WalletRecordController extends BaseController{
	
	@Autowired
	private WalletRecordService service;
	@Autowired
	private WalletAccountService accountService;
	@Autowired
	private WalletAccountRecordService walletAccountRecordService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfWalletRecord> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfWalletRecord bean, String userMobilePhone,Integer externalUserId) throws Exception {
		
		Integer userId = null;
		//会员ID查询 yirongze修改于 17-8-22
		if (StringUtils.isNotBlank(userMobilePhone)||externalUserId!=null) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(userMobilePhone,externalUserId);
			if (null != user) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		List<RfWalletRecord> list = service.initQuery(bean)
				.andEqualTo("recordType", bean.getRecordType())
				.andEqualTo("userId", userId)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		for (RfWalletRecord record : list) {
			if(null != record.getUserId()){
				if(null != record.getUserId()){
					record.getMap().put("userMobilePhone", userService.get(record.getUserId()).getUserMobilePhone());
				}
			}
			
			List<RfWalletAccountRecord> accountRecordlist = walletAccountRecordService.initQuery().
					andEqualTo("walletRecordId", record.getWalletRecordId()).findDelTrue();
			for (RfWalletAccountRecord accountRecord : accountRecordlist) {
				List<RfWalletAccount> accountList = accountService.initQuery().andEqualTo("accountId", accountRecord.getAccountId()).findDelTrue();
				if(null != accountList && accountList.size()>0){
					RfWalletAccount walletAccount = accountList.get(0);
					if(walletAccount != null && walletAccount.getAccountType().equals(WalletAccountType.CASH_ACCOUNT)){
						record.getMap().put("cashChangeAmount", accountRecord.getChangeAmount());
					}else if(walletAccount.getAccountType().equals(WalletAccountType.GIVE_ACCOUNT)){
						
						record.getMap().put("giveChangeAmount", accountRecord.getChangeAmount());
					}
				}
			}
			
			/** 查询客户端ID*/
			if(record.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(record.getUserId());
				/** 查询客户端ID*/
				record.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
			
		}
		PageInfo<RfWalletRecord> pageInfo = new PageInfo<RfWalletRecord>(list);
		return pageInfo;
	}
}
