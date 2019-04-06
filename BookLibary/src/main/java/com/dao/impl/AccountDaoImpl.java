package com.dao.impl;

import com.dao.AccountDao;
import com.domain.Account;
import com.testDao.JDBCUtils;

import java.sql.Connection;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {
    @Override
    public Account get(int accountId) {
        Connection connection=null;
        Account account=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select accountId,balance from account where accountId=?";
            account=get(connection,sql,accountId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
        return account;
    }

    @Override
    public void updateBalance(int accountId, float amount) {
        Connection connection=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="update account set balance=balance-? where accountId=?";
            update(connection,sql,amount,accountId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
    }
}
