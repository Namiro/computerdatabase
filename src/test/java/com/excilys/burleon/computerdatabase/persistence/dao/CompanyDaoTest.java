/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.tool.Utility;

/**
 * @author Junior Burléon
 *
 */
public class CompanyDaoTest {

    private static CompanyDao companyDao;

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
        CompanyDaoTest.companyDao = CompanyDao.INSTANCE;
        Utility.loadAndResetDatabase();
    }

    /**
     * Test delete a company (and the computer linked to this company).
     */
    @Test
    public void testDelete() {
        CompanyDaoTest.companyDao.delete(new Company.CompanyBuilder().id(1).build());
        Assert.assertEquals(41, CompanyDaoTest.companyDao.find(Company.class).size());
        Assert.assertFalse(CompanyDaoTest.companyDao.find(Company.class, 1).isPresent());
        ComputerDao.INSTANCE.find(Computer.class)
                .forEach((item) -> Assert.assertFalse(item.getCompany().getId() == 1));
    }

    /**
     * Test find a company.
     */
    @Test
    public void testFind() {
        Assert.assertEquals(42, CompanyDaoTest.companyDao.find(Company.class).size());
    }
}
