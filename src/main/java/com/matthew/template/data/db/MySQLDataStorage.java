package com.matthew.template.data.db;

import com.matthew.template.data.db.config.MySQLConfig;
import com.matthew.template.data.db.framework.SQLDataStorage;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataStorage extends SQLDataStorage {

    private final String CREATE_PLAYERS_TABLE =
            "CREATE TABLE `player_data` (\n" +
            "  `technical_key` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `uuid` CHAR(36) NOT NULL UNIQUE,\n" +
            "  `data` LONGTEXT NULL,\n" +
            "  PRIMARY KEY (`technical_key`)\n" +
            ");\n";
    private final String INSERT_PLAYER_DATA =
            "INSERT INTO player_data(uuid, data)\n" +
            "VALUES(?, ?)\n" +
            "ON DUPLICATE KEY UPDATE\n" +
            "data = VALUES(data);";

    private final String SELECT_ALL_PLAYER_DATA = "SELECT * FROM player_data";

    private final String SELECT_PLAYER_DATA = "SELECT * FROM player_data WHERE uuid=?";

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
