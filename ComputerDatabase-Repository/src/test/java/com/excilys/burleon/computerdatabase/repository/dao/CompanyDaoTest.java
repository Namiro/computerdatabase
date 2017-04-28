/**
 *
 */
package com.excilys.burleon.computerdatabase.repository.dao;

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

    /**
     * Test find a company.
     */
    @Test
    public void testFind() {
        Assert.assertEquals(42, this.companyDao.find(Company.class).size());
    }
}
