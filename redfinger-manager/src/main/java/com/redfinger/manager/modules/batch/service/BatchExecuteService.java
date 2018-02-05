package com.redfinger.manager.modules.batch.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TkBatchExecute;
import com.redfinger.manager.common.domain.TkBatchExecuteExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkBatchExecuteMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="executeId")
public class BatchExecuteService extends BaseService<TkBatchExecute, TkBatchExecuteExample, TkBatchExecuteMapper> {

}
