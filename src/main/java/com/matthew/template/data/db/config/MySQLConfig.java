package com.matthew.template.data.db.config;

public class MySQLConfig {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQLConfig() {
        this.host = "";
        this.port = 0;
        this.database = "";
        this.password = "";
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
