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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.repository.idao.ICompanyDao;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.EntityValidationException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.spring.config.ServiceConfig;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextHierarchy({ @ContextConfiguration(classes = ServiceConfig.class) })
public class CompanyServiceTest {

    @Mock
    private ICompanyDao mockCompanyDao;

    @InjectMocks
    @Autowired
    private ICompanyService companyService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckDataEntityNameSoLong() {
        final Company company = new Company.CompanyBuilder()
                .name("rhgughrughguhdfguhfdiguhdfighfughdfguifghfgfhdgidfhgufidhgdfughfghfidghfduighfgudfhgdfuhgufhgdfuioghfioghuifdpghfdlgifdhguiodfghdfogfgdfgfi")
                .build();
        this.exception.expect(EntityValidationException.class);
        this.companyService.checkDataEntity(company);
    }

    @Test
    public void testCheckDataEntityNoName() throws ServiceException {
        final Company company = new Company.CompanyBuilder().name("").build();
        this.exception.expect(EntityValidationException.class);
        this.companyService.checkDataEntity(company);
    }

    @Test
    public void testGet() {
        final ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company.CompanyBuilder().name("AAA").id(1).build());
        companies.add(new Company.CompanyBuilder().name("BBB").id(2).build());
        companies.add(new Company.CompanyBuilder().name("CCC").id(3).build());

        ReflectionTestUtils.setField(this.companyService, "dao", this.mockCompanyDao);
        Mockito.when(this.mockCompanyDao.find(Company.class)).thenReturn(companies);
        Assert.assertTrue(this.companyService.get(Company.class).size() == 3);
    }

    @Test
    public void testGetWithBadId() {
        final int id = -1;
        Mockito.when(this.mockCompanyDao.findById(Company.class, id)).thenReturn(Optional.empty());

        Assert.assertFalse(this.companyService.get(Company.class, id).isPresent());
    }

    @Test
    public void testGetWithId() {
        final int id = 2;
        ReflectionTestUtils.setField(this.companyService, "dao", this.mockCompanyDao);
        Mockito.when(this.mockCompanyDao.findById(Company.class, id))
                .thenReturn(Optional.ofNullable(new Company.CompanyBuilder().id(2).name("company").build()));

        final Optional<Company> company = this.companyService.get(Company.class, id);
        Assert.assertTrue(company.isPresent());
        Assert.assertTrue(company.get().getId() == 2);
        Assert.assertTrue(company.get().getName().equals("company"));
    }

    @Test
    public void testSave() {
        final Company company = new Company.CompanyBuilder().name("company").build();
        ReflectionTestUtils.setField(this.companyService, "dao", this.mockCompanyDao);

        Mockito.when(this.mockCompanyDao.create(company))
                .thenReturn(Optional.ofNullable(new Company.CompanyBuilder().id(2).name("company").build()));
        Mockito.when(this.mockCompanyDao.update(company))
                .thenReturn(Optional.ofNullable(new Company.CompanyBuilder().id(2).name("companyedited").build()));

        Optional<Company> companyOpt = this.companyService.save(company);
        Assert.assertTrue(companyOpt.isPresent());
        Assert.assertTrue(companyOpt.get().getId() == 2);
        Assert.assertTrue(companyOpt.get().getName().equals("company"));

        company.setName("companyedited");
        company.setId(2);
        companyOpt = this.companyService.save(company);
        Assert.assertTrue(companyOpt.isPresent());
        Assert.assertTrue(companyOpt.get().getId() == 2);
        Assert.assertTrue(companyOpt.get().getName().equals("companyedited"));
    }

}
