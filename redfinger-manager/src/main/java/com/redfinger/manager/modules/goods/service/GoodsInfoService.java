package com.redfinger.manager.modules.goods.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGoodsInfo;
import com.redfinger.manager.common.domain.RfGoodsInfoExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGoodsInfoMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "infoId")
public class GoodsInfoService extends BaseService<RfGoodsInfo, RfGoodsInfoExample, RfGoodsInfoMapper> {

}
