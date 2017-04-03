/**
 *
 */
package com.excilys.burleon.computerdatabase.service.service;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.excilys.burleon.computerdatabase.persistence.dao.CompanyDao;
import com.excilys.burleon.computerdatabase.persistence.dao.DaoFactory;
import com.excilys.burleon.computerdatabase.persistence.idao.ICompanyDao;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;

/**
 * @author Junior Burléon
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CompanyDao.class, DaoFactory.class })
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
        final ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company.CompanyBuilder().name("AAA").id(1).build());
        companies.add(new Company.CompanyBuilder().name("BBB").id(2).build());
        companies.add(new Company.CompanyBuilder().name("CCC").id(3).build());

        final ICompanyDao mockCompanyDao = PowerMockito.mock(ICompanyDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Company.class)).thenReturn(mockCompanyDao);
        PowerMockito.when(mockCompanyDao.find(Company.class)).thenReturn(companies);
        Assert.assertTrue(CompanyServiceTest.companyService.get(Company.class).size() == 3);
    }

    @Test
    public void testGetWithBadId() {
        final ICompanyDao mockCompanyDao = PowerMockito.mock(ICompanyDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Company.class)).thenReturn(mockCompanyDao);
        final int id = -1;
        PowerMockito.when(mockCompanyDao.find(Company.class, id)).thenReturn(Optional.empty());

        Assert.assertFalse(CompanyServiceTest.companyService.get(Company.class, id).isPresent());
    }

    @Test
    public void testGetWithId() {
        final int id = 2;
        final ICompanyDao mockCompanyDao = PowerMockito.mock(ICompanyDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Company.class)).thenReturn(mockCompanyDao);
        PowerMockito.when(mockCompanyDao.find(Company.class, id))
                .thenReturn(Optional.ofNullable(new Company.CompanyBuilder().id(2).name("company").build()));

        final Optional<Company> company = CompanyServiceTest.companyService.get(Company.class, id);
        Assert.assertTrue(company.isPresent());
        Assert.assertTrue(company.get().getId() == 2);
        Assert.assertTrue(company.get().getName().equals("company"));
    }

    @Test
    public void testSave() {
        final Company company = new Company.CompanyBuilder().name("company").build();
        final ICompanyDao mockCompanyDao = PowerMockito.mock(ICompanyDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Company.class)).thenReturn(mockCompanyDao);
        PowerMockito.when(mockCompanyDao.create(company))
                .thenReturn(Optional.ofNullable(new Company.CompanyBuilder().id(2).name("company").build()));
        PowerMockito.when(mockCompanyDao.update(company))
                .thenReturn(Optional.ofNullable(new Company.CompanyBuilder().id(2).name("companyedited").build()));

        Optional<Company> companyOpt = CompanyServiceTest.companyService.save(company);
        Assert.assertTrue(companyOpt.isPresent());
        Assert.assertTrue(companyOpt.get().getId() == 2);
        Assert.assertTrue(companyOpt.get().getName().equals("company"));

        company.setName("companyedited");
        company.setId(2);
        companyOpt = CompanyServiceTest.companyService.save(company);
        Assert.assertTrue(companyOpt.isPresent());
        Assert.assertTrue(companyOpt.get().getId() == 2);
        Assert.assertTrue(companyOpt.get().getName().equals("companyedited"));
    }

}
