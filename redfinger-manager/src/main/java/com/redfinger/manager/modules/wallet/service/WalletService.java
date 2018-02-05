package com.redfinger.manager.modules.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWallet;
import com.redfinger.manager.common.domain.RfWalletExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWalletMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="walletId")
public class WalletService extends BaseService<RfWallet, RfWalletExample, RfWalletMapper> {

}
