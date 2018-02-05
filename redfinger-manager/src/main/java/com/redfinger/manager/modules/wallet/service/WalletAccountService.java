package com.redfinger.manager.modules.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWalletAccount;
import com.redfinger.manager.common.domain.RfWalletAccountExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWalletAccountMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="accountId")
public class WalletAccountService extends BaseService<RfWalletAccount, RfWalletAccountExample, RfWalletAccountMapper> {

}
