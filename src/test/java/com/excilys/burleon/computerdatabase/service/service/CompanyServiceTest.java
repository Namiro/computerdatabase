/**
 *
 */
package com.excilys.burleon.computerdatabase.service.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.excilys.burleon.computerdatabase.persistence.dao.ComputerDao;
import com.excilys.burleon.computerdatabase.persistence.dao.DaoFactory;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;

/**
 * @author Junior Burléon
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ComputerDao.class, DaoFactory.class })
public class CompanyServiceTest {

    private final static ICompanyService companyService = new CompanyService();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
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
        /*
         * final ArrayList<Computer> computers = new ArrayList<>();
         * computers.add(new
         * Computer.ComputerBuilder().name("AAA").id(1).build());
         * computers.add(new
         * Computer.ComputerBuilder().name("BBB").id(2).build());
         * computers.add(new
         * Computer.ComputerBuilder().name("CCC").id(3).build());
         *
         * final ComputerDao mockComputerDao =
         * PowerMockito.mock(ComputerDao.class); final DaoFactory mockFactory
         * = PowerMockito.mock(DaoFactory.class);
         * Whitebox.setInternalState(DaoFactory.class, "INSTANCE",
         * mockFactory);
         * PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(
         * mockComputerDao);
         * PowerMockito.when(mockComputerDao.find(Computer.class)).thenReturn(
         * computers);
         * Assert.assertTrue(CompanyServiceTest.companyService.get(Company.
         * class).size() == 3);
         */
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
