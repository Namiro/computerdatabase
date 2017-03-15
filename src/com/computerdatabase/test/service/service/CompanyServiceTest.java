/**
 *
 */
package com.computerdatabase.test.service.service;

import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.computerdatabase.persistence.dao.DatabaseConnection;
import com.computerdatabase.persistence.model.Company;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.ICompanyService;
import com.computerdatabase.service.service.CompanyService;

/**
 * @author Junior Burléon
 *
 */
public class CompanyServiceTest {

	private static ICompanyService companyService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CompanyServiceTest.companyService = new CompanyService();
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

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testCheckDataEntityNameSoLong() throws ServiceException {
		final Company company = new Company(
				"rhgughrughguhdfguhfdiguhdfighfughdfguifghfgfhdgidfhgufidhgdfughfghfidghfduighfgudfhgdfuhgufhgdfuioghfioghuifdpghfdlgifdhguiodfghdfogfgdfgfi");
		this.exception.expect(ServiceException.class);
		CompanyServiceTest.companyService.checkDataEntity(company);
	}

	@Test
	public void testCheckDataEntityNoName() throws ServiceException {
		final Company company = new Company("");
		this.exception.expect(ServiceException.class);
		CompanyServiceTest.companyService.checkDataEntity(company);
	}

	@Test
	public void testGet() {
		Assert.assertTrue(CompanyServiceTest.companyService.get().size() == 5);
	}

	@Test
	public void testGetWithBadId() {
		final int id = 50;
		Assert.assertNull(CompanyServiceTest.companyService.get(id));
	}

	@Test
	public void testGetWithId() {
		final int id = 2;
		Assert.assertTrue(CompanyServiceTest.companyService.get(id).getId() == 2);
		Assert.assertTrue(CompanyServiceTest.companyService.get(id).getName().equals("Thinking Machines"));
	}

	@Test
	public void testSave() throws ServiceException {
		Company company = new Company("TestServiceCompany");
		Assert.assertTrue(CompanyServiceTest.companyService.save(company) != null);
		Assert.assertTrue(
				(company = CompanyServiceTest.companyService.save(company)).getName().equals("TestServiceCompany"));
		Assert.assertTrue(
				CompanyServiceTest.companyService.get(company.getId()).getName().equals("TestServiceCompany"));
		company.setName("TestServiceCompanyUpdated");
		Assert.assertTrue(CompanyServiceTest.companyService.save(company) != null);
		Assert.assertTrue(
				CompanyServiceTest.companyService.save(company).getName().equals("TestServiceCompanyUpdated"));
		Assert.assertTrue(
				CompanyServiceTest.companyService.get(company.getId()).getName().equals("TestServiceCompanyUpdated"));
	}

}
