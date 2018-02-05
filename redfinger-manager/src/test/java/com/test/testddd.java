package com.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.mapper.RfGoodsMapper;

public class testddd {
	@Autowired
	RfGoodsMapper mapper;
	@Test
	public void getuser(){
		 RfGoods r = mapper.selectByPrimaryKey(1);
		 System.out.println(r);
		 System.out.println(r.getGoodsCode());
	}
}
