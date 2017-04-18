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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.excilys.burleon.computerdatabase.persistence.dao.ComputerDao;
import com.excilys.burleon.computerdatabase.persistence.dao.DaoFactory;
import com.excilys.burleon.computerdatabase.persistence.dao.DatabaseConnection;
import com.excilys.burleon.computerdatabase.persistence.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.util.Utility;

/**
 * @author Junior Burléon
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ComputerDao.class, DaoFactory.class })
public class ComputerServiceTest {

    private static IComputerService computerService;

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        ComputerServiceTest.computerService = new ComputerService();
        Utility.loadAndResetDatabase();
    }

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
        this.exception.expect(ServiceException.class);
        ComputerServiceTest.computerService.checkDataEntity(computer);
    }

    @Test
    public void testCheckDataEntityNoName() {
        final Computer computer = new Computer.ComputerBuilder().name("").introduced(LocalDateTime.now())
                .discontinued(LocalDateTime.now()).build();
        this.exception.expect(ServiceException.class);
        ComputerServiceTest.computerService.checkDataEntity(computer);
    }

    @Test
    public void testGet() {
        final ArrayList<Computer> computers = new ArrayList<>();
        computers.add(new Computer.ComputerBuilder().name("AAA").id(1).build());
        computers.add(new Computer.ComputerBuilder().name("BBB").id(2).build());
        computers.add(new Computer.ComputerBuilder().name("CCC").id(3).build());

        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        PowerMockito.when(mockComputerDao.find(Computer.class)).thenReturn(computers);
        Assert.assertTrue(ComputerServiceTest.computerService.get(Computer.class).size() == 3);
    }

    @Test
    public void testGetPage() {
        final ArrayList<Computer> computers = new ArrayList<>();
        computers.add(new Computer.ComputerBuilder().name("AAA").id(1).build());
        computers.add(new Computer.ComputerBuilder().name("BBB").id(2).build());
        computers.add(new Computer.ComputerBuilder().name("CCC").id(3).build());

        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        PowerMockito.when(mockComputerDao.findRange(Computer.class, 0, 3, "CM", OrderComputerEnum.NAME))
                .thenReturn(computers);
        Assert.assertTrue(ComputerServiceTest.computerService
                .getPage(Computer.class, 1, 3, "CM", OrderComputerEnum.NAME).size() == 3);
    }

    @Test
    public void testGetWithBadId() {
        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        final int id = -1;
        PowerMockito.when(mockComputerDao.findById(Computer.class, id)).thenReturn(Optional.empty());

        Assert.assertFalse(ComputerServiceTest.computerService.get(Computer.class, id).isPresent());
    }

    @Test
    public void testGetWithId() {
        final LocalDateTime now = LocalDateTime.now();
        final int id = 2;
        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        PowerMockito.when(mockComputerDao.findById(Computer.class, id)).thenReturn(Optional.ofNullable(
                new Computer.ComputerBuilder().id(2).name("computer").discontinued(now).introduced(now).build()));

        final Optional<Computer> computer = ComputerServiceTest.computerService.get(Computer.class, id);
        Assert.assertTrue(computer.isPresent());
        Assert.assertTrue(computer.get().getId() == 2);
        Assert.assertTrue(computer.get().getName().equals("computer"));
        Assert.assertTrue(computer.get().getIntroduced().equals(now));
        Assert.assertTrue(computer.get().getDiscontinued().equals(now));
    }

    @Test
    public void testRemove() {
        final Computer computer = new Computer.ComputerBuilder().id(2).build();
        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        PowerMockito.when(mockComputerDao.delete(computer, DatabaseConnection.INSTANCE.getConnection()))
                .thenReturn(true);

        Assert.assertTrue(ComputerServiceTest.computerService.remove(computer));
    }

    @Test
    public void testRemoveWithBadId() {
        final Computer computer = new Computer.ComputerBuilder().id(-1).build();
        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        PowerMockito.when(mockComputerDao.delete(computer, DatabaseConnection.INSTANCE.getConnection()))
                .thenReturn(false);

        Assert.assertFalse(ComputerServiceTest.computerService.remove(computer));
    }

    @Test
    public void testSave() {
        final LocalDateTime nowIntro = LocalDateTime.now();
        final LocalDateTime nowDisco = LocalDateTime.now().plusMonths(2);
        final Computer computer = new Computer.ComputerBuilder().name("computer").build();
        final IComputerDao mockComputerDao = PowerMockito.mock(IComputerDao.class);
        final DaoFactory mockFactory = PowerMockito.mock(DaoFactory.class);
        Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
        PowerMockito.when(mockFactory.getDao(Computer.class)).thenReturn(mockComputerDao);
        PowerMockito.when(mockComputerDao.create(computer))
                .thenReturn(Optional.ofNullable(new Computer.ComputerBuilder().id(2).name("computer")
                        .introduced(nowIntro).discontinued(nowDisco).build()));
        PowerMockito.when(mockComputerDao.update(computer))
                .thenReturn(Optional.ofNullable(new Computer.ComputerBuilder().id(2).name("computeredited")
                        .introduced(nowIntro).discontinued(nowDisco.plusMonths(1)).build()));

        Optional<Computer> computerOpt = ComputerServiceTest.computerService.save(computer);
        Assert.assertTrue(computerOpt.isPresent());
        Assert.assertTrue(computerOpt.get().getId() == 2);
        Assert.assertTrue(computerOpt.get().getName().equals("computer"));
        Assert.assertTrue(computerOpt.get().getIntroduced().equals(nowIntro));
        Assert.assertTrue(computerOpt.get().getDiscontinued().equals(nowDisco));

        computer.setName("computeredited");
        computer.setId(2);
        computerOpt = ComputerServiceTest.computerService.save(computer);
        Assert.assertTrue(computerOpt.isPresent());
        Assert.assertTrue(computerOpt.get().getId() == 2);
        Assert.assertTrue(computerOpt.get().getName().equals("computeredited"));
        Assert.assertTrue(computerOpt.get().getIntroduced().equals(nowIntro));
        Assert.assertTrue(computerOpt.get().getDiscontinued().equals(nowDisco.plusMonths(1)));
    }

}
