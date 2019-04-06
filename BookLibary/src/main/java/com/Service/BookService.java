package com.Service;

import com.dao.impl.BookDaoImpl;
import com.domain.Book;
import com.xu.CriteriaBook;
import com.xu.Page;
import com.xu.ShoppingCart;

public class BookService {
    private BookDaoImpl bookDao=new BookDaoImpl();

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
}
