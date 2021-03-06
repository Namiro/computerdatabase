/**
 *
 */
package com.excilys.burleon.computerdatabase.service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.AfterClass;
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

import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.repository.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooLongComputerNameException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortComputerNameException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.spring.config.ServiceConfig;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextHierarchy({ @ContextConfiguration(classes = ServiceConfig.class) })
public class ComputerServiceTest {

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Mock
    private IComputerDao mockComputerDao;

    @InjectMocks
    @Autowired
    private IComputerService computerService;

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
    public void testCheckDataBadId() {
        final Computer computer = new Computer.ComputerBuilder().id(-4).build();
        this.exception.expect(ServiceException.class);
        this.computerService.checkDataEntity(computer);
    }

    /*
     * Following tests are here to check computer datas according to the
     * client needs. Here we shall test if:
     *
     * - The user tries to enter a computer with a long name. tested with
     * testCheckDataEntityNameSoLong - The user tries to enter a computer with
     * no name tested with testCheckDataEntityNoName - The user tries to enter
     * a computer with a bad id tested with testCheckDataBadId - The user
     * tries to enter a computer with no entity tested with
     * testCheckDataNoEntity
     */
    @Test
    public void testCheckDataEntityNameSoLong() {
        final Computer computer = new Computer.ComputerBuilder()
                .name("TestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjd"
                        + "fgiofhgijfghdfighdfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljh"
                        + "iodfghfighdfgjdfuigjdfiogjdfgiofhgijfghdfighdfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfj"
                        + "hgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofhgijfghdfighdfgdfhTestNamedx"
                        + "klgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofh"
                        + "gijfghdfighdfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfgh"
                        + "fighdfgjdfuigjdfiogjdfgiofhgijfghdfighdfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfkl"
                        + "gdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofhgijfghdfighdfgdfhTestNamedxklgdfu"
                        + "igjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofhgijfgh"
                        + "dfighdfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdf"
                        + "gjdfuigjdfiogjdfgiofhgijfghdfighdfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiog"
                        + "dfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofhgijfghdfighdfgdfhTestNamedxklgdfuigjkgd"
                        + "fgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfuigjdfiogjdfgiofhgijfghdfigh"
                        + "dfgdfhTestNamedxklgdfuigjkgdfgdfhgdfhmgidfjhgdfklgdfiogdfigdfjgiodfljhiodfghfighdfgjdfu"
                        + "igjdfiogjdfgiofhgijfghdfighdfgdfh")
                .introduced(LocalDateTime.now()).discontinued(LocalDateTime.now()).build();
        this.exception.expect(TooLongComputerNameException.class);
        this.computerService.checkDataEntity(computer);
    }

    @Test
    public void testCheckDataEntityNoName() {
        final Computer computer = new Computer.ComputerBuilder().name("").introduced(LocalDateTime.now())
                .discontinued(LocalDateTime.now()).build();
        this.exception.expect(TooShortComputerNameException.class);
        this.computerService.checkDataEntity(computer);
    }

    @Test
    public void testCheckDataNoEntity() {
        final Computer computer = null;
        this.exception.expect(ServiceException.class);
        this.computerService.checkDataEntity(computer);
    }

    /*
     * Following tests are here to check wether we can get computers according
     * to the client needs. Here we shall test if:
     *
     * - The user tries to get several computers tested with testGet - The
     * user tries to get a single computer tested with testGetSingleComputer -
     * The user tries to get a computer page tested with testGetPage - The
     * user tries to get a computer with a bad id tested with testGetWithBadId
     * - The user tries to get a computer by id tested with testGetWithId
     */
    @Test
    public void testGet() {
        final ArrayList<Computer> computers = new ArrayList<>();
        computers.add(new Computer.ComputerBuilder().name("AAA").id(1).build());
        computers.add(new Computer.ComputerBuilder().name("BBB").id(2).build());
        computers.add(new Computer.ComputerBuilder().name("CCC").id(3).build());
        ReflectionTestUtils.setField(this.computerService, "dao", this.mockComputerDao);
        Mockito.when(this.mockComputerDao.find(Computer.class)).thenReturn(computers);
        Assert.assertEquals(this.computerService.get(Computer.class).size(), 3);
        Assert.assertEquals(this.computerService.get(Computer.class).get(0).getName(), "AAA");
        Assert.assertEquals(this.computerService.get(Computer.class).get(1).getName(), "BBB");
        Assert.assertEquals(this.computerService.get(Computer.class).get(2).getName(), "CCC");
    }

    @Test
    public void testGetPage() {
        final ArrayList<Computer> computers = new ArrayList<>();
        computers.add(new Computer.ComputerBuilder().name("AAA").id(1).build());
        computers.add(new Computer.ComputerBuilder().name("BBB").id(2).build());
        computers.add(new Computer.ComputerBuilder().name("CCC").id(3).build());
        ReflectionTestUtils.setField(this.computerService, "dao", this.mockComputerDao);
        Mockito.when(this.mockComputerDao.findRange(Computer.class, 0, 3, "CM", OrderComputerEnum.NAME))
                .thenReturn(computers);
        Assert.assertEquals(
                this.computerService.getPage(Computer.class, 1, 3, "CM", OrderComputerEnum.NAME).size(), 3);
        Assert.assertEquals(
                this.computerService.getPage(Computer.class, 1, 3, "CM", OrderComputerEnum.NAME).get(0).getName(),
                "AAA");
        Assert.assertEquals(
                this.computerService.getPage(Computer.class, 1, 3, "CM", OrderComputerEnum.NAME).get(1).getName(),
                "BBB");
        Assert.assertEquals(
                this.computerService.getPage(Computer.class, 1, 3, "CM", OrderComputerEnum.NAME).get(2).getName(),
                "CCC");
    }

