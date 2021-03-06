package com.github.menglim.dbutils;

import lombok.Data;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

@Data
public class DbConnectionManager {

    private static HashMap<Integer, String> connectionString; //jdbc:oracle:thin:@uat.fast.bicbank.net:1521:XE
    private static HashMap<Integer, String> username;
    private static HashMap<Integer, String> password;
    private static HashMap<Integer, DbType> dbSupport;
    private static HashMap<Integer, String> dateFormat;

    public static void initConnection(int connectionIndex, DbType dbSupport, String connectionString, String username, String password) {
        if (DbConnectionManager.connectionString == null) {
            DbConnectionManager.connectionString = new HashMap<>();
        }
        if (DbConnectionManager.username == null) {
            DbConnectionManager.username = new HashMap<>();
        }
        if (DbConnectionManager.password == null) {
            DbConnectionManager.password = new HashMap<>();
        }
        if (DbConnectionManager.dbSupport == null) {
            DbConnectionManager.dbSupport = new HashMap<>();
        }
        if (DbConnectionManager.dateFormat == null) {
            DbConnectionManager.dateFormat = new HashMap<>();
        }
        DbConnectionManager.connectionString.put(connectionIndex, connectionString);
        DbConnectionManager.username.put(connectionIndex, username);
        DbConnectionManager.password.put(connectionIndex, password);
        DbConnectionManager.dbSupport.put(connectionIndex, dbSupport);
        DbConnectionManager.dateFormat.put(connectionIndex, "yyyy-MM-dd hh:mm:ss");
    }

    public static void initConnection(int connectionIndex, DbType dbSupport, String connectionString, String username, String password, String dateFormat) {
        if (DbConnectionManager.connectionString == null) {
            DbConnectionManager.connectionString = new HashMap<>();
        }
        if (DbConnectionManager.username == null) {
            DbConnectionManager.username = new HashMap<>();
        }
        if (DbConnectionManager.password == null) {
            DbConnectionManager.password = new HashMap<>();
        }
        if (DbConnectionManager.dbSupport == null) {
            DbConnectionManager.dbSupport = new HashMap<>();
        }
        if (DbConnectionManager.dateFormat == null) {
            DbConnectionManager.dateFormat = new HashMap<>();
        }
        DbConnectionManager.connectionString.put(connectionIndex, connectionString);
        DbConnectionManager.username.put(connectionIndex, username);
        DbConnectionManager.password.put(connectionIndex, password);
        DbConnectionManager.dbSupport.put(connectionIndex, dbSupport);
        DbConnectionManager.dateFormat.put(connectionIndex, dateFormat);
    }

    static Connection getConnection(int connectionIndex) {
        try {
            DbType selectedDbSupport = dbSupport.get(connectionIndex);
            switch (selectedDbSupport) {
                case MySQL:
                    Class.forName("com.mysql.jdbc.Driver");
                    break;
                case Oracle:
                    Class.forName("oracle.jdbc.driver.OracleDriver");
//                    Class.forName("oracle.jdbc.OracleDriver");
                    break;
                default:
                    throw new NotImplementedException();
            }
            Connection connection = DriverManager.getConnection(connectionString.get(connectionIndex)
                    , username.get(connectionIndex)
                    , password.get(connectionIndex)
            );
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String getDateFormat(int connectionIndex) {
        return dateFormat.get(connectionIndex);
    }
}
