package com.testDao;

import com.dao.DAO;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JdbcDaoImpl<T> implements DAO<T> {

    private QueryRunner queryRunner;
    private Class<T> type;

    public JdbcDaoImpl(){
        queryRunner=new QueryRunner();
        ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
        type= (Class<T>) t.getActualTypeArguments()[0];
    }

    public long insert(Connection connection, String sql, Object... args) {
        return 0;
    }

    public void update(Connection connection, String sql, Object... args) throws SQLException {
        queryRunner.update(connection,sql,args);
    }

    public T get(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
    }

    public List<T> getForList(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
    }

    public void batch(Connection connection, String sql, Object[]... args) throws SQLException {
        queryRunner.batch(connection,sql,args);

    }

    public <E> E getForValue(Connection connection, String sql, Object...args) throws SQLException {
        return queryRunner.query(connection,sql,new ScalarHandler<E>(),args);
    }
}
