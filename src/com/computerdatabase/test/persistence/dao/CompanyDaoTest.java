/**
 *
 */
package com.computerdatabase.test.persistence.dao;

import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.computerdatabase.persistence.dao.CompanyDao;
import com.computerdatabase.persistence.dao.DatabaseConnection;
import com.computerdatabase.persistence.model.Company;

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
		CompanyDaoTest.companyDao = CompanyDao.INSTANCE;

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
		try {
			Assert.assertEquals(5, CompanyDaoTest.companyDao.find(Company.class).size());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
