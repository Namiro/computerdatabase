package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

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
        } catch (final ClassNotFoundException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
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
            return this.dataSource.getConnection();
        } catch (final SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        }
    }
}