    @Test
    public void testGetSingleComputer() {
        final Optional<
                Computer> computer = Optional.of(new Computer.ComputerBuilder().id(34).name("test").build());
        ReflectionTestUtils.setField(this.computerService, "dao", this.mockComputerDao);
        Mockito.when(this.mockComputerDao.findById(Computer.class, 34)).thenReturn(computer);
        Assert.assertEquals(this.computerService.get(Computer.class, 34).get().getId(), 34);
        Assert.assertEquals(this.computerService.get(Computer.class, 34).get().getName(), "test");
    }

    @Test
    public void testGetWithBadId() {
        final int id = -1;
        Mockito.when(this.mockComputerDao.findById(Computer.class, id)).thenReturn(Optional.empty());
        Assert.assertFalse(this.computerService.get(Computer.class, id).isPresent());
    }

    @Test
    public void testGetWithId() {
        final LocalDateTime now = LocalDateTime.now();
        final int id = 2;
        ReflectionTestUtils.setField(this.computerService, "dao", this.mockComputerDao);
        Mockito.when(this.mockComputerDao.findById(Computer.class, id)).thenReturn(Optional.ofNullable(
                new Computer.ComputerBuilder().id(2).name("computer").discontinued(now).introduced(now).build()));
        final Optional<Computer> computer = this.computerService.get(Computer.class, id);
        Assert.assertTrue(computer.isPresent());
        Assert.assertEquals(computer.get().getId(), 2);
        Assert.assertTrue(computer.get().getName().equals("computer"));
        Assert.assertTrue(computer.get().getIntroduced().equals(now));
        Assert.assertTrue(computer.get().getDiscontinued().equals(now));
    }

    /*
     * Following tests are here to check wether we can remove computers
     * according to the client needs. Here we shall test if:
     *
     * - The user tries to remove a computer tested with testRemove - The user
     * tries to remove a computer with a bad Id tested with
     * testRemoveWithBadId
     */
    @Test
    public void testRemove() {
        final Computer computer = new Computer.ComputerBuilder().id(2).build();
        ReflectionTestUtils.setField(this.computerService, "dao", this.mockComputerDao);
        Mockito.when(this.mockComputerDao.delete(computer)).thenReturn(true);
        Assert.assertTrue(this.computerService.remove(computer));
    }

    @Test
    public void testRemoveWithBadId() {
        final Computer computer = new Computer.ComputerBuilder().id(-1).build();
        Mockito.when(this.mockComputerDao.delete(computer)).thenReturn(false);
        Assert.assertFalse(this.computerService.remove(computer));
    }

    /*
     * Following tests are here to check wether we can save computers
     * according to the client needs. Here we shall test if:
     *
     * - The user tries to save a computer tested with testSave
     */
    @Test
    public void testSave() {
        final LocalDateTime nowIntro = LocalDateTime.now();
        final LocalDateTime nowDisco = LocalDateTime.now().plusMonths(2);
        final Computer computer = new Computer.ComputerBuilder().name("computer").build();
        ReflectionTestUtils.setField(this.computerService, "dao", this.mockComputerDao);
        Mockito.when(this.mockComputerDao.create(computer))
                .thenReturn(Optional.ofNullable(new Computer.ComputerBuilder().id(2).name("computer")
                        .introduced(nowIntro).discontinued(nowDisco).build()));
        Mockito.when(this.mockComputerDao.update(computer))
                .thenReturn(Optional.ofNullable(new Computer.ComputerBuilder().id(2).name("computeredited")
                        .introduced(nowIntro).discontinued(nowDisco.plusMonths(1)).build()));
        Optional<Computer> computerOpt = this.computerService.save(computer);
        Assert.assertTrue(computerOpt.isPresent());
        Assert.assertEquals(computerOpt.get().getId(), 2);
        Assert.assertTrue(computerOpt.get().getName().equals("computer"));
        Assert.assertTrue(computerOpt.get().getIntroduced().equals(nowIntro));
        Assert.assertTrue(computerOpt.get().getDiscontinued().equals(nowDisco));
        computer.setName("computeredited");
        computer.setId(2);
        computerOpt = this.computerService.save(computer);
        Assert.assertTrue(computerOpt.isPresent());
        Assert.assertEquals(computerOpt.get().getId(), 2);
        Assert.assertTrue(computerOpt.get().getName().equals("computeredited"));
        Assert.assertTrue(computerOpt.get().getIntroduced().equals(nowIntro));
        Assert.assertTrue(computerOpt.get().getDiscontinued().equals(nowDisco.plusMonths(1)));
    }

}
