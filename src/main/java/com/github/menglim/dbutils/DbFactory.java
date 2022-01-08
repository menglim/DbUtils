package com.github.menglim.dbutils;

import com.github.menglim.dbutils.interfaces.BaseObjectModel;
import com.github.menglim.dbutils.interfaces.Factory;
import com.github.menglim.mutils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class DbFactory<T> implements Factory<T> {

    private BaseObjectModel<T> baseObjectModel;
    private DbFactoryHelper<T> _factoryHelper = new DbFactoryHelper<>();


    @Override
    public List<T> selectMany(T newInstance, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder) {
        List<T> list = new ArrayList<>();
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            String tableName = _factoryHelper.getTableName(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {

                String sql = "SELECT * FROM " + tableName;
                if (conditionBuilder != null) {
                    sql = sql + conditionBuilder.build();
                }
                if (orderBuilder != null) {
                    sql = sql + orderBuilder.build();
                }
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                if (conditionBuilder != null) {
                    _factoryHelper.setParameters(statement, (T) conditionBuilder.getInstance());
                }
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = _factoryHelper.setField(newInstance, rs);
                    list.add(newInstance);
                    newInstance = baseObjectModel.newInstance();
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<T> selectMany(String sql, T newInstance, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder) {
        List<T> list = new ArrayList<>();
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {

                if (conditionBuilder != null) {
                    sql = sql + conditionBuilder.build();
                }
                if (orderBuilder != null) {
                    sql = sql + orderBuilder.build();
                }
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                if (conditionBuilder != null) {
                    _factoryHelper.setParameters(statement, (T) conditionBuilder.getInstance());
                }
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    list.add(_factoryHelper.setField(newInstance, rs));
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<T> selectMany(T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder) {
        List<T> list = new ArrayList<>();
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            String tableName = _factoryHelper.getTableName(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {

                String sql = "SELECT * FROM " + tableName;
                if (conditionBuilder != null) {
                    sql = sql + conditionBuilder.build();
                }
                if (orderBuilder != null) {
                    sql = sql + orderBuilder.build();
                }
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                if (conditionBuilder != null) {
                    _factoryHelper.setParameters(statement, (T) conditionBuilder.getInstance());
                }
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    list.add(function.apply(rs));
                }
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    @Override
    public T selectOne(String sql, T newInstance, Function<ResultSet, T> function) {
        if (function == null) {
            try {
                throw new Exception("Function cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = function.apply(rs);
                    break;
                }
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return newInstance;
    }

    @Override
    public T selectOne(String sql, T newInstance) {
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = _factoryHelper.setField(newInstance, rs);
                    break;
                }
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return newInstance;
    }

    @Override
    public T selectOne(T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder) {
        if (function == null) {
            try {
                throw new Exception("Function cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                String sql = "SELECT * FROM " + _factoryHelper.getTableName(newInstance);
                log.info("Executing Sql => " + sql);
                if (orderBuilder != null) {
                    sql = sql + orderBuilder.build();
                }
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = function.apply(rs);
                    break;
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return newInstance;
    }

    @Override
    public T selectOne(T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder) {
        return selectOne(newInstance, function, conditionBuilder, null);
    }

    @Override
    public T selectOne(T newInstance, Function<ResultSet, T> function) {
        return selectOne(newInstance, function, null, null);
    }

    @Override
    public T selectOne(T newInstance, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder) {
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                String sql = "SELECT * FROM " + _factoryHelper.getTableName(newInstance);
                if (orderBuilder != null) {
                    sql = sql + orderBuilder.build();
                }
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = _factoryHelper.setField(newInstance, rs);
                    break;
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return newInstance;
    }

    @Override
    public T selectOne(T newInstance, Condition.Builder conditionBuilder) {
        return selectOne(newInstance, conditionBuilder, null);
    }

    @Override
    public <Key> T selectOne(T newInstance, Key idValue) {
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Field primaryKeyField = _factoryHelper.getPrimaryKeyField(newInstance);
            if (primaryKeyField == null) {
                throw new DbException("PrimaryKey not found");
            }
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                String primaryKeyValue = BeanUtils.getProperty(newInstance, primaryKeyField.getName());

                String sql = "SELECT * FROM " + _factoryHelper.getTableName(newInstance) + " WHERE " + _factoryHelper.getFieldName(newInstance, primaryKeyField.getName()) + "=" + primaryKeyValue;
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = _factoryHelper.setField(newInstance, rs);
                    break;
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return newInstance;
    }

    @Override
    public <Key> T selectOne(T newInstance, Function<ResultSet, T> function, Key idValue) {
        if (function == null) {
            try {
                throw new Exception("Function cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Field primaryKeyField = _factoryHelper.getPrimaryKeyField(newInstance);
            if (primaryKeyField == null) {
                throw new DbException("PrimaryKey not found");
            }
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                String primaryKeyValue = BeanUtils.getProperty(newInstance, primaryKeyField.getName());

                String sql = "SELECT * FROM " + _factoryHelper.getTableName(newInstance) + " WHERE " + _factoryHelper.getFieldName(newInstance, primaryKeyField.getName()) + "=" + primaryKeyValue;
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    newInstance = function.apply(rs);
                    break;
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return newInstance;
    }


    @Override
    public List<T> selectMany(String sql, T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder) {
        List<T> list = new ArrayList<>();
        if (function == null) {
            try {
                throw new Exception("Function cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (newInstance == null) {
            try {
                throw new Exception("newInstance cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            try {
                log.info("Executing Sql => " + sql);
                NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
                if (conditionBuilder != null) {
                    _factoryHelper.setParameters(statement, (T) conditionBuilder.getInstance());
                }
                ResultSet rs = statement != null ? statement.executeQuery() : null;
                while (rs.next()) {
                    list.add(function.apply(rs));
                }
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                        log.info("Connection is closed");
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<T> selectMany(String sql, T newInstance, Condition.Builder conditionBuilder) throws Exception {
        List<T> list = new ArrayList<>();
        if (newInstance == null) {
            throw new Exception("newInstance cannot be null");
        } else {
            baseObjectModel = (BaseObjectModel<T>) newInstance;
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Connection connection = DbConnectionManager.getConnection(connectionIndex);
            log.info("Executing Sql => " + sql);
            NamedParameterStatement statement = new NamedParameterStatement(connection, sql);
            if (conditionBuilder != null) {
                _factoryHelper.setParameters(statement, (T) conditionBuilder.getInstance());
            }
            ResultSet rs = statement != null ? statement.executeQuery() : null;
            while (rs.next()) {
                newInstance = _factoryHelper.setField(newInstance, rs);
                if (newInstance != null) list.add(newInstance);
                newInstance = baseObjectModel.newInstance();
            }
            rs.close();
            statement.close();
            connection.close();
        }
        return list;
    }


    @Override
    public T save(T newInstance) {
        Connection connection = null;
        NamedParameterStatement preparedStmt = null;
        try {
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Field primaryKeyField = _factoryHelper.getPrimaryKeyField(newInstance);

            if (primaryKeyField == null) {
                throw new SQLException("PrimaryKey not found");
            }
            String primaryKeyValue = BeanUtils.getProperty(newInstance, primaryKeyField.getName());
            String sql = "";
            if (AppUtils.getInstance().isNull(primaryKeyValue)) {
                sql = _factoryHelper.getInsertSql(newInstance);
                log.info("Executing Sql => " + sql);
                connection = DbConnectionManager.getConnection(connectionIndex);
                preparedStmt = new NamedParameterStatement(Objects.requireNonNull(connection), sql, Statement.RETURN_GENERATED_KEYS);
                preparedStmt = _factoryHelper.setParameters(preparedStmt, newInstance);
                preparedStmt.executeUpdate();
                ResultSet rs = preparedStmt.getGenerateKey();
                Long lastInsertKey = 0L;
                if (rs.next()) {
                    lastInsertKey = rs.getLong(1);
                }
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                BeanUtils.setProperty(newInstance, primaryKeyField.getName(), lastInsertKey);
            } else {
                sql = _factoryHelper.getUpdateSql(newInstance);
                log.info("Executing Sql => " + sql);
                connection = DbConnectionManager.getConnection(connectionIndex);
                preparedStmt = new NamedParameterStatement(Objects.requireNonNull(connection), sql);
                preparedStmt = _factoryHelper.setParameters(preparedStmt, newInstance);
                preparedStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStmt.close();
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return newInstance;
    }

    public T update(T newInstance) {
        Connection connection = null;
        NamedParameterStatement preparedStmt = null;
        try {
            int connectionIndex = _factoryHelper.getConnectionIndex(newInstance);
            Field primaryKeyField = _factoryHelper.getPrimaryKeyField(newInstance);

            if (primaryKeyField == null) {
                throw new SQLException("PrimaryKey not found");
            }
            String primaryKeyValue = BeanUtils.getProperty(newInstance, primaryKeyField.getName());
            String sql = _factoryHelper.getUpdateSql(newInstance);
            log.info("Executing Sql => " + sql);
            connection = DbConnectionManager.getConnection(connectionIndex);
            preparedStmt = new NamedParameterStatement(Objects.requireNonNull(connection), sql);
            preparedStmt = _factoryHelper.setParameters(preparedStmt, newInstance);
            preparedStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStmt.close();
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return newInstance;
    }

    @Override
    public List<T> selectMany(T newInstance, Function<ResultSet, T> function) {
        return selectMany(newInstance, function, null, null);
    }
}
