/**
 *
 */
package com.excilys.burleon.computerdatabase.service.service;

import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.excilys.burleon.computerdatabase.persistence.dao.DatabaseConnection;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;

/**
 * @author Junior Burléon
 *
 */
public class CompanyServiceTest {

    private static ICompanyService companyService;

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() {
        DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        CompanyServiceTest.companyService = new CompanyService();
        final String[] queries = { "SET FOREIGN_KEY_CHECKS = 0", "TRUNCATE company", "SET FOREIGN_KEY_CHECKS = 1",
                "INSERT INTO company (id,name) VALUES (  1,'Apple Inc.')",
                "INSERT INTO company (id,name) VALUES (  2,'Thinking Machines')",
                "INSERT INTO company (id,name) VALUES (  3,'RCA')",
                "INSERT INTO company (id,name) VALUES (  4,'Netronics')",
                "INSERT INTO company (id,name) VALUES (  5,'Tandy Corporation')" };

        final Statement statement = DatabaseConnection.INSTANCE.getConnection().createStatement();
        for (final String query : queries) {
            statement.execute(query);
        }
    }

    @Test
    public void testCheckDataEntityNameSoLong() {
        final Company company = new Company.CompanyBuilder()
                .name("rhgughrughguhdfguhfdiguhdfighfughdfguifghfgfhdgidfhgufidhgdfughfghfidghfduighfgudfhgdfuhgufhgdfuioghfioghuifdpghfdlgifdhguiodfghdfogfgdfgfi")
                .build();
        this.exception.expect(ServiceException.class);
        CompanyServiceTest.companyService.checkDataEntity(company);
    }

    @Test
    public void testCheckDataEntityNoName() throws ServiceException {
        final Company company = new Company.CompanyBuilder().name("").build();
        this.exception.expect(ServiceException.class);
        CompanyServiceTest.companyService.checkDataEntity(company);
    }

    @Test
    public void testGet() {
        Assert.assertTrue(CompanyServiceTest.companyService.get(Company.class).size() == 5);
    }

    @Test
    public void testGetWithBadId() {
        final int id = 50;
        Assert.assertNull(CompanyServiceTest.companyService.get(Company.class, id));
    }

    @Test
    public void testGetWithId() {
        final int id = 2;
        Assert.assertTrue(CompanyServiceTest.companyService.get(Company.class, id).getId() == 2);
        Assert.assertTrue(
                CompanyServiceTest.companyService.get(Company.class, id).getName().equals("Thinking Machines"));
    }

    @Test
    public void testSave() {
        Company company = new Company.CompanyBuilder().name("TestServiceCompany").build();
        Assert.assertTrue(CompanyServiceTest.companyService.save(company) != null);
        Assert.assertTrue((company = CompanyServiceTest.companyService.save(company)).getName()
                .equals("TestServiceCompany"));
        Assert.assertTrue(CompanyServiceTest.companyService.get(Company.class, company.getId()).getName()
                .equals("TestServiceCompany"));
        company.setName("TestServiceCompanyUpdated");
        Assert.assertTrue(CompanyServiceTest.companyService.save(company) != null);
        Assert.assertTrue(
                CompanyServiceTest.companyService.save(company).getName().equals("TestServiceCompanyUpdated"));
        Assert.assertTrue(CompanyServiceTest.companyService.get(Company.class, company.getId()).getName()
                .equals("TestServiceCompanyUpdated"));
    }

}
