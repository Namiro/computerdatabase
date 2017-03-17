package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.service.tool.PropertiesManager;

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

    /**
     * Default constructor.
     */
    DatabaseConnection() {

    }

    /**
     * Allow to close the connection to the database.
     *
     * @param connection
     *            the connection to a database
     */
    public void closeConnection(final Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (final SQLException e) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                throw new PersistenceException(e);
            }
        }
    }

    /**
     * Close a resultset.
     *
     * @param resultset
     *            The resulstet to close
     */
    public void closeResultSet(final ResultSet resultset) {
        if (resultset != null) {
            try {
                resultset.close();
            } catch (final SQLException e) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                throw new PersistenceException(e);
            }
        }
    }

    /**
     * Close a statement.
     *
     * @param statement
     *            The statement to close
     */
    public void closeStatement(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (final SQLException e) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
                throw new PersistenceException(e);
            }
        }
    }

    /**
     * Get a connection with the database.
     *
     * @return A connection
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PropertiesManager.load();
            this.url = PropertiesManager.config.getString("database");
            this.user = PropertiesManager.config.getString("dbuser");
            this.pwd = PropertiesManager.config.getString("dbpassword");
            return DriverManager.getConnection(this.url, this.user, this.pwd);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        }
    }
}