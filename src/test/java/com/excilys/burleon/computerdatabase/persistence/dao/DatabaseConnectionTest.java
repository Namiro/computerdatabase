/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.excilys.burleon.computerdatabase.spring.config.MainConfig;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = MainConfig.class) })
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    DatabaseConnection databaseConnection;

    @Test
    public void testConnection() {
        Assert.assertNotNull(this.databaseConnection);
    }
}
