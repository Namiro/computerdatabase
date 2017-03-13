/**
 *
 */
package com.computerdatabase.test.model.dao;

import org.junit.Assert;
import org.junit.Test;

import com.computerdatabase.model.dao.DatabaseConnection;

/**
 * @author Junior Burléon
 *
 */
public class DatabaseConnectionTest {

	@Test
	public void testConnection() {
		Assert.assertNotNull(DatabaseConnection.getInstance());
		DatabaseConnection.close();
	}
}
