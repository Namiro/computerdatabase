/**
 *
 */
package com.computerdatabase.test.model.dao;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.computerdatabase.model.dao.DatabaseConnection;

/**
 * @author Junior Burléon
 *
 */
public class ComputerDaoTest {

	private static Connection databaseConnection;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ComputerDaoTest.databaseConnection = DatabaseConnection.getInstance(true);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreate() {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGet(final int id) {
		Assert.fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		Assert.fail("Not yet implemented");
	}

}
