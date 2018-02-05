package com.redfinger.manager.modules.goods.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGoodsType;
import com.redfinger.manager.common.domain.RfGoodsTypeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGoodsTypeMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "typeId")
public class GoodsTypeService extends BaseService<RfGoodsType, RfGoodsTypeExample, RfGoodsTypeMapper> {

}
