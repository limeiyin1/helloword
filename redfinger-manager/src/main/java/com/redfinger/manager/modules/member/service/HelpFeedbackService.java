package com.redfinger.manager.modules.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.HelpFeedback;
import com.redfinger.manager.common.domain.HelpFeedbackExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.HelpFeedbackMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "feedbackId")
public class HelpFeedbackService extends BaseService<HelpFeedback, HelpFeedbackExample, HelpFeedbackMapper> {
}
