package com.redfinger.manager.modules.goods.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfGoodsExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGoodsMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "goodsId")
public class GoodsService extends BaseService<RfGoods, RfGoodsExample, RfGoodsMapper> {

	
}
