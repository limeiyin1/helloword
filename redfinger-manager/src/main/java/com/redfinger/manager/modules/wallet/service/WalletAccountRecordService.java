package com.redfinger.manager.modules.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWalletAccountRecord;
import com.redfinger.manager.common.domain.RfWalletAccountRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWalletAccountRecordMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="accountRecordId")
public class WalletAccountRecordService extends BaseService<RfWalletAccountRecord, RfWalletAccountRecordExample, RfWalletAccountRecordMapper> {

}
