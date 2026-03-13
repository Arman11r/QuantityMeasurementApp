package com.app.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

    private static ConnectionPool instance;

    private List<Connection> availableConnections = new LinkedList<>();
    private List<Connection> usedConnections = new LinkedList<>();

    private int poolSize;
    private String url;
    private String username;
    private String password;

    private ConnectionPool() throws SQLException {

    	url = DatabaseConfig.get("db.url");
    	username = DatabaseConfig.get("db.username");
    	password = DatabaseConfig.get("db.password");

    	poolSize = Integer.parseInt(
    	        DatabaseConfig.get("db.pool.size")
    	);
        for(int i=0;i<poolSize;i++) {
            availableConnections.add(createConnection());
        }
    }

    public static synchronized ConnectionPool getInstance() throws SQLException {

        if(instance == null) {
            instance = new ConnectionPool();
        }

        return instance;
    }

    private Connection createConnection() throws SQLException {

        return DriverManager.getConnection(url,username,password);
    }

    public synchronized Connection getConnection() throws SQLException {

        if(availableConnections.isEmpty()) {
            throw new SQLException("No available connections");
        }

        Connection conn = availableConnections.remove(0);
        usedConnections.add(conn);

        return conn;
    }

    public synchronized void releaseConnection(Connection conn) {

        usedConnections.remove(conn);
        availableConnections.add(conn);
    }

    public void closeAll() throws SQLException {

        for(Connection conn : availableConnections) conn.close();
        for(Connection conn : usedConnections) conn.close();
    }

}