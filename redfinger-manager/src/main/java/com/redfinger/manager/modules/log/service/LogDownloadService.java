package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppLogDownload;
import com.redfinger.manager.common.domain.AppLogDownloadExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppLogDownloadMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class LogDownloadService  extends BaseService<AppLogDownload, AppLogDownloadExample,AppLogDownloadMapper > {

}
