package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppComment;
import com.redfinger.manager.common.domain.AppCommentExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppCommentMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class LogCommentService extends BaseService<AppComment, AppCommentExample, AppCommentMapper>{

}
