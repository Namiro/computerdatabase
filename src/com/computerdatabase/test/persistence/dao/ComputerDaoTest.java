/**
 *
 */
package com.computerdatabase.test.persistence.dao;

import java.sql.Statement;
import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.computerdatabase.persistence.dao.ComputerDao;
import com.computerdatabase.persistence.dao.DatabaseConnection;
import com.computerdatabase.persistence.model.Computer;

/**
 * @author Junior Burléon
 *
 */
public class ComputerDaoTest {

	private static ComputerDao computerDao;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ComputerDaoTest.computerDao = new ComputerDao();

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
	public void testCreate() {

		final Computer computer = ComputerDaoTest.computerDao
				.create(new Computer("ComputerNameTest", LocalDateTime.now(), LocalDateTime.now(), 0));
		Assert.assertTrue(computer.getId() == 11);
	}

	@Test
	public void testDelete() {
		final Computer computer = ComputerDaoTest.computerDao.find(11);
		Assert.assertTrue(ComputerDaoTest.computerDao.delete(computer));
	}

	@Test
	public void testFind() {
		Assert.assertTrue(ComputerDaoTest.computerDao.find().size() == 10);
	}

	@Test
	public void testFindWithBadId() {
		final int id = 15;
		final Computer computer = ComputerDaoTest.computerDao.find(id);
		Assert.assertNull(computer);
	}

	@Test
	public void testFindWithId() {
		final int id = 10;
		final Computer computer = ComputerDaoTest.computerDao.find(id);
		Assert.assertTrue(computer.getId() == id && computer.getName().equals("Apple IIc Plus"));
	}

	@Test
	public void testUpdate() {
		final Computer computer = ComputerDaoTest.computerDao.find(6);
		Assert.assertTrue(computer.getId() == 6 && computer.getName().equals("MacBook Pro"));
		computer.setName("testNameCool");
		ComputerDaoTest.computerDao.update(computer);
		final Computer computerUpdated = ComputerDaoTest.computerDao.find(6);
		Assert.assertTrue(computerUpdated.getName().equals("testNameCool"));
		computerUpdated.setName("MacBook Pro");
		ComputerDaoTest.computerDao.update(computerUpdated);
	}

}
