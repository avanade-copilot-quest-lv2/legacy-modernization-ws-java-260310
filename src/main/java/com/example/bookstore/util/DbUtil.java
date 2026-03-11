package com.example.bookstore.util;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.math.BigDecimal;
import java.util.concurrent.*;

public class DbUtil {

    private static final String DEFAULT_DB_HOST = "legacy-mysql";
    private static final String DEFAULT_DB_PORT = "3306";
    private static final String DEFAULT_DB_NAME = "legacy_db";
    private static final String DEFAULT_DB_USER = "legacy_user";
    private static final String DEFAULT_DB_PASS = "legacy_pass";

    private static final String DB_URL_PROD = "jdbc:mysql://db-server:3306/bookstore_prod";

    private static String envOrDefault(String key, String fallback) {
        String value = System.getenv(key);
        if (value == null || value.trim().length() == 0) {
            return fallback;
        }
        return value.trim();
    }

    public static String getHost() {
        return envOrDefault("DB_HOST", DEFAULT_DB_HOST);
    }

    public static String getPort() {
        return envOrDefault("DB_PORT", DEFAULT_DB_PORT);
    }

    public static String getDatabase() {
        return envOrDefault("DB_NAME", DEFAULT_DB_NAME);
    }

    public static String getUser() {
        return envOrDefault("DB_USER", DEFAULT_DB_USER);
    }

    public static String getPassword() {
        return envOrDefault("DB_PASSWORD", DEFAULT_DB_PASS);
    }

    public static String buildJdbcUrl() {
        return "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + getDatabase()
                + "?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
    }

    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(buildJdbcUrl(), getUser(), getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DbUtil: Failed to connect to database");
        }
        return conn;
    }

    
    public static Connection getConnection(String url, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    
    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {

            }
        }
    }

    
    public static void closeQuietly(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {

            }
        }
    }

    
    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        closeQuietly(rs);
        closeQuietly(stmt);
        closeQuietly(conn);
    }

    
    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            return conn != null;
        } catch (Exception e) {
            return false;
        } finally {
            closeQuietly(conn);
        }
    }
}
