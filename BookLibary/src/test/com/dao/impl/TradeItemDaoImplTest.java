package com.dao.impl;

import com.dao.TradeItemDao;
import com.domain.TradeItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class TradeItemDaoImplTest {

    private TradeItemDao tradeItemDao=new TradeItemDaoImpl();

    @Test
    public void batchSave() {
        Collection<TradeItem> collection=new ArrayList<>();

        collection.add(new TradeItem(1,1,3));
        collection.add(new TradeItem(2,2,3));
        collection.add(new TradeItem(3,3,3));
        collection.add(new TradeItem(4,4,3));

        tradeItemDao.batchSave(collection);
    }

    @Test
    public void getTradeItemWithTradeId() {
        Set<TradeItem> tradeItemWithTradeId = tradeItemDao.getTradeItemWithTradeId(3);
        System.out.println(tradeItemWithTradeId);
    }
}