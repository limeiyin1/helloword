package com.redfinger.manager.modules.goods.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ShopPackage;
import com.redfinger.manager.common.domain.ShopPackageCode;
import com.redfinger.manager.common.domain.ShopPackageCodeExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.ShopPackageCodeMapper;
import com.redfinger.manager.common.mapper.ShopPackageMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GoodsShopPackageCodeService extends BaseService<ShopPackageCode, ShopPackageCodeExample, ShopPackageCodeMapper> {
	@Autowired
	ShopPackageMapper packageMapper;
	@Autowired
	ShopPackageCodeMapper mapper;
	@Autowired
	GoodsShopPackageService service;
	@Autowired
	ShopPackageCodeMapper packageCodeMapper;
	

	public void updateCodes(HttpServletRequest request, ShopPackage bean, String codes,String check,Integer num) {
		ShopPackage shopPackage=service.get(bean.getId());
		ShopPackage shop=new ShopPackage();
		shop.setId(bean.getId());
		if (codes == null) {
			throw new BusinessException("请输入正确的参数！");
		}
		String userId = SessionUtils.getCurrentUserId(request);
		Date createTime = new Date();
		codes = codes.replaceAll(" ", "");
		String[] codearr = codes.split("\n");
		Set<String> set = Sets.newLinkedHashSet();
		List<String> list = Arrays.asList(codearr);
		set.addAll(list);
		
		Date beginTime=null;
		Date endTime=null;
		int codeday=0;
		//按天数自动计算
		if("on".equals(check)){
			Date begin=DateUtils.parseDate(bean.getBeginTimeStr());
			Date end=DateUtils.parseDate(bean.getEndTimeStr());
			int days=(int) DateUtils.getDistanceOfTwoDate(begin, end);
			beginTime=begin;
			//每天平均分的礼包码数量
			codeday=set.size()/days;
		}
		//按数量自动计算天数
		int countday=0;
		if(num!=null){
			beginTime=shopPackage.getBeginTime();
			//总共需要卖的多少天数
			countday=set.size()/num;
			//更新礼包的有效期时间为礼包码的最后一天
			bean.setEndTime(DateUtils.addDays(beginTime, countday));
		}
		if(bean.getBeginTimeStr()!=null){
			beginTime=DateUtils.parseDate(bean.getBeginTimeStr());
			endTime=DateUtils.parseDate(bean.getEndTimeStr());
		}
		int count = 0;
		for (String code : set) {
			code = code.replaceAll("\r", "");
			if("".equals(code)){
				continue;
			}
			List<ShopPackageCode> packageCodeList = this.initQuery().andEqualTo("code", code).andEqualTo("packageId", bean.getId()).findAll();
			if (packageCodeList.size() != 0)
				continue;
			ShopPackageCode shopPackageCode = new ShopPackageCode();
			shopPackageCode.setName(shopPackage.getName());
			shopPackageCode.setPackageId(shopPackage.getId());
			shopPackageCode.setCode(code);
			shopPackageCode.setCreateTime(createTime);
			shopPackageCode.setCreater(userId);
			shopPackageCode.setPrice(shopPackage.getPrice());
			shopPackageCode.setBeginTime(beginTime);
			if("on".equals(check) || num!=null){
				shopPackageCode.setEndTime(DateUtils.addDays(beginTime, 1));
				shop.setEndTime(shopPackageCode.getEndTime());
			}else{
				shopPackageCode.setEndTime(endTime);
				shop.setEndTime(shopPackageCode.getEndTime());
			}
			mapper.insertSelective(shopPackageCode);
			count++;
			if(codeday!=0 && count%codeday==0){
				beginTime=DateUtils.addDays(beginTime, 1);
			}
			if(num !=null && count%num==0){
				beginTime=DateUtils.addDays(beginTime, 1);
			}
		}
		bean.setNum(count);
		bean.setStock(count);
		packageMapper.updateByPrimaryKeySelectiveSync(bean);
		packageMapper.updateByPrimaryKeySelective(shop);
	}
	
	@ServiceAnnotation(name = ServiceMethod.after_remove)
	public void updateShopPackage(HttpServletRequest request, ShopPackageCode bean){
		//查库存
		ShopPackageCodeExample example=new ShopPackageCodeExample();
		example.createCriteria().andPackageIdEqualTo(bean.getPackageId()).andBuyFlagEqualTo("0");
		int stock=mapper.countByExample(example);
		//查总数
		ShopPackageCodeExample example1=new ShopPackageCodeExample();
		example1.createCriteria().andPackageIdEqualTo(bean.getPackageId());
		int num=mapper.countByExample(example1);
		ShopPackage packagebean=new ShopPackage();
		packagebean.setId(bean.getPackageId());
		packagebean.setStock(stock);
		packagebean.setNum(num);
		packageMapper.updateByPrimaryKeySelective(packagebean);
	}

	public void modify(HttpServletRequest request, ShopPackageCode bean) {
		String userId = SessionUtils.getCurrentUserId(request);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			ShopPackageCode packagecode=new ShopPackageCode();
			packagecode.setId(Integer.parseInt(idStr));
			packagecode.setBeginTime(bean.getBeginTime());
			packagecode.setEndTime(bean.getEndTime());
			packagecode.setModifier(userId);
			packagecode.setModifyTime(new Date());
			packageCodeMapper.updateByPrimaryKeySelective(packagecode);
		}
	}
}
