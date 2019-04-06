package com.dao.impl;

import com.dao.DAO;
import com.testDao.JDBCUtils;
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
     * @param connection
     * @param sql
     * @param args
     * @return
     */
    public long insert(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long id=0;
        try {
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

    public void update(Connection connection, String sql, Object... args) throws SQLException {
        queryRunner.update(connection,sql,args);
    }

    public T get(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection,sql,new BeanHandler<>(type),args);
    }

    public List<T> getForList(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection,sql,new BeanListHandler<>(type),args);
    }

    public <E> E getForValue(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection,sql,new ScalarHandler<>(),args);
    }

    public void batch(Connection connection, String sql, Object[]... args) throws SQLException {
        queryRunner.batch(connection,sql,args);
    }
}
