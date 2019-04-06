package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    long insert(Connection connection,String sql,Object...args);
    /**
     * insert,update,delete
     * @param connection
     * @param sql
     * @param args
     */
    void update(Connection connection,String sql,Object...args) throws SQLException;

    /**
     * 返回一个T的对象
     * @param connection
     * @param sql
     * @param args
     * @return
     */
    T get(Connection connection,String sql,Object...args) throws SQLException;

    /**
     * 返回一个T的集合
     * @param connection
     * @param sql
     * @param args
     * @return
     */
    List<T> getForList(Connection connection,String sql,Object...args) throws SQLException;

    /**
     * 返回具体的一个值，例如总人数，平均工资等
     * @param connection
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    <E> E getForValue(Connection connection,String sql,Object...args) throws SQLException;

    /**
     * 批量处理的方法
     * @param connection
     * @param sql
     * @param args
     */
    void batch(Connection connection,String sql,Object[]...args) throws SQLException;
}
