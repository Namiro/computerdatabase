/**
 *
 */
package com.excilys.burleon.computerdatabase.test.service.service;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.burleon.computerdatabase.persistence.dao.DatabaseConnection;

/**
 * @author Junior Burléon
 *
 */
public class DatabaseConnectionTest {

	@Test
	public void testConnection() {
		Assert.assertNotNull(DatabaseConnection.getInstance());
		DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
	}
}
