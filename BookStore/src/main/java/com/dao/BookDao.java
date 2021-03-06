package com.dao;

import com.domain.Book;
import com.xu.CriteriaBook;
import com.xu.Page;
import com.xu.ShoppingCartItem;

import java.util.Collection;
import java.util.List;

public interface BookDao {

    Book getBook(int id);

    Page<Book> getPage(CriteriaBook criteriaBook);

    long getTotalBookNumber(CriteriaBook criteriaBook);

    List<Book> getPageList(CriteriaBook criteriaBook,int pageSize);

    void batchUpdateStoreNumberAndSalesNumber(Collection<ShoppingCartItem> items);
}
