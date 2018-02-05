package com.redfinger.manager.modules.coupon.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.bean.view.ViewCoupon;
import com.redfinger.manager.common.domain.RfCoupon;
import com.redfinger.manager.common.domain.RfCouponExample;
import com.redfinger.manager.common.domain.RfCouponGoods;
import com.redfinger.manager.common.domain.RfCouponGoodsExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfCouponGoodsMapper;
import com.redfinger.manager.common.mapper.RfCouponMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.coupon.dto.RfCouponGood;

@Transactional
@Service
@PrimaryKeyAnnotation(field="couponId")
public class CouponService extends BaseService<RfCoupon, RfCouponExample, RfCouponMapper>{
	
	@Autowired
	RfCouponMapper rfCouponMapper;
	@Autowired
	RfCouponGoodsMapper couponGoodsMapper;
	
	public void saveCodeList(HttpServletRequest request,List<RfCoupon> list,List<RfCouponGood> couponList) throws Exception{
		if(null != list && list.size() > 0){
			for (RfCoupon rfCoupon : list) {
				Integer couponId = this.saveCoupon(rfCoupon);
				if( null != couponList && couponList.size() > 0){
					for (RfCouponGood record : couponList) {
						RfCouponGoods couponGoods = new RfCouponGoods();
						couponGoods.setCouponId(couponId);
						couponGoods.setGoodsId(record.getGoodsId());
						couponGoods.setCouponMoney(record.getCouponMoney());
						couponGoods.setStatus(YesOrNoStatus.YES);
						couponGoods.setEnableStatus(YesOrNoStatus.YES);
						couponGoods.setCreater(SessionUtils.getCurrentUserId(request));
						couponGoods.setCreateTime(new Date());
						couponGoodsMapper.insertSelective(couponGoods);
					}
				}
			}
		}
	}
	
	public void updateCoupont(HttpServletRequest request,RfCoupon bean,List<RfCouponGood> couponList) throws Exception{
		this.update(request, bean);
		if(null != couponList && couponList.size() > 0){
			RfCouponGoodsExample example = new RfCouponGoodsExample();
			example.createCriteria().andCouponIdEqualTo(bean.getCouponId());
			couponGoodsMapper.deleteByExample(example);
			for (RfCouponGood couponGood : couponList) {
				RfCouponGoods couponGoods = new RfCouponGoods();
				couponGoods.setCouponId(bean.getCouponId());
				couponGoods.setGoodsId(couponGood.getGoodsId());
				couponGoods.setCouponMoney(couponGood.getCouponMoney());
				couponGoods.setStatus(YesOrNoStatus.YES);
				couponGoods.setEnableStatus(YesOrNoStatus.YES);
				couponGoods.setCreater(SessionUtils.getCurrentUserId(request));
				couponGoods.setCreateTime(new Date());
				couponGoodsMapper.insertSelective(couponGoods);
			}
		}
		
	}
	
	public Integer saveCoupon(RfCoupon rfCoupon){
		rfCouponMapper.insertSelective(rfCoupon);
		return rfCoupon.getCouponId();
	}
}
