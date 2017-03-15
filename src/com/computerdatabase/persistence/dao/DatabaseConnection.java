package com.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.persistence.exception.PersistenceException;
import com.computerdatabase.service.tool.PropertiesManager;

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

	private Connection connection;
	private String url = "";
	private String user = "";
	private String pwd = "";

	private DatabaseConnection() {

	}

	public void closeConnection(final Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (final SQLException e) {
				Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
				throw new PersistenceException(e);
			}
	}

	public void closeResultSet(final ResultSet resultset) {
		if (resultset != null)
			try {
				resultset.close();
			} catch (final SQLException e) {
				Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
				throw new PersistenceException(e);
			}
	}

	public void closeStatement(final Statement statement) {
		if (statement != null)
			try {
				statement.close();
			} catch (final SQLException e) {
				Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
				throw new PersistenceException(e);
			}
	}

	public Connection getConnection() {
		try {
			if (this.connection == null || this.connection.isClosed())
				Class.forName("com.mysql.jdbc.Driver");
			PropertiesManager.load();
			this.url = PropertiesManager.prop.getProperty("database");
			this.user = PropertiesManager.prop.getProperty("dbuser");
			this.pwd = PropertiesManager.prop.getProperty("dbpassword");
			this.connection = DriverManager.getConnection(this.url, this.user, this.pwd);
		} catch (SQLException | ClassNotFoundException e) {
			Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
			throw new PersistenceException(e);
		}
		return this.connection;
	}
}