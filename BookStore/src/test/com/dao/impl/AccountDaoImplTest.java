package com.dao.impl;

import com.dao.AccountDao;
import com.domain.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDaoImplTest {

    private AccountDao accountDao=new AccountDaoImpl();

    @Test
    public void get() {
        Account account = accountDao.get(1);
        System.out.println(account);
    }

    @Test
    public void updateBalance() {
        accountDao.updateBalance(1,50);
        System.out.println(accountDao.get(1));
    }

}