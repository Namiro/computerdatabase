package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author Junior Burleon
 *
 *
 */
@Component
public class DatabaseConnection {

    private final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    private final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Autowired
    private HikariDataSource dataSource;

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