package com.dao.impl;

import com.dao.DAO;
import com.testDao.JDBCUtils;
import com.xu.ConnectionContext;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.List;

public class BaseDao<T> implements DAO<T> {

    private QueryRunner queryRunner;
    private Class<T> type;

    public BaseDao(){
        queryRunner=new QueryRunner();
        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
        type= (Class<T>) t.getActualTypeArguments()[0];
    }

    /**
     * 返回插入的id
     * @param sql
     * @param args
     * @return
     */
    public long insert(String sql, Object... args) {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        long id=0;
        try {
            connection=ConnectionContext.getInstance().get();
            preparedStatement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            for (int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            preparedStatement.executeUpdate();

            //获取主键值
            resultSet=preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                id=resultSet.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(resultSet,preparedStatement,null);
        }
        return id;
    }

    public void update(String sql, Object... args){
        Connection connection=null;
        try {
            connection=ConnectionContext.getInstance().get();
            queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T get(String sql, Object... args){
        Connection connection=null;
        try {
            connection=ConnectionContext.getInstance().get();
            return queryRunner.query(connection,sql,new BeanHandler<>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<T> getForList(String sql, Object... args){
        Connection connection=null;
        try {
            connection=ConnectionContext.getInstance().get();
            return queryRunner.query(connection,sql,new BeanListHandler<>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <E> E getForValue(String sql, Object... args){
        Connection connection=null;
        try {
            connection=ConnectionContext.getInstance().get();
            return queryRunner.query(connection,sql,new ScalarHandler<>(),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void batch(String sql, Object[]... args){
        Connection connection=null;
        try {
            connection=ConnectionContext.getInstance().get();
            queryRunner.batch(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
