package com.redfinger.manager.modules.coupon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfCoupon;
import com.redfinger.manager.common.domain.RfCouponExample;
import com.redfinger.manager.common.domain.RfCouponGoods;
import com.redfinger.manager.common.domain.RfCouponGoodsExample;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfCouponTypeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfCouponGoodsMapper;
import com.redfinger.manager.common.mapper.RfCouponMapper;
import com.redfinger.manager.common.mapper.RfCouponTypeMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.coupon.dto.RfCouponGood;

@Transactional
@Service
@PrimaryKeyAnnotation(field="typeId")
public class CouponTypeService extends BaseService<RfCouponType, RfCouponTypeExample, RfCouponTypeMapper>{

	@Autowired
	private RfCouponMapper rfCouponMapper;
	@Autowired
	private RfCouponGoodsMapper couponGoodsMapper;
	
	public void updateCoupon(HttpServletRequest request, Integer typeId,String couponCondition,String batchNo,
			Date couponStartTime,Date couponEndTime,String couponType,List<RfCouponGood> couponList) throws Exception{
		RfCoupon record = new RfCoupon();
		record.setCouponStatus(YesOrNoStatus.NO);
		record.setBindStatus(YesOrNoStatus.NO);
		record.setTypeId(typeId);
		record.setCouponCondition(couponCondition);
		record.setBatchNo(batchNo);
		record.setCouponType(couponType);
		record.setCouponStartTime(couponStartTime);
		record.setCouponEndTime(couponEndTime);
		record.setModifier(SessionUtils.getCurrentUserId(request));
		record.setModifyTime(new Date());
		rfCouponMapper.updateByTypeIdSelective(record);
		
		if(null != couponList && couponList.size()>0){
			RfCouponExample example = new RfCouponExample();
			example.createCriteria().andTypeIdEqualTo(typeId)
			.andCouponStatusEqualTo(YesOrNoStatus.NO)
			.andBindStatusEqualTo(YesOrNoStatus.NO);
			List<Integer> ids = new ArrayList<Integer>();
			List<RfCoupon> list = rfCouponMapper.selectByExample(example);
			if(null !=list && list.size() > 0){
				for (RfCoupon rfCoupon : list) {
					ids.add(rfCoupon.getCouponId());
				}
				RfCouponGoodsExample couponGoodsExample = new RfCouponGoodsExample();
				couponGoodsExample.createCriteria().andCouponIdIn(ids);
				couponGoodsMapper.deleteByExample(couponGoodsExample);
				for (Integer id : ids) {
					for(RfCouponGood rfCouponGood : couponList){
						RfCouponGoods couponGoods = new RfCouponGoods();
						couponGoods.setCouponId(id);
						couponGoods.setGoodsId(rfCouponGood.getGoodsId());
						couponGoods.setCouponMoney(rfCouponGood.getCouponMoney());
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
}
