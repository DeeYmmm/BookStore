package com.dao.impl;

import com.dao.AccountDao;
import com.domain.Account;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {
    @Override
    public Account get(int accountId) {
        String sql="select accountId,balance from account where accountId=?";
        return get(sql,accountId);
    }

    @Override
    public void updateBalance(int accountId, float amount) {
        String sql="update account set balance=balance-? where accountId=?";
        update(sql,amount,accountId);
    }
}
