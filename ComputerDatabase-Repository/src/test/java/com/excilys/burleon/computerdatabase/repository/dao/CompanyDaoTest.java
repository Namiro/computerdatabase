/**
 *
 */
package com.excilys.burleon.computerdatabase.repository.dao;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.repository.spring.config.RepositoryConfig;
import com.excilys.burleon.computerdatabase.repository.util.Utility;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(classes = RepositoryConfig.class) })
@ActiveProfiles("test")
public class CompanyDaoTest {

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Autowired
    private CompanyDao companyDao;

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
     * single company can be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The user wants to show an existing company
     * tested with findsAndReadsExistingCompanyByPage.
     * tested with findsAndReadExistingCompany
     *  - The Id searched is not in the DB
     * tested with findsAndReadsExistingCompanyWithdNotInDB
     */
    
    @Test
    public void findsAndReadsExistingCompanyByPage() throws Exception {
        assertEquals(22, this.companyDao.findRange(Company.class, 0, 1, null, null).get(0).getId());
        assertEquals("Acorn computer", this.companyDao.findRange(Company.class, 0, 1, null, null).get(0).getName());
    }
    
    @Test
    public void findsAndReadsExistingCompany() throws Exception {
        assertEquals(22, this.companyDao.findById(Company.class, 22).get().getId());
        assertEquals("Acorn computer", this.companyDao.findById(Company.class, 22).get().getName());
    }
    
    @Test
    public void findsAndReadsExistingCompanyWithdNotInDB() throws Exception {
        assertFalse(this.companyDao.findById(Company.class, 2000).isPresent());
    }
    
    /*
     * Following tests are here to check wether several
     * companies can be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The user wants to show existing companies
     * tested with findsAndReadsExistingCompaniesByPage.
     * tested with findsAndReadsExistingCompaniesByPageWithFilter
     *  - One Id searched is not in the DB
     * tested with findsAndReadsExistingCompaniesWithFirstIdNotInDB
     * tested with findsAndReadsExistingCompaniesByPageGoingTooFar
     * tested with findsAndReadsUnexistingCompanies
     */

    @Test
    public void findsAndReadsExistingCompaniesByPage() throws Exception {
        assertEquals(22, this.companyDao.findRange(Company.class, 0, 3, null, null).get(0).getId());
        assertEquals(29, this.companyDao.findRange(Company.class, 0, 3, null, null).get(1).getId());
        assertEquals(14, this.companyDao.findRange(Company.class, 0, 3, null, null).get(2).getId());
        assertEquals("Acorn computer",this.companyDao.findRange(Company.class, 0, 3, null, null).get(0).getName());
        assertEquals("ACVS", this.companyDao.findRange(Company.class, 0, 3, null, null).get(1).getName());
        assertEquals("Amiga Corporation", this.companyDao.findRange(Company.class, 0, 3, null, null).get(2).getName());
    }
    
    @Test
    public void findsAndReadsExistingCompaniesByPageWithFilter() throws Exception {
        assertEquals(28, this.companyDao.findRange(Company.class, 0, 3, "Z", null).get(0).getId());
        assertEquals("Zemmix",this.companyDao.findRange(Company.class, 0, 3, "Z", null).get(0).getName());
        assertEquals(1, this.companyDao.findRange(Company.class, 0, 3, "Z", null).size());
    }
    
    @Test
    public void findsAndReadsExistingCompaniesWithFirstIdNotInDB() throws Exception {
        assertEquals(22, this.companyDao.findRange(Company.class, -255, 3, null, null).get(0).getId());
        assertEquals(29, this.companyDao.findRange(Company.class, -255, 3, null, null).get(1).getId());
        assertEquals(14, this.companyDao.findRange(Company.class, -255, 3, null, null).get(2).getId());
        assertEquals("Acorn computer",this.companyDao.findRange(Company.class, -255, 3, null, null).get(0).getName());
        assertEquals("ACVS", this.companyDao.findRange(Company.class, -255, 3, null, null).get(1).getName());
        assertEquals("Amiga Corporation",this.companyDao.findRange(Company.class, -255, 3, null, null).get(2).getName());
    }
    
    @Test
    public void findsAndReadsExistingCompaniesByPageGoingTooFar() throws Exception {
        assertEquals(26, this.companyDao.findRange(Company.class, 40, 10, null, null).get(0).getId());
        assertEquals(28, this.companyDao.findRange(Company.class, 40, 10, null, null).get(1).getId());
        assertEquals("Xerox", this.companyDao.findRange(Company.class, 40, 10, null, null).get(0).getName());
        assertEquals("Zemmix",this.companyDao.findRange(Company.class, 40, 10, null, null).get(1).getName());
        assertEquals(2, this.companyDao.findRange(Company.class, 40, 10, null, null).size());
    }
    
    @Test
    public void findsAndReadsUnexistingCompanies() throws Exception {
        assertEquals(0, this.companyDao.findRange(Company.class, 43, 10, null, null).size());
    }

    /*
     * Following tests are here to check wether we
     * can create a new company.
     * Here we shall test if:
     * 
     *  - The user wants to create a company with legal parameters
     * tested with createCompany
     *  - The user tries to create a bad company
     * tested with createCompanyWithBadId
     */
    
    @Test
    @Transactional
    public void createCompany() throws Exception {
        assertEquals(42, this.companyDao.find(Company.class).size());
        this.companyDao.create(new Company.CompanyBuilder().build());
        assertEquals(43, this.companyDao.find(Company.class).size());
    }
    
    @Test
    @Transactional
    public void createCompanyWithBadId() throws Exception {
        try {
            this.companyDao.create(new Company(10L, "Apple"));
            fail("Should not be able to persist existing id");
          }catch(javax.persistence.PersistenceException e){
            assert(e.getMessage().contains("detached entity passed to persist"));
          }
        assertEquals(42, this.companyDao.find(Company.class).size());
    }
    
    

    /*
     * Following test is here to check wether we
     * can count the number of companies.
     * Here we shall test if:
     * 
     *  - The user wants to count companies
     * tested with countCompanies
     * tested with countCompaniesByPage
     * 
     *  - The user want to count companies with a filter word
     * tested with countFilteredCompaniesByPage
     */
    
    @Test
    public void countCompanies() {
        assertEquals(42, this.companyDao.find(Company.class).size());
    }
    
    @Test
    public void countCompaniesByPage() {
        assertEquals(42, this.companyDao.findRange(Company.class, 0, 100, null, null).size());
    }
    
    @Test
    public void countFilteredCompaniesByPage() {
        assertEquals(1, this.companyDao.findRange(Company.class, 0, 100, "Apple", null).size());
    }
    
    /*
     * Following tests are here to check wether we
     * can update a company.
     * Here we shall test if:
     * 
     *  - The user wants to update an existing company
     * tested with updateCompany
     *  - The user tries to update a bad company
     * tested with updateUnexistingCompany
     */
    
    @Test
    @Transactional
    public void updateCompany() throws Exception {
        this.companyDao.update(new Company(10L, "test"));
        assertEquals(10, this.companyDao.findById(Company.class, 10).get().getId());
        assertEquals("test", this.companyDao.findById(Company.class, 10).get().getName());
    }
    
    @Test
    @Transactional
    public void updateUnexistingCompany() throws Exception {
        this.companyDao.update(new Company(60L, "test"));
        assertFalse(this.companyDao.findById(Company.class, 60).isPresent());
    }
}
