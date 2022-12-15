package com.github.menglim.dbutils;

import com.github.menglim.dbutils.annotations.DbDataSource;
import com.github.menglim.dbutils.annotations.DbDateFormat;
import com.github.menglim.dbutils.annotations.DbField;
import com.github.menglim.mutils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.bouncycastle.util.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class DbFactoryHelper<T> {

    String getTableName(T newInstance) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        String tableName = newInstance.getClass().getSimpleName();
        Annotation annotation = newInstance.getClass().getAnnotation(DbDataSource.class);
        if (annotation != null) {
            DbDataSource odbcTableName = (DbDataSource) annotation;
            tableName = odbcTableName.schema();
        }
        return tableName;
    }

    int getConnectionIndex(T newInstance) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        Annotation annotation = newInstance.getClass().getAnnotation(DbDataSource.class);
        if (annotation != null) {
            DbDataSource odbcTableName = (DbDataSource) annotation;
            return odbcTableName.connectionIndex();
        }
        return 0;
    }

    T setField(T newInstance, ResultSet rs) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        if (rs == null) {
            throw new DbException("ResultSet cannot be NULL");
        }
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String defaultFieldValue = null;
            Annotation fieldAnnotation = field.getAnnotation(DbField.class);
            DbField odbcField = null;
            if (fieldAnnotation != null) {
                odbcField = (DbField) fieldAnnotation;
                if (odbcField.ignore()) continue;
                fieldName = odbcField.value();
                defaultFieldValue = odbcField.defaultValueIfNull().equalsIgnoreCase("") ? null : odbcField.defaultValueIfNull();
            }
            Annotation dateFormatAnnotation = field.getAnnotation(DbDateFormat.class);
            try {
                if (field.getType().equals(Integer.class) || field.getType().equals(int.class) || field.getType().equals(short.class)) {
                    Integer value = rs.getInt(fieldName);
                    BeanUtils.setProperty(newInstance, field.getName(), value);
                } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    Double value = rs.getDouble(fieldName);
                    BeanUtils.setProperty(newInstance, field.getName(), value);
                } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    Long value = rs.getLong(fieldName);
                    BeanUtils.setProperty(newInstance, field.getName(), value);
                } else if (field.getType().equals(String.class)) {
                    String value = rs.getString(fieldName);
                    if (AppUtils.getInstance().nonNull(value)) {
                        if (dateFormatAnnotation != null) {
                            DbDateFormat dbDateFormat = (DbDateFormat) dateFormatAnnotation;
                            switch (dbDateFormat.fromFormatDate()) {
                                case YYYYMMDD:
                                case MMDDYYYY:
                                case DDMMYYYY:
                                    value = value.substring(0, 8 + (dbDateFormat.fromDateSeparator().length() + dbDateFormat.fromDateSeparator().length()));
                                    break;
                                default:
                                    value = AppUtils.getInstance().toDate(value, dbDateFormat.fromFormatDate(), dbDateFormat.toFormatDate());
                                    break;
                            }
                        }

                        BeanUtils.setProperty(newInstance, field.getName(), value);
                    } else {
                        BeanUtils.setProperty(newInstance, field.getName(), defaultFieldValue);
                    }
                } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    String value = rs.getString(fieldName);
                    if (Objects.isNull(value)) {
                        BeanUtils.setProperty(newInstance, field.getName(), false);
                        break;
                    }
                    switch (Strings.toUpperCase(value).trim()) {
                        case "Y":
                        case "YES":
                        case "TRUE":
                        case "SUCCESS":
                        case "ACTIVE":
                        case "1":
                            BeanUtils.setProperty(newInstance, field.getName(), true);
                            break;
                        default:
                            BeanUtils.setProperty(newInstance, field.getName(), false);
                            break;
                    }

                } else if (field.getType().equals(Date.class)) {
                    String formatter = DbConnectionManager.getDateFormat(getConnectionIndex(newInstance));
                    if (odbcField != null) {
                        if (AppUtils.getInstance().nonNull(odbcField.formatter())) {
                            formatter = odbcField.formatter();
                        }
                    }
                    Date value = AppUtils.getInstance().getDate(rs.getString(fieldName), formatter);
                    BeanUtils.setProperty(newInstance, field.getName(), value);
                } else if (field.getType().equals(BigDecimal.class)) {
                    BigDecimal value = rs.getBigDecimal(fieldName);
                    BeanUtils.setProperty(newInstance, field.getName(), value);
                } else if (field.getType().equals(byte[].class)) {
                    byte[] value = rs.getBytes(fieldName);
                    BeanUtils.setProperty(newInstance, field.getName(), value);
                } else {
                    log.error("Unknown datatype " + field.getType());
                }
            } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
                log.error("Field name => " + fieldName);
                e.printStackTrace();
            }
        }
        return newInstance;
    }

    String getInsertSql(T newInstance) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        String sql = "INSERT INTO " + getTableName(newInstance) + "(";
        String insertFields = "";
        String insertValues = "";
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Annotation fieldAnn = field.getAnnotation(DbField.class);
            if (fieldAnn != null) {
                DbField odbcField = (DbField) fieldAnn;
                if (odbcField.ignore()) continue;
                if (odbcField.insertField() && !odbcField.primaryKey()) {
                    fieldName = AppUtils.getInstance().isNull(odbcField.value()) ? field.getName() : odbcField.value();
                    insertFields = insertFields + fieldName + ",";
                    insertValues = insertValues + ":" + fieldName + ",";
                }
            }
        }
        insertFields = insertFields.substring(0, insertFields.length() - 1);
        insertValues = insertValues.substring(0, insertValues.length() - 1);
        sql = sql + insertFields + ") VALUES (" + insertValues + ")";
        log.info("INSERT STATEMENT => " + sql);
        return sql;
    }

    String getUpdateSql(T newInstance) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        String sql = "UPDATE " + getTableName(newInstance) + " SET ";
        String updateField = "";
        String where = " WHERE ";
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Annotation fieldAnn = field.getAnnotation(DbField.class);
            if (fieldAnn != null) {
                DbField odbcField = (DbField) fieldAnn;
                //if (odbcField.ignore()) continue;
                if (odbcField.updateField() && !odbcField.primaryKey()) {
                    fieldName = AppUtils.getInstance().isNull(odbcField.value()) ? field.getName() : odbcField.value();
                    updateField = updateField + fieldName + "=:" + fieldName + ",";
                }
                if (odbcField.primaryKey()) {
                    fieldName = AppUtils.getInstance().isNull(odbcField.value()) ? field.getName() : odbcField.value();
                    where = where + fieldName + "=:" + fieldName;
                }
            }
        }
        updateField = updateField.substring(0, updateField.length() - 1);
        sql = sql + updateField + where;
        log.info("UPDATE STATEMENT => " + sql);
        return sql;
    }

    Field getPrimaryKeyField(T newInstance) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation fieldAnn = field.getAnnotation(DbField.class);
            if (fieldAnn != null) {
                DbField odbcField = (DbField) fieldAnn;
                if (odbcField.ignore()) continue;
                if (odbcField.primaryKey()) {
                    return field;
                }
            }
        }
        return null;
    }

    private String getSelectSql(T newInstance) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        return null;
    }

    NamedParameterStatement setParameters(NamedParameterStatement preparedStmt, T newInstance) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, SQLException {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        if (preparedStmt == null) {
            throw new DbException("PreparedStmt cannot be NULL");
        }
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Annotation fieldAnn = field.getAnnotation(DbField.class);
            if (fieldAnn != null) {
                DbField odbcField = (DbField) fieldAnn;
                //if (odbcField.ignore()) continue;
                if (odbcField.insertField() || odbcField.updateField()) {
                    fieldName = AppUtils.getInstance().isNull(odbcField.value()) ? field.getName() : odbcField.value();
                    if (field.getType().equals(Integer.class) || field.getType().equals(int.class) || field.getType().equals(short.class)) {
                        if (AppUtils.getInstance().nonNull(BeanUtils.getProperty(newInstance, field.getName())))
                            preparedStmt.setInt("" + fieldName, Integer.parseInt(BeanUtils.getProperty(newInstance, field.getName())));
                    } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                        if (AppUtils.getInstance().nonNull(BeanUtils.getProperty(newInstance, field.getName())))
                            preparedStmt.setDouble("" + fieldName, Double.valueOf(BeanUtils.getProperty(newInstance, field.getName())));
                    } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                        if (AppUtils.getInstance().nonNull(BeanUtils.getProperty(newInstance, field.getName())))
                            preparedStmt.setLong("" + fieldName, Long.parseLong(BeanUtils.getProperty(newInstance, field.getName())));
                    } else if (field.getType().equals(String.class)) {
                        if (AppUtils.getInstance().nonNull(BeanUtils.getProperty(newInstance, field.getName())))
                            preparedStmt.setString("" + fieldName, BeanUtils.getProperty(newInstance, field.getName()));
                        else
                            preparedStmt.setString("" + fieldName, null);
                    } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                        if (AppUtils.getInstance().nonNull(BeanUtils.getProperty(newInstance, field.getName())))
                            switch (Strings.toUpperCase(BeanUtils.getProperty(newInstance, field.getName())).trim()) {
                                case "Y":
                                case "YES":
                                case "TRUE":
                                    preparedStmt.setBoolean("" + fieldName, true);
                                    break;
                                default:
                                    preparedStmt.setBoolean("" + fieldName, false);
                                    break;
                            }
                    } else if (field.getType().equals(Date.class)) {
                        if (AppUtils.getInstance().nonNull(BeanUtils.getProperty(newInstance, field.getName())))
                            preparedStmt.setTimestamp("" + fieldName, Timestamp.valueOf(BeanUtils.getProperty(newInstance, field.getName())));
                    } else {
                        log.error("Unknown datatype " + field.getType());
                    }
                }
            }
        }
        return preparedStmt;
    }

    String getFieldName(T newInstance, String objectFieldName) {
        if (newInstance == null) {
            throw new DbException("NewInstance cannot be NULL");
        }
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation fieldAnn = field.getAnnotation(DbField.class);
            if (fieldAnn != null) {
                if (field.getName().equalsIgnoreCase(objectFieldName)) {
                    DbField odbcField = (DbField) fieldAnn;
                    if (odbcField.ignore()) continue;
                    return AppUtils.getInstance().isNull(odbcField.value()) ? field.getName() : odbcField.value();
                }
            }
        }
        return null;
    }
}
