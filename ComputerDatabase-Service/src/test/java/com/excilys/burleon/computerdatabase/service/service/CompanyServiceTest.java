/**
 *
 */
package com.excilys.burleon.computerdatabase.service.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderCompanyEnum;
import com.excilys.burleon.computerdatabase.repository.idao.ICompanyDao;
import com.excilys.burleon.computerdatabase.service.exception.DataValidationException;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
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

    /*
     * Following tests are here to check computer datas
     * according to the client needs.
     * Here we shall test if:
     * 
     *  - The user tries to enter a company a long name.
     * tested with testCheckDataEntityNameSoLong
     *  - The user tries to enter a company with no name
     * tested with testCheckDataEntityNoName
     *  - The user tries to enter a company with a bad id
     * tested with testCheckDataBadId
     *  - The user tries to enter a company with no entity
     * tested with testCheckDataNoEntity
     */
    @Test
    public void testCheckDataEntityNameSoLong() {
        final Company company = new Company.CompanyBuilder()
                .name("rhgughrughguhdfguhfdiguhdfighfughdfguifghfgfhdgidfhgufidhgdfughfghfidghfduighfgudfhgdfuhgufhgdfuioghfioghuifdpghfdlgifdhguiodfghdfogfgdfgfi")
                .build();
        this.exception.expect(DataValidationException.class);
        this.companyService.checkDataEntity(company);
    }

    @Test
    public void testCheckDataEntityNoName() throws ServiceException {
        final Company company = new Company.CompanyBuilder().name("").build();
        this.exception.expect(DataValidationException.class);
        this.companyService.checkDataEntity(company);
    }
    
    public void testCheckDataNoEntity() {
        final Company company = null;
        this.exception.expect(ServiceException.class);
        this.companyService.checkDataEntity(company);
    }
    
    @Test
    public void testCheckDataBadId() { 
    final Company company = new Company.CompanyBuilder().id(-4).build();
    this.exception.expect(ServiceException.class);
    this.companyService.checkDataEntity(company);
}

    /*
     * Following tests are here to check wether we can get companies
     * according to the client needs.
     * Here we shall test if:
     * 
     *  - The user tries to get several companies
     * tested with testGet
     *  - The user tries to get a single company
     * tested with testGetSingleCompany
     *  - The user tries to get a company page
     * tested with testGetPage
     *  - The user tries to get a company with a bad id
     * tested with testGetWithBadId
     *  - The user tries to get a company by id
     * tested with testGetWithId
     */ 
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
    public void testGetSingleCompany() {
        Optional<Company> company = Optional.of(new Company.CompanyBuilder().id(34).name("test").build());
        ReflectionTestUtils.setField(this.companyService, "dao", this.mockCompanyDao);
        Mockito.when(this.mockCompanyDao.findById(Company.class, 34)).thenReturn(company);
        assertEquals(this.companyService.get(Company.class, 34).get().getId(),34);
        assertEquals(this.companyService.get(Company.class, 34).get().getName(),"test");
    }
    
    @Test
    public void testGetPage() {
        final ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company.CompanyBuilder().name("AAA").id(1).build());
        companies.add(new Company.CompanyBuilder().name("BBB").id(2).build());
        companies.add(new Company.CompanyBuilder().name("CCC").id(3).build());
        ReflectionTestUtils.setField(this.companyService, "dao", this.mockCompanyDao);
        Mockito.when(this.mockCompanyDao.findRange(Company.class, 0, 3, "CM", OrderCompanyEnum.NAME))
                .thenReturn(companies);
        assertEquals(
                this.companyService.getPage(Company.class, 1, 3, "CM", OrderCompanyEnum.NAME).size(), 3);
        assertEquals(
                this.companyService.getPage(Company.class, 1, 3, "CM", OrderCompanyEnum.NAME).get(0).getName(), "AAA");
        assertEquals(
                this.companyService.getPage(Company.class, 1, 3, "CM", OrderCompanyEnum.NAME).get(1).getName(), "BBB");
        assertEquals(
                this.companyService.getPage(Company.class, 1, 3, "CM", OrderCompanyEnum.NAME).get(2).getName(), "CCC");
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
    
    /*
     * Following tests are here to check wether we can remove companies
     * according to the client needs.
     * Here we shall test if:
     * 
     *  - The user tries to remove a company
     * tested with testRemove
     *  - The user tries to remove a company with a bad Id
     * tested with testRemoveWithBadId
     */ 
    @Test
    public void testRemove() {
        final Company company = new Company.CompanyBuilder().id(2).build();
        ReflectionTestUtils.setField(this.companyService, "dao", this.mockCompanyDao);
        Mockito.when(this.mockCompanyDao.delete(company)).thenReturn(true);
        assertTrue(this.companyService.remove(company));
    }

    @Test
    public void testRemoveWithBadId() {
        final Company company = new Company.CompanyBuilder().id(-1).build();
        Mockito.when(this.mockCompanyDao.delete(company)).thenReturn(false);
        assertFalse(this.companyService.remove(company));
    }

    /*
     * Following tests are here to check wether we can save computers
     * according to the client needs.
     * Here we shall test if:
     * 
     *  - The user tries to save a computer
     * tested with testSave
     */ 
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
