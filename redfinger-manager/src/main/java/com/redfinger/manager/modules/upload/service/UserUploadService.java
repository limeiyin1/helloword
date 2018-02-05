package com.redfinger.manager.modules.upload.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfUserUpload;
import com.redfinger.manager.common.domain.RfUserUploadExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfUserUploadMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="uploadId")
public class UserUploadService extends BaseService<RfUserUpload, RfUserUploadExample, RfUserUploadMapper> {

	
}
