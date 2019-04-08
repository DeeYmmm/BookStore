package com.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    long insert(String sql,Object...args);
    /**
     * insert,update,delete
     * @param sql
     * @param args
     */
    void update(String sql,Object...args) throws SQLException;

    /**
     * 返回一个T的对象
     * @param sql
     * @param args
     * @return
     */
    T get(String sql,Object...args) throws SQLException;

    /**
     * 返回一个T的集合
     * @param sql
     * @param args
     * @return
     */
    List<T> getForList(String sql,Object...args) throws SQLException;

    /**
     * 返回具体的一个值，例如总人数，平均工资等
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    <E> E getForValue(String sql,Object...args) throws SQLException;

    /**
     * 批量处理的方法
     * @param sql
     * @param args
     */
    void batch(String sql,Object[]...args) throws SQLException;
}
