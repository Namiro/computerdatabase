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

import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.repository.idao.IUserDao;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortUsernameException;
import com.excilys.burleon.computerdatabase.service.iservice.IUserService;
import com.excilys.burleon.computerdatabase.service.spring.config.ServiceConfig;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextHierarchy({ @ContextConfiguration(classes = ServiceConfig.class) })
public class UserServiceTest {

    @Mock
    private IUserDao mockUserDao;

    @InjectMocks
    @Autowired
    private IUserService userService;

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
        final User user = new User.UserBuilder().id(-4).build();
        this.exception.expect(ServiceException.class);
        this.userService.checkDataEntity(user);
    }

    /*
     * Following tests are here to check computer datas according to the
     * client needs. Here we shall test if:
     *
     * - The user tries to enter a user a long name. tested with
     * testCheckDataEntityNameSoLong - The user tries to enter a user with no
     * name tested with testCheckDataEntityNoName - The user tries to enter a
     * user with a bad id tested with testCheckDataBadId - The user tries to
     * enter a user with no entity tested with testCheckDataNoEntity
     */
    @Test
    public void testCheckDataEntityNameSoLong() {
        final User user = null;
        this.exception.expect(ServiceException.class);
        this.userService.checkDataEntity(user);
    }

    @Test
    public void testCheckDataEntityNoName() throws ServiceException {
        final User user = new User.UserBuilder().username("").build();
        this.exception.expect(TooShortUsernameException.class);
        this.userService.checkDataEntity(user);
    }

    public void testCheckDataNoEntity() {
        final User user = null;
        this.exception.expect(ServiceException.class);
        this.userService.checkDataEntity(user);
    }

    /*
     * Following tests are here to check wether we can get users according to
     * the client needs. Here we shall test if:
     *
     * - The user tries to get several users tested with testGet - The user
     * tries to get a single users tested with testGetSingleUser - The user
     * tries to get an user page tested with testGetPage - The user tries to
     * get an user with a bad id tested with testGetWithBadId - The user tries
     * to get an user by id tested with testGetWithId
     */

    @Test
    public void testGet() {
        final ArrayList<User> users = new ArrayList<>();
        users.add(new User.UserBuilder().username("AAA").id(1).build());
        users.add(new User.UserBuilder().username("BBB").id(2).build());
        users.add(new User.UserBuilder().username("CCC").id(3).build());

        ReflectionTestUtils.setField(this.userService, "dao", this.mockUserDao);
        Mockito.when(this.mockUserDao.find(User.class)).thenReturn(users);
        Assert.assertTrue(this.userService.get(User.class).size() == 3);
    }

    @Test
    public void testGetSingleUser() {
        final Optional<
                User> user = Optional.of(new User.UserBuilder().id(34).username("test").password("").build());
        ReflectionTestUtils.setField(this.userService, "dao", this.mockUserDao);
        Mockito.when(this.mockUserDao.findById(User.class, 34)).thenReturn(user);
        Assert.assertEquals(this.userService.get(User.class, 34).get().getId(), 34);
        Assert.assertEquals(this.userService.get(User.class, 34).get().getUsername(), "test");
    }

    @Test
    public void testGetWithBadId() {
        final int id = -1;
        Mockito.when(this.mockUserDao.findById(User.class, id)).thenReturn(Optional.empty());

        Assert.assertFalse(this.userService.get(User.class, id).isPresent());
    }

    @Test
    public void testGetWithId() {
        final int id = 2;
        ReflectionTestUtils.setField(this.userService, "dao", this.mockUserDao);
        Mockito.when(this.mockUserDao.findById(User.class, id))
                .thenReturn(Optional.ofNullable(new User.UserBuilder().id(2).username("user").build()));

        final Optional<User> user = this.userService.get(User.class, id);
        Assert.assertTrue(user.isPresent());
        Assert.assertTrue(user.get().getId() == 2);
        Assert.assertTrue(user.get().getUsername().equals("user"));
    }

    @Test
    public void testSave() {
        final User user = new User.UserBuilder().username("user").password("test").build();
        ReflectionTestUtils.setField(this.userService, "dao", this.mockUserDao);

        Mockito.when(this.mockUserDao.create(user))
                .thenReturn(Optional.of(new User.UserBuilder().id(2).username("user").password("test").build()));
        Mockito.when(this.mockUserDao.update(user)).thenReturn(
                Optional.of(new User.UserBuilder().id(2).username("useredited").password("test").build()));

        Optional<User> userOpt = this.userService.save(user);
        Assert.assertTrue(userOpt.isPresent());
        Assert.assertTrue(userOpt.get().getId() == 2);
        Assert.assertTrue(userOpt.get().getUsername().equals("user"));

        user.setUsername("useredited");
        user.setId(2);
        userOpt = this.userService.save(user);
        Assert.assertTrue(userOpt.isPresent());
        Assert.assertTrue(userOpt.get().getId() == 2);
        Assert.assertTrue(userOpt.get().getUsername().equals("useredited"));
    }

}
