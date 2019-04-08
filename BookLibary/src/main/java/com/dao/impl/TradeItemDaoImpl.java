package com.dao.impl;

import com.dao.TradeItemDao;
import com.domain.TradeItem;

import java.util.*;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDao {
    @Override
    public void batchSave(Collection<TradeItem> items) {
        String sql="insert into tradeitem(bookid,quantity,tradeid)" +
                " values(?,?,?)";
        Object[][] params=new Object[items.size()][3];
        List<TradeItem> list=new ArrayList<>(items);
        for (int i=0;i<items.size();i++){
            params[i][0]=list.get(i).getBookId();
            params[i][1]=list.get(i).getQuantity();
            params[i][2]=list.get(i).getTradeId();
        }
        batch(sql,params);
    }

    @Override
    public Set<TradeItem> getTradeItemWithTradeId(int tradeId) {
        String sql="select tradeItemId,bookId,quantity,tradeId from tradeitem where tradeid=?";
        Set<TradeItem> set=new HashSet<>(getForList(sql,tradeId));

        return set;
    }
}
