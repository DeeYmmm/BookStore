package com.Service;

import com.dao.*;
import com.dao.impl.*;
import com.domain.Book;
import com.domain.Trade;
import com.domain.TradeItem;
import com.xu.CriteriaBook;
import com.xu.Page;
import com.xu.ShoppingCart;
import com.xu.ShoppingCartItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class BookService {
    private BookDao bookDao=new BookDaoImpl();
    private AccountDao accountDao=new AccountDaoImpl();
    private TradeDao tradeDao=new TradeDaoImpl();
    private TradeItemDao tradeItemDao=new TradeItemDaoImpl();
    private UserDao userDao=new UserDaoImpl();

    public Page<Book> getPage(CriteriaBook criteriaBook){
        return bookDao.getPage(criteriaBook);
    }

    public Book getBook(int id) {
        return bookDao.getBook(id);
    }

    public boolean addToCart(int id, ShoppingCart shoppingCart) {
        Book book=bookDao.getBook(id);

        if (book!=null){
            shoppingCart.addBook(book);
            return true;
        }

        return false;
    }

    public void removeItemFromShoppingCart(ShoppingCart shoppingCart, int id) {
        shoppingCart.remove(id);
    }

    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.clear();
    }

    public void updateItemQuantity(ShoppingCart shoppingCart, int id, int quantity) {
        shoppingCart.upadteItemQuantity(id,quantity);
    }

    //业务方法．
    public void cash(ShoppingCart shoppingCart, String username, String accountId) {
        //1.更新mybooks数据表相关记录的salesnumber和storenumber
        bookDao.batchUpdateStoreNumberAndSalesNumber(shoppingCart.getItems());
        int i=10/0;
        //2.更新account数据表中的balance
        accountDao.updateBalance(Integer.parseInt(accountId),shoppingCart.getTotalMoney());
        //3.向trade数据表中插入一条购买记录
        Trade trade=new Trade();
        trade.setUserId(userDao.getUser(username).getUserId());
        trade.setTradeTime(new Date(new java.util.Date().getTime()));
        int tradeId = tradeDao.insert(trade);
        //System.out.println(tradeId);
        //4.向tradeitem数据表中插入n条记录项
        Collection<TradeItem> collection=new ArrayList<>();
        for (ShoppingCartItem item:shoppingCart.getItems()){
            TradeItem tradeItem=new TradeItem();
            tradeItem.setBookId(item.getBook().getId());
            tradeItem.setQuantity(item.getQuantity());
            tradeItem.setTradeId(tradeId);
            collection.add(tradeItem);
        }
        tradeItemDao.batchSave(collection);
        //5.清空购物车
        shoppingCart.clear();
    }
}
