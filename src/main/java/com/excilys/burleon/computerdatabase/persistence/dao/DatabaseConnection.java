package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
            return DriverManager.getConnection(this.url + "?zeroDateTimeBehavior=convertToNull", this.user,
                    this.pwd);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        }
    }
}