/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
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
        DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
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
     * Test find a company.
     */
    @Test
    public void testFind() {
        try {
            Assert.assertEquals(42, CompanyDaoTest.companyDao.find(Company.class).size());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
