package com.dao;

import com.domain.Account;

public interface AccountDao {
    /**
     * 根据accountId获取Accout对象
     * @param accountId
     * @return
     */
    Account get(int accountId);

    /**
     * 根据传入的accountId,amount更新指定账户的余额：扣除amount的钱数
     * @param accountId
     * @param amount
     */
    void updateBalance(int accountId,float amount);

}
