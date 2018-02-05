package com.redfinger.manager.modules.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWalletRecord;
import com.redfinger.manager.common.domain.RfWalletRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWalletRecordMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="walletRecordId")
public class WalletRecordService extends BaseService<RfWalletRecord, RfWalletRecordExample, RfWalletRecordMapper> {

}
