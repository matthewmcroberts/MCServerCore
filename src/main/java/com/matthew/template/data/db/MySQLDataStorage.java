package com.matthew.template.data.db;

import com.matthew.template.data.db.config.MySQLConfig;
import com.matthew.template.data.db.framework.SQLDataStorage;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataStorage extends SQLDataStorage {

    private final String CREATE_PLAYERS_TABLE = "";
    private final String INSERT_PLAYER_DATA = "";
    private final String SELECT_ALL_PLAYER_DATA = "";
    private final String SELECT_PLAYER_DATA = "";

    private final MySQLConfig sqlConfig;
    private final String connectionUrl;

    public MySQLDataStorage(MySQLConfig config) {
        super();
        this.sqlConfig = config;
        this.connectionUrl = String.format("jdbc:mysql://%s:%d/%s", sqlConfig.getHost(), sqlConfig.getPort(), sqlConfig.getDatabase());
    }

    @Override
    protected void initDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    protected Connection openConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, this.sqlConfig.getUsername(), this.sqlConfig.getPassword());
    }

    @Override
    protected @NotNull String getCreatePlayersTableStatement() {
        return this.CREATE_PLAYERS_TABLE;
    }

    @Override
    protected @NotNull String getInsertPlayerDataStatement() {
        return this.INSERT_PLAYER_DATA;
    }

    @Override
    protected @NotNull String getSelectAllPlayerDataQuery() {
        return this.SELECT_ALL_PLAYER_DATA;
    }

    @Override
    protected @NotNull String getSelectPlayerDataQuery() {
        return SELECT_PLAYER_DATA;
    }
}
