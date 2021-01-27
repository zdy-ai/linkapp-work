package com.bytesvc.service.confirm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytesvc.ServiceException;
import com.bytesvc.service.IAccountService;

@Service("accountServiceConfirm")
public class AccountServiceConfirm implements IAccountService {

    @javax.annotation.Resource(name = "jdbcTemplate1")
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = ServiceException.class)
    public void increaseAmount(String acctId, double amount) throws ServiceException {
        this.jdbcTemplate.update("update tb_account_one set amount = amount + ?, frozen = frozen - ? where acct_id = ?", amount,
                amount, acctId);
        System.out.printf("done increase: acct= %s, amount= %7.2f%n", acctId, amount);
    }

    @Transactional(rollbackFor = ServiceException.class)
    public void decreaseAmount(String acctId, double amount) throws ServiceException {
        this.jdbcTemplate.update("update tb_account_one set frozen = frozen - ? where acct_id = ?", amount, acctId);
        System.out.printf("done decrease: acct= %s, amount= %7.2f%n", acctId, amount);
    }

}
