/**
 *
 */
package com.computerdatabase.test.service.service;

import java.sql.Statement;
import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.computerdatabase.model.dao.DatabaseConnection;
import com.computerdatabase.model.entity.Computer;
import com.computerdatabase.model.entity.IEntity;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.IComputerService;
import com.computerdatabase.service.service.ComputerService;

/**
 * @author Junior Burléon
 *
 */
public class ComputerServiceTest {

	private static IComputerService computerService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ComputerServiceTest.computerService = new ComputerService();

		final String[] queries = { "SET FOREIGN_KEY_CHECKS = 0", "TRUNCATE company", "TRUNCATE computer",
				"SET FOREIGN_KEY_CHECKS = 1", "INSERT INTO company (id,name) VALUES (  1,'Apple Inc.')",
				"INSERT INTO company (id,name) VALUES (  2,'Thinking Machines')",
				"INSERT INTO company (id,name) VALUES (  3,'RCA')",
				"INSERT INTO company (id,name) VALUES (  4,'Netronics')",
				"INSERT INTO company (id,name) VALUES (  5,'Tandy Corporation')",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  1,'MacBook Pro 15.4 inch',null,null,1)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  2,'CM-2a',null,null,2)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  3,'CM-200',null,null,2)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  4,'CM-5e',null,null,2)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  5,'CM-5','1991-01-01',null,2)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  6,'MacBook Pro','2006-01-10',null,1)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  7,'Apple IIe',null,null,null)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  8,'Apple IIc',null,null,null)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES (  9,'Apple IIGS',null,null,null)",
				"INSERT INTO computer (id,name,introduced,discontinued,company_id) VALUES ( 10,'Apple IIc Plus',null,null,null)" };

		final Statement statement = DatabaseConnection.getInstance().createStatement();
		for (final String query : queries)
			statement.execute(query);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testCheckDataEntityNameSoLong() {
		final Computer computer = new Computer(
				"TestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofhgijfghdfighdfgdfh",
				LocalDateTime.now(), LocalDateTime.now(), 0);
		this.exception.expect(ServiceException.class);
		ComputerServiceTest.computerService.checkDataEntity(computer);
	}

	@Test
	public void testCheckDataEntityNoName() {
		final Computer computer = new Computer("", LocalDateTime.now(), LocalDateTime.now(), 0);
		this.exception.expect(ServiceException.class);
		ComputerServiceTest.computerService.checkDataEntity(computer);
	}

	@Test
	public void testGet() {
		Assert.assertTrue(ComputerServiceTest.computerService.get().size() == 10);
	}

	@Test
	public void testGetWithBadId() {
		final int id = 50;
		Assert.assertNull(ComputerServiceTest.computerService.get(id));
	}

	@Test
	public void testGetWithId() {
		final int id = 2;
		Assert.assertTrue(ComputerServiceTest.computerService.get(id).getId() == 2);
		Assert.assertTrue(ComputerServiceTest.computerService.get(id).getName().equals("CM-2a"));
	}

	@Test
	public void testRemove() {
		final IEntity entity = new Computer();
		entity.setId(8);
		Assert.assertTrue(ComputerServiceTest.computerService.remove(entity));
	}

	public void testRemoveWithBadId() {
		final IEntity entity = new Computer();
		entity.setId(50);
		Assert.assertFalse(ComputerServiceTest.computerService.remove(entity));
	}

	@Test
	public void testSave() {
		final LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
		Computer computer = new Computer("TestServiceComputer", localDateTime, localDateTime, 0);
		computer = ComputerServiceTest.computerService.save(computer);
		Assert.assertTrue(computer != null);
		Assert.assertTrue(computer.getName().equals("TestServiceComputer"));
		computer = ComputerServiceTest.computerService.get(computer.getId());
		Assert.assertTrue(computer.getName().equals("TestServiceComputer"));
		Assert.assertTrue(computer.getId() != 0);
		final long idOk = computer.getId();
		Assert.assertTrue(computer.getCompanyId() == 0);
		Assert.assertTrue(computer.getDiscontinued().equals(localDateTime));
		Assert.assertTrue(computer.getIntroduced().equals(localDateTime));
		computer.setName("TestServiceComputerUpdated");
		computer.setDiscontinued(localDateTime.plusYears(1));
		computer.setIntroduced(localDateTime.plusYears(2));
		computer.setCompanyId(2);
		computer = ComputerServiceTest.computerService.save(computer);
		Assert.assertTrue(computer != null);
		Assert.assertTrue(computer.getName().equals("TestServiceComputerUpdated"));
		computer = ComputerServiceTest.computerService.get(computer.getId());
		Assert.assertTrue(computer.getName().equals("TestServiceComputerUpdated"));
		Assert.assertTrue(computer.getId() == idOk);
		Assert.assertTrue(computer.getCompanyId() == 2);
		Assert.assertTrue(computer.getDiscontinued().equals(localDateTime.plusYears(1)));
		Assert.assertTrue(computer.getIntroduced().equals(localDateTime.plusYears(2)));
	}

}
