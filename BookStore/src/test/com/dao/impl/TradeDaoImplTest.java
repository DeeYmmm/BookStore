package com.dao.impl;

import com.dao.TradeDao;
import com.domain.Trade;
import org.junit.Test;

import java.sql.Date;
import java.util.Set;

public class TradeDaoImplTest {

    private TradeDao tradeDao=new TradeDaoImpl();

    @Test
    public void insert() {
        Trade trade=new Trade();
        trade.setUserId(1);
        trade.setTradeTime(new Date(new java.util.Date().getTime()));
        int insert = tradeDao.insert(trade);
        System.out.println(insert);
    }

    @Test
    public void getTradeWithUserId() {
        Set<Trade> tradeWithUserId = tradeDao.getTradeWithUserId(2);
        System.out.println(tradeWithUserId);
    }
}