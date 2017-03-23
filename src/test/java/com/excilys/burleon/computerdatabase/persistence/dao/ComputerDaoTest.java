/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.tool.Utility;

/**
 * @author Junior Burléon
 *
 */
public class ComputerDaoTest {

    private static ComputerDao computerDao;

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
    }

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        ComputerDaoTest.computerDao = ComputerDao.INSTANCE;
        Utility.loadAndResetDatabase();
    }

    @Test
    public void testCreate() {

        final Computer computer = ComputerDaoTest.computerDao
                .create(new Computer.ComputerBuilder().name("TestServiceComputer").introduced(LocalDateTime.now())
                        .discontinued(LocalDateTime.now()).build());
        Assert.assertTrue(
                computer.getId() == ComputerDaoTest.computerDao.find(Computer.class, computer.getId()).getId());
    }

    @Test
    public void testDelete() {
        final Computer computer = ComputerDaoTest.computerDao.find(Computer.class, 7);
        Assert.assertTrue(ComputerDaoTest.computerDao.delete(computer));
    }

    @Test
    public void testFind() {
        Assert.assertTrue(ComputerDaoTest.computerDao.find(Computer.class).size() == 200);
    }

    @Test
    public void testFindWithBadId() {
        final int id = -1;
        final Computer computer = ComputerDaoTest.computerDao.find(Computer.class, id);
        Assert.assertNull(computer);
    }

    @Test
    public void testFindWithId() {
        final int id = 10;
        final Computer computer = ComputerDaoTest.computerDao.find(Computer.class, id);
        Assert.assertTrue(computer.getId() == id && computer.getName().equals("Apple IIc Plus"));
    }

    @Test
    public void testUpdate() {
        final Computer computer = ComputerDaoTest.computerDao.find(Computer.class, 6);
        Assert.assertTrue(computer.getId() == 6 && computer.getName().equals("MacBook Pro"));
        computer.setName("testNameCool");
        ComputerDaoTest.computerDao.update(computer);
        final Computer computerUpdated = ComputerDaoTest.computerDao.find(Computer.class, 6);
        Assert.assertTrue(computerUpdated.getName().equals("testNameCool"));
        computerUpdated.setName("MacBook Pro");
        ComputerDaoTest.computerDao.update(computerUpdated);
    }

}
