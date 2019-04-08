package com.Service;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;
import com.domain.Account;

public class AccountService {
    private AccountDao accountDao=new AccountDaoImpl();

    public Account getAccount(int accountId){
        return accountDao.get(accountId);
    }
}
