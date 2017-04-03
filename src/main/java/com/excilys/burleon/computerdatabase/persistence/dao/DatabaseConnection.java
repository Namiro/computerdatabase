package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.service.tool.PropertiesManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author Junior Burleon
 *
 *
 */
public enum DatabaseConnection {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    private String url = "";
    private String user = "";
    private String pwd = "";

    private HikariDataSource dataSource;

    /**
     * Default constructor.
     */
    DatabaseConnection() {
        try {
            PropertiesManager.load();

            Class.forName("com.mysql.jdbc.Driver");
            this.url = PropertiesManager.config.getString("database");
            this.user = PropertiesManager.config.getString("dbuser");
            this.pwd = PropertiesManager.config.getString("dbpassword");

            final HikariConfig config = new HikariConfig();
            config.setJdbcUrl(this.url);
            config.setUsername(this.user);
            config.setPassword(this.pwd);
            config.setMaximumPoolSize(20);
            this.dataSource = new HikariDataSource(config);
            this.dataSource.setAutoCommit(false);

            // To close the datasource when the server is closing
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    DatabaseConnection.this.dataSource.close();
                }
            });

        } catch (final ClassNotFoundException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Get a connection with the database.
     *
     * @return A connection
     */
    public Connection getConnection() {
        final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>() {

            @Override
            protected Connection initialValue() {
                try {
                    final Connection connection = DatabaseConnection.this.dataSource.getConnection();
                    connection.setAutoCommit(false);
                    return connection;
                } catch (final SQLException e) {
                    DatabaseConnection.LOGGER.error(e.getMessage());
                    throw new PersistenceException(e);
                }
            }

            @Override
            public void remove() {
                try {
                    this.get().close();
                } catch (final SQLException e) {
                    DatabaseConnection.LOGGER.error(e.getMessage());
                    throw new PersistenceException(e);
                }
                super.remove();
            }
        };
        return threadLocal.get();

    }
}