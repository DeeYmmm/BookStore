package com.domain;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
    private int tradeId;
    private int userId;
    private Date tradeTime;
    private Set<TradeItem> items=new LinkedHashSet<>();

    public Set<TradeItem> getItems() {
        return items;
    }

    public void setItems(Set<TradeItem> items) {
        this.items = items;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", userId=" + userId +
                ", tradeTime=" + tradeTime +
                '}';
    }
}
