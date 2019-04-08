package com.dao;

import com.domain.Trade;

import java.util.Set;

public interface TradeDao {

    int insert(Trade trade);

    Set<Trade> getTradeWithUserId(int userId);
}
