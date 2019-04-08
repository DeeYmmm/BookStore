package com.dao.impl;

import com.dao.BookDao;
import com.domain.Book;
import com.xu.CriteriaBook;
import com.xu.Page;
import com.xu.ShoppingCartItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookDaoImpl extends BaseDao<Book> implements BookDao {

    public Book getBook(int id) {
        String sql="select id,author,title,price,storeNumber,salesNumber from mybooks where id=?";
        return get(sql,id);
    }

    public Page<Book> getPage(CriteriaBook criteriaBook) {
        Page<Book> page=new Page<>(criteriaBook.getPageNo());

        page.setTotalItemNumber(getTotalBookNumber(criteriaBook));
        criteriaBook.setPageNo(page.getPageNo());
        page.setList(getPageList(criteriaBook,page.getPageSize()));
        return page;
    }

    public long getTotalBookNumber(CriteriaBook criteriaBook) {
        String sql="select count(id) from mybooks where price>=? and price<=?";
        return getForValue(sql, criteriaBook.getMinPrice(),criteriaBook.getMaxPrice());

    }

    public List<Book> getPageList(CriteriaBook criteriaBook, int pageSize) {
        String sql="select id,author,title,price,storeNumber,salesNumber" +
                    " from mybooks where price>=? and price<=? limit ?,?";
        return getForList(sql, criteriaBook.
                            getMinPrice(), criteriaBook.getMaxPrice(),
                    (criteriaBook.getPageNo() - 1) * pageSize, pageSize);
    }

    @Override
    public void batchUpdateStoreNumberAndSalesNumber(
            Collection<ShoppingCartItem> items) {
        String sql="update mybooks set storeNumber=storeNumber-?," +
                "salesNumber=salesNumber+? where id=?";
        Object[][] params=new Object[items.size()][3];
        List<ShoppingCartItem> shoppingCartItems=new ArrayList<>(items);
        for (int i=0;i<items.size();i++){
            params[i][0]=shoppingCartItems.get(i).getQuantity();
            params[i][1]=shoppingCartItems.get(i).getQuantity();
            params[i][2]=shoppingCartItems.get(i).getBook().getId();
        }
        batch(sql,params);

    }
}
