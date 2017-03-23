/**
 *
 */
package com.excilys.burleon.computerdatabase.service.service;

import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.tool.Utility;

/**
 * @author Junior Burléon
 *
 */
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
        Assert.assertTrue(ComputerServiceTest.computerService.get(Computer.class).size() == 200);
    }

    @Test
    public void testGetWithBadId() {
        final int id = -1;
        Assert.assertFalse(ComputerServiceTest.computerService.get(Computer.class, id).isPresent());
    }

    @Test
    public void testGetWithId() {
        final int id = 2;
        Assert.assertTrue(ComputerServiceTest.computerService.get(Computer.class, id).get().getId() == 2);
        Assert.assertTrue(
                ComputerServiceTest.computerService.get(Computer.class, id).get().getName().equals("CM-2a"));
    }

    @Test
    public void testRemove() {
        final Computer entity = new Computer();
        entity.setId(8);
        Assert.assertTrue(ComputerServiceTest.computerService.remove(entity));
    }

    @Test
    public void testRemoveWithBadId() {
        final Computer entity = new Computer();
        entity.setId(-1);
        Assert.assertFalse(ComputerServiceTest.computerService.remove(entity));
    }

    @Test
    public void testSave() {
        final LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        Computer computer = new Computer.ComputerBuilder().name("TestServiceComputer")
                .introduced(LocalDateTime.now()).discontinued(LocalDateTime.now()).build();
        computer = ComputerServiceTest.computerService.save(computer).get();
        Assert.assertTrue(computer != null);
        Assert.assertTrue(computer.getName().equals("TestServiceComputer"));
        computer = ComputerServiceTest.computerService.get(Computer.class, computer.getId()).get();
        Assert.assertTrue(computer.getName().equals("TestServiceComputer"));
        Assert.assertTrue(computer.getId() != 0);
        final long idOk = computer.getId();
        Assert.assertTrue(computer.getCompany().getId() == 0);
        Assert.assertTrue(computer.getDiscontinued().equals(localDateTime));
        Assert.assertTrue(computer.getIntroduced().equals(localDateTime));
        computer.setName("TestServiceComputerUpdated");
        computer.setDiscontinued(localDateTime.plusYears(2));
        computer.setIntroduced(localDateTime.plusYears(1));
        computer.setCompany(new Company.CompanyBuilder().id(2).name("Truurur").build());
        computer = ComputerServiceTest.computerService.save(computer).get();
        Assert.assertTrue(computer != null);
        Assert.assertTrue(computer.getName().equals("TestServiceComputerUpdated"));
        computer = ComputerServiceTest.computerService.get(Computer.class, computer.getId()).get();
        Assert.assertTrue(computer.getName().equals("TestServiceComputerUpdated"));
        Assert.assertTrue(computer.getId() == idOk);
        Assert.assertTrue(computer.getCompany().getId() == 2);
        Assert.assertTrue(computer.getDiscontinued().equals(localDateTime.plusYears(2)));
        Assert.assertTrue(computer.getIntroduced().equals(localDateTime.plusYears(1)));
    }

}
