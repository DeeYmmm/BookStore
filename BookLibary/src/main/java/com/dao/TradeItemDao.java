package com.dao;

import com.domain.TradeItem;

import java.util.Collection;
import java.util.Set;

public interface TradeItemDao {

    void batchSave(Collection<TradeItem> items);

    Set<TradeItem> getTradeItemWithTradeId(int tradeId);
}
