package com.bytesvc.service.impl;

import org.bytesoft.compensable.Compensable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bytesvc.ServiceException;
import com.bytesvc.service.IAccountService;

@com.alibaba.dubbo.config.annotation.Service(interfaceClass = IAccountService.class, group = "x-bytetcc", filter = "bytetcc", loadbalance = "bytetcc", cluster = "failfast", retries = -1)
@Compensable(interfaceClass = IAccountService.class, confirmableKey = "accountServiceConfirm", cancellableKey = "accountServiceCancel")
public class AccountServiceImpl implements IAccountService {

	@javax.annotation.Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Transactional(rollbackFor = ServiceException.class)
	public void increaseAmount(String acctId, double amount) throws ServiceException {
		this.jdbcTemplate.update("update tb_account_one set frozen = frozen + ? where acct_id = ?", amount, acctId);
		System.out.printf("exec increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void decreaseAmount(String acctId, double amount) throws ServiceException {
		this.jdbcTemplate.update("update tb_account_one set amount = amount - ?, frozen = frozen + ? where acct_id = ?", amount,
				amount, acctId);
		System.out.printf("exec decrease: acct= %s, amount= %7.2f%n", acctId, amount);
	}

}
