/**
 *
 */
package com.excilys.burleon.computerdatabase.repository.dao;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
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
import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.repository.spring.config.RepositoryConfig;
import com.excilys.burleon.computerdatabase.repository.util.Utility;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(classes = RepositoryConfig.class) })
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

    /*
     * Following tests are here to check wether one
     * single computer can be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The user wants to show an existing computer
     * tested with findsAndReadsExistingComputerWithAllParameters
     * findAndReadsExistingComputerWithOnlyMandatoryParameters
     *  - The Id searched is not in the DB
     * tested with findsAndReadsUnexistingComputer
     */

    @Test
    public void findsAndReadsExistingComputerByIdWithOnlyMandatoryParameters()
            throws Exception {
        assertEquals(this.computerDao.findById(Computer.class, 200).get().getId(), 200);
        assertEquals(this.computerDao.findById(Computer.class, 200).get().getName(), ("PowerBook 500 series"));
        assertNull(this.computerDao.findById(Computer.class, 200).get().getCompany());
        assertNull(this.computerDao.findById(Computer.class, 200).get().getIntroduced());
        assertNull(this.computerDao.findById(Computer.class, 200).get().getDiscontinued());
    }

    @Test
    public void findsAndReadsExistingComputerByIdWithAllParameters()
            throws Exception {
        assertEquals(this.computerDao.findById(Computer.class, 80).get().getId(), 80);
        assertEquals(this.computerDao.findById(Computer.class, 80).get().getName(), "Macintosh SE/30");
        assertEquals(this.computerDao.findById(Computer.class, 80).get().getCompany().getId(), 1);
        assertEquals(this.computerDao.findById(Computer.class, 80).get().getCompany().getName(), "Apple Inc.");
//        assertEquals(this.computerDao.findById(Computer.class, 80).get().getIntroduced(), LocalDateTime.of(1989, 01, 19,0,0));
//        assertEquals(this.computerDao.findById(Computer.class, 80).get().getDiscontinued(), LocalDateTime.of(1991, 10, 21,0,0));
    }

    @Test
    public void findsAndReadsUnexistingComputer() throws Exception {
        assertFalse(this.computerDao.findById(Computer.class, 800).isPresent());
    }
    
    /*
     * Following tests are here to check wether several
     * single computer can be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The user wants to show existing companies
     * tested with findsAndReadsExistingComputersByPage
     * tested with findsAndReadsExistingComputersByPageWithFilter
     *  - One Id searched is not in the DB
     * tested with findsAndReadsExistingComputersWithFirstIdNotInDB
     * tested with findsAndReadsExistingComputersByPageGoingTooFar
     * tested with findsAndReadsUnexistingComputers
     */

    @Test
    public void findsAndReadsExistingComputersByPage()
            throws Exception {
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).get(0).getId(), 152);
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).get(1).getId(), 46);
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).get(2).getId(), 57);
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).get(0).getName(), "Acorn Archimedes");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).get(1).getName(), "Acorn System 2");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).get(2).getName(), "Altair 8800");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).size(), 3);
    }
    
    @Test
    public void findsAndReadsExistingComputersByPageWithFilter()
            throws Exception {
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).get(0).getId(), 101);
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).get(1).getId(), 102);
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).get(2).getId(), 99);
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).get(0).getName(), "Z1");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).get(1).getName(), "Z2");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).get(2).getName(), "Z3");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 10, "Z", null).size(), 5);
    }
    
    @Test
    public void findsAndReadsExistingComputersByPageGoingTooFar()
            throws Exception {
        assertEquals(this.computerDao.findRange(Computer.class, 198, 3, null, null).get(0).getId(), 100);
        assertEquals(this.computerDao.findRange(Computer.class, 198, 3, null, null).get(1).getId(), 149);
        assertEquals(this.computerDao.findRange(Computer.class, 198, 3, null, null).get(0).getName(), "Z4");
        assertEquals(this.computerDao.findRange(Computer.class, 198, 3, null, null).get(1).getName(), "ZX Spectrum");
        assertEquals(this.computerDao.findRange(Computer.class, 198, 3, null, null).size(), 2);
    }
    
    @Test
    public void findsAndReadsExistingComputersWithFirstIdNotInDB()
            throws Exception {
        assertEquals(this.computerDao.findRange(Computer.class, -100, 3, null, null).get(0).getId(), 152);
        assertEquals(this.computerDao.findRange(Computer.class, -100, 3, null, null).get(1).getId(), 46);
        assertEquals(this.computerDao.findRange(Computer.class, -100, 3, null, null).get(2).getId(), 57);
        assertEquals(this.computerDao.findRange(Computer.class, -100, 3, null, null).get(0).getName(), "Acorn Archimedes");
        assertEquals(this.computerDao.findRange(Computer.class, -100, 3, null, null).get(1).getName(), "Acorn System 2");
        assertEquals(this.computerDao.findRange(Computer.class, -100, 3, null, null).get(2).getName(), "Altair 8800");
        assertEquals(this.computerDao.findRange(Computer.class, 0, 3, null, null).size(), 3);
    }
    
    @Test
    public void findsAndReadsUnexistingComputers()
            throws Exception {
        assertEquals(this.computerDao.findRange(Computer.class, 2000, 3, null, null).size(), 0);
    }
    
    /**
     * Test to find a range of company.
     */
    @Test
    public void findRange() {
        Assert.assertTrue(this.computerDao.findRange(Computer.class, 0, 5, "CM",
                OrderComputerEnum.NAME).get(0).getId() == 14);
        Assert.assertTrue(this.computerDao.findRange(Computer.class, 0, 5, "CM",
                OrderComputerEnum.NAME).get(1).getId() == 3);
        Assert.assertTrue(this.computerDao.findRange(Computer.class, 0, 5, "CM",
                OrderComputerEnum.NAME).get(2).getId() == 2);
        Assert.assertTrue(this.computerDao.findRange(Computer.class, 0, 5, "CM",
                OrderComputerEnum.NAME).get(3).getId() == 5);
        Assert.assertTrue(this.computerDao.findRange(Computer.class, 0, 5, "CM",
                OrderComputerEnum.NAME).get(4).getId() == 4);
    }

    /**
     * Test to count the number of computer with a filter word.
     */
    @Test
    public void getNbRecords() {
        final String filterWord = "CM";

        final long nbComputers = this.computerDao.getNbRecordsByName(Computer.class, filterWord);
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
    @Transactional
    public void testCreate() {

        final Optional<Computer> computer = this.computerDao
                .create(new Computer.ComputerBuilder().name("TestServiceComputer").introduced(LocalDateTime.now())
                        .discontinued(LocalDateTime.now()).build());
        Assert.assertTrue(computer.get().getId() == this.computerDao
                .findById(Computer.class, computer.get().getId()).get().getId());
    }

    @Test
    @Transactional
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
    @Transactional
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
