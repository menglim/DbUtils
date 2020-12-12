package com.github.menglim.dbutils.interfaces;

import com.github.menglim.dbutils.Condition;
import com.github.menglim.dbutils.OrderBy;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

public interface Factory<T> {

    T selectOne(String sql, T newInstance, Function<ResultSet, T> function);

    T selectOne(String sql, T newInstance);

    T selectOne(T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder);

    T selectOne(T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder);

    T selectOne(T newInstance, Function<ResultSet, T> function);

    T selectOne(T newInstance, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder);

    T selectOne(T newInstance, Condition.Builder conditionBuilder);

    <Key> T selectOne(T newInstance, Key idValue);

    <Key> T selectOne(T newInstance, Function<ResultSet, T> function, Key idValue);

    List<T> selectMany(String sql, T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder);

    List<T> selectMany(String sql, T newInstance, Condition.Builder conditionBuilder) throws Exception;

    List<T> selectMany(T newInstance, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder);

    List<T> selectMany(T newInstance, Function<ResultSet, T> function, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder);

    List<T> selectMany(T newInstance, Function<ResultSet, T> function);

    List<T> selectMany(String sql, T newInstance, Condition.Builder conditionBuilder, OrderBy.Builder orderBuilder);

    T save(T t);
}
