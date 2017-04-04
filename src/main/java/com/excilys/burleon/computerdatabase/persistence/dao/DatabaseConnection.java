package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.service.tool.PropertiesManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

/**
 *
 * @author Junior Burleon
 *
 *
 */
public enum DatabaseConnection {
    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    private String url = "";
    private String user = "";
    private String pwd = "";
    private int maxpoolsize = 20;

    private HikariDataSource dataSource;

    private final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

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
            try {
                this.maxpoolsize = Integer.valueOf(PropertiesManager.config.getString("maxpoolsize"));
            } catch (final NumberFormatException e) {
                this.maxpoolsize = 20;
                this.LOGGER.error("Impossible to get the number of max pool size. Default value used is 20", e);
            }

            final HikariConfig config = new HikariConfig();
            config.setJdbcUrl(this.url);
            config.setUsername(this.user);
            config.setPassword(this.pwd);
            config.setMaximumPoolSize(this.maxpoolsize);
            this.dataSource = new HikariDataSource(config);
            this.dataSource.setAutoCommit(false);

            // To close the datasource when the server is closing
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    DatabaseConnection.this.dataSource.close();
                }
            });
        } catch (final ClassNotFoundException | PoolInitializationException e) {
            this.LOGGER.error("Impossible to get a data source", e);
            throw new PersistenceException(e);
        }
    }

    /**
     * Get a connection with the database.
     *
     * @return A connection
     */
    public Connection getConnection() {
        try {
            if (this.threadLocal.get() == null || this.threadLocal.get().isClosed()) {
                this.threadLocal.set(this.dataSource.getConnection());
            }
        } catch (final SQLException e) {
            this.LOGGER.error("Impossible to get a connection", e);
            throw new PersistenceException(e);
        }
        return this.threadLocal.get();
    }
}