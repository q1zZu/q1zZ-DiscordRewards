package me.q1zz.discordrewards.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import me.q1zz.discordrewards.data.configuration.PluginConfiguration;
import me.q1zz.discordrewards.data.configuration.sections.DatabaseSection;
import me.q1zz.discordrewards.user.User;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class Database {

    private final PluginConfiguration pluginConfiguration;
    private final Logger logger;

    private HikariDataSource dataSource;
    private Connection connection;

    public void saveData(User user) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO `q1zz_receivedrewards` (`UniqueID`, `DiscordAccountID`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `DiscordAccountID` = VALUES(DiscordAccountID);")) {
            preparedStatement.setString(1, user.getUniqueID().toString());
            preparedStatement.setLong(2, user.getDiscordAccountID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("Error while saving data for player with uuid: " + user.getUniqueID());
            e.printStackTrace();
        }
    }

    public User loadData(UUID uniqueId, long discordAccountID) {
        try (Statement statement = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet results = statement.executeQuery("SELECT * FROM `q1zz_receivedrewards` WHERE `UniqueID` = '" + uniqueId + "' OR `DiscordAccountID` = '" + discordAccountID + "';");
            if (results.first()) {
                return new User(UUID.fromString(results.getString("UniqueID")), results.getLong("DiscordAccountID"));
            }
        } catch (SQLException e) {
            this.logger.info("Error while loading data for player with uuid: " + uniqueId);
            e.printStackTrace();
        }
        return null;
    }

    public void connect() {
        this.dataSource = new HikariDataSource(this.getHikariConfig(this.pluginConfiguration.getDatabase()));
        this.logger.info("Successfully connected to MySQL database!");
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `q1zz_receivedrewards` (`UniqueID` VARCHAR(36) PRIMARY KEY, `DiscordAccountID` LONG);");
        } catch (SQLException e) {
            this.logger.severe("Error while creating table in database...");
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    public void closeConnection() {
        if(this.dataSource != null && !this.dataSource.isClosed()) {
            this.dataSource.close();
        }
    }

    private HikariConfig getHikariConfig(DatabaseSection dbs) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s",
                dbs.getHost(),
                dbs.getPort(),
                dbs.getDatabase()));
        hikariConfig.setUsername(dbs.getUsername());
        hikariConfig.setPassword(dbs.getPassword());

        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(dbs.isUseSSL()));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");

        return hikariConfig;
    }

}
