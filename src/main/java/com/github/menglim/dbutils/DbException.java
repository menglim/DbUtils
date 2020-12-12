package com.github.menglim.dbutils;

public class DbException extends RuntimeException {
    public DbException(String message) {
        throw new RuntimeException(message);
    }
}
