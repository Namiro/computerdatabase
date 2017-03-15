/**
 *
 */
package com.computerdatabase.test.model.dao;

import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.computerdatabase.model.dao.CompanyDao;
import com.computerdatabase.model.dao.DatabaseConnection;

/**
 * @author Junior Burléon
 *
 */
public class CompanyDaoTest {

	private static CompanyDao companyDao;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CompanyDaoTest.companyDao = new CompanyDao();

		final String[] queries = { "SET FOREIGN_KEY_CHECKS = 0", "TRUNCATE company", "SET FOREIGN_KEY_CHECKS = 1",
				"INSERT INTO company (id,name) VALUES (  1,'Apple Inc.')",
				"INSERT INTO company (id,name) VALUES (  2,'Thinking Machines')",
				"INSERT INTO company (id,name) VALUES (  3,'RCA')",
				"INSERT INTO company (id,name) VALUES (  4,'Netronics')",
				"INSERT INTO company (id,name) VALUES (  5,'Tandy Corporation')" };

		final Statement statement = DatabaseConnection.INSTANCE.getConnection().createStatement();
		for (final String query : queries)
			statement.execute(query);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
	}

	@Test
	public void testFind() {
		Assert.assertEquals(5, CompanyDaoTest.companyDao.find().size());
	}
}
