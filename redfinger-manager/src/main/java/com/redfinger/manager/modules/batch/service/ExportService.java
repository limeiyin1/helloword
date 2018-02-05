package com.redfinger.manager.modules.batch.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.domain.RfExportExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfExportMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="exportId")
public class ExportService extends BaseService<RfExport, RfExportExample, RfExportMapper> {

}
