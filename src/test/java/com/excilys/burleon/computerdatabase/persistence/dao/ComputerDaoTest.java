/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.util.Utility;

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
     * Test to fin a range of company.
     */
    @Test
    public void findRange() {
        final int first = 0;
        final int nbRecord = 5;
        final String filterWord = "CM";
        final IOrderEnum<Computer> order = OrderComputerEnum.NAME;

        final List<Computer> computers = ComputerDaoTest.computerDao.findRange(Computer.class, first, nbRecord,
                filterWord, order);
        Assert.assertTrue(computers.get(0).getId() == 14);
        Assert.assertTrue(computers.get(1).getId() == 3);
        Assert.assertTrue(computers.get(2).getId() == 2);
        Assert.assertTrue(computers.get(3).getId() == 5);
        Assert.assertTrue(computers.get(4).getId() == 4);
    }

    /**
     * Test to count the number of computer with a filter word.
     */
    @Test
    public void getNbRecords() {
        final String filterWord = "CM";

        final long nbComputers = ComputerDaoTest.computerDao.getNbRecords(Computer.class, filterWord);
        Assert.assertTrue(nbComputers == 5);
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
                .findById(Computer.class, computer.get().getId()).get().getId());
    }

    @Test
    public void testDelete() {
        final Optional<Computer> computer = ComputerDaoTest.computerDao.findById(Computer.class, 7);
        Assert.assertTrue(
                ComputerDaoTest.computerDao.delete(computer.get(), DatabaseConnection.INSTANCE.getConnection()));
    }

    @Test
    public void testFind() {
        Assert.assertTrue(ComputerDaoTest.computerDao.find(Computer.class).size() == 200);
    }

    @Test
    public void testFindWithBadId() {
        final int id = -1;
        final Optional<Computer> computer = ComputerDaoTest.computerDao.findById(Computer.class, id);
        Assert.assertFalse(computer.isPresent());
    }

    @Test
    public void testFindWithId() {
        final int id = 10;
        final Optional<Computer> computer = ComputerDaoTest.computerDao.findById(Computer.class, id);
        Assert.assertTrue(computer.get().getId() == id && computer.get().getName().equals("Apple IIc Plus"));
    }

    @Test
    public void testUpdate() {
        final Optional<Computer> computer = ComputerDaoTest.computerDao.findById(Computer.class, 6);
        Assert.assertTrue(computer.get().getId() == 6 && computer.get().getName().equals("MacBook Pro"));
        computer.get().setName("testNameCool");
        ComputerDaoTest.computerDao.update(computer.get());
        final Optional<Computer> computerUpdated = ComputerDaoTest.computerDao.findById(Computer.class, 6);
        Assert.assertTrue(computerUpdated.get().getName().equals("testNameCool"));
        computerUpdated.get().setName("MacBook Pro");
        ComputerDaoTest.computerDao.update(computerUpdated.get());
    }

}
