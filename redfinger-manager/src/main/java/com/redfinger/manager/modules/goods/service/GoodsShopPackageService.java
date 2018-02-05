package com.redfinger.manager.modules.goods.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ShopPackage;
import com.redfinger.manager.common.domain.ShopPackageCode;
import com.redfinger.manager.common.domain.ShopPackageCodeExample;
import com.redfinger.manager.common.domain.ShopPackageExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.ShopPackageCodeMapper;
import com.redfinger.manager.common.mapper.ShopPackageMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GoodsShopPackageService extends BaseService<ShopPackage,ShopPackageExample,ShopPackageMapper>{

	@Autowired
	ShopPackageMapper mapper;
	@Autowired
	ShopPackageCodeMapper codeMapper;
	
	public boolean isExist(String name) {// 判断礼包是否已经存在
		ShopPackageExample example = new ShopPackageExample();
		ShopPackageExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		ShopPackage shop = mapper.selectOneByExample(example);
		if (shop == null) {
			return true;
		} else {
			throw new BusinessException("此礼包已经存在！");
		}
	}
	
	@ServiceAnnotation(name = ServiceMethod.after_save)
	public void afterStart(HttpServletRequest request, ShopPackage bean) {
		bean=this.get(bean.getId());
		bean.setEnableStatus("0");
		try {
			this.update(request, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ServiceAnnotation(name = ServiceMethod.after_update)
	public void afterUpadate(HttpServletRequest request, ShopPackage bean){
		bean=this.get(bean.getId());
		ShopPackageCode codebean=new ShopPackageCode();
		codebean.setName(bean.getName());
		codebean.setPrice(bean.getPrice());
		codebean.setModifier(bean.getModifier());
		codebean.setModifyTime(bean.getModifyTime());
		ShopPackageCodeExample example=new ShopPackageCodeExample();
		example.createCriteria().andPackageIdEqualTo(bean.getId());
		codeMapper.updateByExampleSelective(codebean, example);
	}
}