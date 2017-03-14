package com.computerdatabase.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.service.tool.PropertiesManager;

/**
 *
 * @author Junior Burleon
 *
 *
 */
public abstract class DatabaseConnection {

	private static String url = "";
	private static String user = "";
	private static String pwd = "";
	private static Connection connect;

	public static void close() {
		if (DatabaseConnection.connect != null)
			try {
				DatabaseConnection.connect.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
	}

	public static Connection getInstance() {
		if (DatabaseConnection.connect == null)
			try {
				Class.forName("com.mysql.jdbc.Driver");
				PropertiesManager.load();
				DatabaseConnection.url = PropertiesManager.prop.getProperty("database");
				DatabaseConnection.user = PropertiesManager.prop.getProperty("dbuser");
				DatabaseConnection.pwd = PropertiesManager.prop.getProperty("dbpassword");
				DatabaseConnection.connect = DriverManager.getConnection(DatabaseConnection.url,
						DatabaseConnection.user, DatabaseConnection.pwd);
			}

			catch (SQLException | ClassNotFoundException ex) {
				Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
			}
		return DatabaseConnection.connect;
	}
}