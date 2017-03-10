package com.computerdatabase.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Junior Burleon
 *
 *
 */
public abstract class DatabaseConnection {

	private static String urlTest = "jdbc:mysql://localhost/computer-database-db-test";
	private static String urlProd = "jdbc:mysql://localhost/computer-database-db";

	private static String user = "root";
	private static String pwd = "apql1161713";
	private static Connection connect;

	public static Connection getInstance(final boolean isTestDatabase) {
		if (DatabaseConnection.connect == null)
			try {
				String url;
				if (isTestDatabase)
					url = DatabaseConnection.urlTest;
				else
					url = DatabaseConnection.urlProd;

				Class.forName("com.mysql.jdbc.Driver");
				DatabaseConnection.connect = DriverManager.getConnection(url, DatabaseConnection.user,
						DatabaseConnection.pwd);
			}

			catch (SQLException | ClassNotFoundException ex) {
				Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
			}
		return DatabaseConnection.connect;
	}
}