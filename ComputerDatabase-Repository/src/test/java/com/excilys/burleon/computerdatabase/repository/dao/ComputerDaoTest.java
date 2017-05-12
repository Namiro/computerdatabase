/**
 *
 */
package com.excilys.burleon.computerdatabase.repository.dao;

import static org.junit.Assert.*;
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

    
    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        Utility.loadAndResetDatabase();
    }
    
    /*
     * Following tests are here to check wether one
     * single computer can be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The user wants to show an existing computer
     * tested with findsAndReadsExistingComputerWithAllParameters
     * tested with findAndReadsExistingComputerWithOnlyMandatoryParameters
     * tested with findRange
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
  
    /*
     * Following tests are here to check wether we
     * can create a new computer.
     * Here we shall test if:
     * 
     *  - The user wants to create a computer with legal parameters
     * tested with createComputer
     *  - The user tries to create a bad computer
     * tested with createComputerWithBadId
     */
    
    @Test
    @Transactional
    public void createComputer() throws Exception {
        assertEquals(200, this.computerDao.find(Computer.class).size());
        this.computerDao.create(new Computer.ComputerBuilder().build());
        assertEquals(201, this.computerDao.find(Computer.class).size());
    }
    
    @Test
    @Transactional
    public void createCompanyWithBadId() throws Exception {
        try {
            this.computerDao.create(new Computer(10L, "Apple", null, null, null));
            fail("Should not be able to persist existing id");
          }catch(javax.persistence.PersistenceException e){
            assert(e.getMessage().contains("detached entity passed to persist"));
          }
        assertEquals(200, this.computerDao.find(Computer.class).size());
    }

    /*
     * Following test are here to check wether we
     * can count the number of computers.
     * Here we shall test if:
     * 
     *  - The user wants to count computers
     * tested with countComputers
     * tested with countComputersByPage
     * 
     *  - The user want to count computers with a filter word
     * tested with countFilteredComputersByPage
     */
    
    @Test
    public void countComputers() {
        assertEquals(200, this.computerDao.find(Computer.class).size());
        assertEquals(200, this.computerDao.getNbRecords(Computer.class));
        assertEquals(200, this.computerDao.findRange(Computer.class, 0, 400, null, null).size());
    }
    
    @Test
    public void countFilteredComputersByPage() {
        assertEquals(6, this.computerDao.findRange(Computer.class, 0, 400, "Thinking", null).size());
        assertEquals(6, this.computerDao.getNbRecordsByName(Computer.class, "Thinking"));
    }
    
    /*
     * Following tests are here to check wether we
     * can update a computer.
     * Here we shall test if:
     * 
     *  - The user wants to update an existing computer
     * tested with updateComputer
     *  - The user tries to update a bad computer
     * tested with updateUnexistingComputer
     */
    
    @Test
    @Transactional
    public void updateComputer() throws Exception {
        this.computerDao.update(new Computer(10L, "test", null, null, null));
        assertEquals(10, this.computerDao.findById(Computer.class, 10).get().getId());
        assertEquals("test", this.computerDao.findById(Computer.class, 10).get().getName());
    }
    
    @Test
    @Transactional
    public void updateUnexistingComputer() throws Exception {
        this.computerDao.update(new Computer(2000L, "test", null, null, null));
        assertFalse(this.computerDao.findById(Computer.class, 2000).isPresent());
    }
   
    /*
     * Following test are here to check wether we
     * can delete a computer.
     * Here we shall test if:
     * 
     *  - The user wants to delete anb existing computer
     * tested with deleteComputer
     * 
     *  - The user wants to delete an unexisting computer
     * tested with deleteUnexistingComputer
     */
    
    @Test
    @Transactional
    public void deleteComputer() {
        assertTrue(this.computerDao.delete(new Computer(8, null, null, null, null)));
        assertTrue(this.computerDao.delete(this.computerDao.findById(Computer.class, 7).get()));
    }
    
    @Test
    @Transactional
    public void deleteUnexistingComputer() {
        assertTrue(this.computerDao.delete(new Computer(2000, null, null, null, null)));
    }

}
