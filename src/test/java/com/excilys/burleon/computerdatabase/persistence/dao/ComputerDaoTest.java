/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import java.time.LocalDateTime;
import java.util.Optional;

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

        final Optional<Computer> computer = ComputerDaoTest.computerDao
                .create(new Computer.ComputerBuilder().name("TestServiceComputer").introduced(LocalDateTime.now())
                        .discontinued(LocalDateTime.now()).build());
        Assert.assertTrue(computer.get().getId() == ComputerDaoTest.computerDao
                .find(Computer.class, computer.get().getId()).get().getId());
    }

    @Test
    public void testDelete() {
        final Optional<Computer> computer = ComputerDaoTest.computerDao.find(Computer.class, 7);
        Assert.assertTrue(ComputerDaoTest.computerDao.delete(computer.get()));
    }

    @Test
    public void testFind() {
        Assert.assertTrue(ComputerDaoTest.computerDao.find(Computer.class).size() == 200);
    }

    @Test
    public void testFindWithBadId() {
        final int id = -1;
        final Optional<Computer> computer = ComputerDaoTest.computerDao.find(Computer.class, id);
        Assert.assertFalse(computer.isPresent());
    }

    @Test
    public void testFindWithId() {
        final int id = 10;
        final Optional<Computer> computer = ComputerDaoTest.computerDao.find(Computer.class, id);
        Assert.assertTrue(computer.get().getId() == id && computer.get().getName().equals("Apple IIc Plus"));
    }

    @Test
    public void testUpdate() {
        final Optional<Computer> computer = ComputerDaoTest.computerDao.find(Computer.class, 6);
        Assert.assertTrue(computer.get().getId() == 6 && computer.get().getName().equals("MacBook Pro"));
        computer.get().setName("testNameCool");
        ComputerDaoTest.computerDao.update(computer.get());
        final Optional<Computer> computerUpdated = ComputerDaoTest.computerDao.find(Computer.class, 6);
        Assert.assertTrue(computerUpdated.get().getName().equals("testNameCool"));
        computerUpdated.get().setName("MacBook Pro");
        ComputerDaoTest.computerDao.update(computerUpdated.get());
    }

}
