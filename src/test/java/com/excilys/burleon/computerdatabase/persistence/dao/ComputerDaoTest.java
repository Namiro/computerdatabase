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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.excilys.burleon.computerdatabase.repository.dao.ComputerDao;
import com.excilys.burleon.computerdatabase.repository.dao.DatabaseConnection;
import com.excilys.burleon.computerdatabase.repository.model.Computer;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.spring.config.ApplicationConfig;
import com.excilys.burleon.computerdatabase.util.Utility;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = ApplicationConfig.class) })
@ActiveProfiles("test")
public class ComputerDaoTest {
    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private DatabaseConnection databaseConnection;

    /**
     * Test to fin a range of company.
     */
    @Test
    public void findRange() {
        final int first = 0;
        final int nbRecord = 5;
        final String filterWord = "CM";
        final IOrderEnum<Computer> order = OrderComputerEnum.NAME;

        final List<Computer> computers = this.computerDao.findRange(Computer.class, first, nbRecord, filterWord,
                order);
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

        final long nbComputers = this.computerDao.getNbRecords(Computer.class, filterWord);
        Assert.assertTrue(nbComputers == 5);
    }

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        Utility.loadAndResetDatabase();
    }

    @Test
    public void testCreate() {

        final Optional<Computer> computer = this.computerDao
                .create(new Computer.ComputerBuilder().name("TestServiceComputer").introduced(LocalDateTime.now())
                        .discontinued(LocalDateTime.now()).build());
        Assert.assertTrue(computer.get().getId() == this.computerDao
                .findById(Computer.class, computer.get().getId()).get().getId());
    }

    @Test
    public void testDelete() {
        final Optional<Computer> computer = this.computerDao.findById(Computer.class, 7);
        Assert.assertTrue(this.computerDao.delete(computer.get()));
    }

    @Test
    public void testFind() {
        Assert.assertTrue(this.computerDao.find(Computer.class).size() == 200);
    }

    @Test
    public void testFindWithBadId() {
        final int id = -1;
        final Optional<Computer> computer = this.computerDao.findById(Computer.class, id);
        Assert.assertFalse(computer.isPresent());
    }

    @Test
    public void testFindWithId() {
        final int id = 10;
        final Optional<Computer> computer = this.computerDao.findById(Computer.class, id);
        Assert.assertTrue(computer.get().getId() == id && computer.get().getName().equals("Apple IIc Plus"));
    }

    @Test
    public void testUpdate() {
        final Optional<Computer> computer = this.computerDao.findById(Computer.class, 6);
        Assert.assertTrue(computer.get().getId() == 6 && computer.get().getName().equals("MacBook Pro"));
        computer.get().setName("testNameCool");
        this.computerDao.update(computer.get());
        final Optional<Computer> computerUpdated = this.computerDao.findById(Computer.class, 6);
        Assert.assertTrue(computerUpdated.get().getName().equals("testNameCool"));
        computerUpdated.get().setName("MacBook Pro");
        this.computerDao.update(computerUpdated.get());
    }

}
