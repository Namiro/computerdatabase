/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

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

import com.excilys.burleon.computerdatabase.repository.dao.CompanyDao;
import com.excilys.burleon.computerdatabase.repository.model.Company;
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

    /**
     * Test find a company.
     */
    @Test
    public void testFind() {
        Assert.assertEquals(42, this.companyDao.find(Company.class).size());
    }
}
