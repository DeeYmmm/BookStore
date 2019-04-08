package com.dao.impl;

import com.dao.TradeDao;
import com.domain.Trade;

import java.util.HashSet;
import java.util.Set;

public class TradeDaoImpl extends BaseDao<Trade> implements TradeDao {
    @Override
    public int insert(Trade trade) {
        String sql="insert into trade(userid,tradetime) values(?,?)";
        return (int) insert(sql,trade.getUserId(),trade.getTradeTime());

    }

    @Override
    public Set<Trade> getTradeWithUserId(int userId) {
        String sql="select tradeID,userId,tradeTime from trade where userId=?";
        Set<Trade> set=new HashSet<>(getForList(sql,userId));
        return set;
    }
}
