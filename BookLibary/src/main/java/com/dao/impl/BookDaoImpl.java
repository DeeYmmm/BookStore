package com.dao.impl;

import com.dao.BookDao;
import com.domain.Book;
import com.testDao.JDBCUtils;
import com.xu.CriteriaBook;
import com.xu.Page;

import java.sql.Connection;
import java.util.List;

public class BookDaoImpl extends BaseDao<Book> implements BookDao {

    public Book getBook(int id) {
        Connection connection=null;
        Book book=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select id,author,title,price,storeNumber,salesNumber from mybooks where id=?";
            book=get(connection,sql,id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
        return book;
    }

    public Page<Book> getPage(CriteriaBook criteriaBook) {
        Page<Book> page=new Page<>(criteriaBook.getPageNo());

        page.setTotalItemNumber(getTotalBookNumber(criteriaBook));
        criteriaBook.setPageNo(page.getPageNo());
        page.setList(getPageList(criteriaBook,page.getPageSize()));
        return page;
    }

    public long getTotalBookNumber(CriteriaBook criteriaBook) {
        Connection connection=null;
        long count=0;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select count(id) from mybooks where price>=? and price<=?";
            count=getForValue(connection,sql,
                    criteriaBook.getMinPrice(),criteriaBook.getMaxPrice());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
        return count;
    }

    public List<Book> getPageList(CriteriaBook criteriaBook, int pageSize) {
        Connection connection=null;
        List<Book> list=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="select id,author,title,price,storeNumber,salesNumber" +
                    " from mybooks where price>=? and price<=? limit ?,?";
            list = getForList(connection, sql, criteriaBook.
                            getMinPrice(), criteriaBook.getMaxPrice(),
                    (criteriaBook.getPageNo() - 1) * pageSize, pageSize);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null,null,connection);
        }
        return list;
    }
}
