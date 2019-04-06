package com.dao.impl;

import com.dao.BookDao;
import com.domain.Book;
import com.xu.CriteriaBook;
import com.xu.Page;
import org.junit.Test;

import java.util.List;

public class BookDdaoImplTest {

    BookDao bookDao=new BookDaoImpl();

    @Test
    public void getBook() {
        int id=4;
        Book book = bookDao.getBook(id);
        System.out.println(book);
    }

    @Test
    public void getPage() {
        CriteriaBook criteriaBook=new CriteriaBook
                (30,40,1);
        Page<Book> page = bookDao.getPage(criteriaBook);
        System.out.println(page);
    }

    @Test
    public void getTotalBookNumber() {
        CriteriaBook criteriaBook=new CriteriaBook
                (0,Integer.MAX_VALUE,2);
        long totalBookNumber = bookDao.getTotalBookNumber(criteriaBook);
        System.out.println(totalBookNumber);
    }

    @Test
    public void getPageList() {
        CriteriaBook criteriaBook=new CriteriaBook
                (0,Integer.MAX_VALUE,1);
        int pageSize=5;
        List<Book> pageList = bookDao.getPageList(criteriaBook, pageSize);
        System.out.println(pageList);
    }
}