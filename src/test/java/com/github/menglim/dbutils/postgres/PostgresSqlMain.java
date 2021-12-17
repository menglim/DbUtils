package com.github.menglim.dbutils.postgres;

import com.github.menglim.dbutils.DbConnectionManager;
import com.github.menglim.dbutils.DbType;

public class PostgresSqlMain {
    public static void main(String[] args) {
        DbConnectionManager.initConnection(
                0,
                DbType.PostgreSQL,
                "" + "jdbc:postgresql://172.16.250.104:5432/tps",
                "" + "enq",
                "" + "Pa$$w0rd"
        );
        DbConnectionManager.testIsConnected(0);
    }
}
