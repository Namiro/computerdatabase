package com.excilys.burleon.computerdatabase.repository.dao;

import static org.junit.Assert.*;
import javax.transaction.Transactional;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.core.model.enumeration.AccessLevelEnum;
import com.excilys.burleon.computerdatabase.repository.spring.config.RepositoryConfig;
import com.excilys.burleon.computerdatabase.repository.util.Utility;

/**
 * @author Junior Burléon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(classes = RepositoryConfig.class) })
@ActiveProfiles("test")
public class UserDaoTest {
    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Autowired
    private UserDao userDao;

    
    /**
     * @throws java.lang.Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        Utility.loadAndResetDatabase();
    }
    
    /*
     * Following tests are here to check wether one
     * single user can be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The user wants to show an existing user
     * tested with findsAndReadsExistingUserWithAllParameters
     * tested with findAndReadsExistingUserWithOnlyMandatoryParameters
     * tested with findRange
     *  - The Id searched is not in the DB
     * tested with findsAndReadsUnexistingUser
     */

    @Test
    public void findsAndReadsExistingUserByIdWithOnlyMandatoryParameters()
            throws Exception {
        assertEquals(this.userDao.findById(User.class, 1).get().getId(), 1);
        assertEquals(this.userDao.findById(User.class, 1).get().getUsername(), ("root"));
        assertEquals(this.userDao.findById(User.class, 1).get().getAccessLevel(), AccessLevelEnum.ANONYMOUS);
        assertEquals(this.userDao.findById(User.class, 1).get().getPassword(), "");
        assertEquals(this.userDao.findByUsername("root").get().getId(), 1);
        assertEquals(this.userDao.findByUsername("root").get().getUsername(), ("root"));
        assertEquals(this.userDao.findByUsername("root").get().getAccessLevel(), AccessLevelEnum.ANONYMOUS);
        assertEquals(this.userDao.findByUsername("root").get().getPassword(), "");
    }

    @Test
    public void findsAndReadsExistingUserByIdWithAllParameters()
            throws Exception {
        assertEquals(this.userDao.findById(User.class, 2).get().getId(), 2);
        assertEquals(this.userDao.findById(User.class, 2).get().getUsername(), "notRoot");
        assertEquals(this.userDao.findById(User.class, 2).get().getAccessLevel(), AccessLevelEnum.ADMIN);
        assertEquals(this.userDao.findById(User.class, 2).get().getPassword(), "not");
        assertEquals(this.userDao.findByUsername("notRoot").get().getId(), 2);
        assertEquals(this.userDao.findByUsername("notRoot").get().getUsername(), "notRoot");
        assertEquals(this.userDao.findByUsername("notRoot").get().getAccessLevel(), AccessLevelEnum.ADMIN);
        assertEquals(this.userDao.findByUsername("notRoot").get().getPassword(), "not");
    }

    @Test
    public void findsAndReadsUnexistingUser() throws Exception {
        assertFalse(this.userDao.findById(User.class, 800).isPresent());
        assertFalse(this.userDao.findByUsername("Pierre").isPresent());
    }
  
    /*
     * Following tests are here to check wether we
     * can create a new user.
     * Here we shall test if:
     * 
     *  - The user wants to create a user with legal parameters
     * tested with createUser
     *  - The user tries to create a bad user
     * tested with createUserWithBadId
     */
    
    @Test
    @Transactional
    public void createUser() throws Exception {
        assertEquals(2, this.userDao.find(User.class).size());
        this.userDao.create(new User.UserBuilder().username("test").password("test").build());
        assertEquals(3, this.userDao.find(User.class).size());
    }
    
    @Test
    @Transactional
    public void createUserWithBadId() throws Exception {
        try {
            this.userDao.create(new User(2L, null, null));
            fail("Should not be able to persist existing id");
          }catch(javax.persistence.PersistenceException e){
            assert(e.getMessage().contains("detached entity passed to persist"));
          }
        assertEquals(2, this.userDao.find(User.class).size());
    }

    /*
     * Following tests are here to check wether several
     * users can't be found according to the client needs.
     * Here we shall test if:
     * 
     *  - The call to findRange is forbidden
     * tested with forbiddenFindRange
     */

    
    @Test
    public void forbiddenFindRange() throws Exception {
        try {
            this.userDao.findRange(User.class, 43, 10, null, null).size();
            fail("Should not be able to access findRange existing id");
          }catch(UnsupportedOperationException e){
            assert(e.getMessage().contains("wasn't implemented"));
          }
        assertEquals(2, this.userDao.find(User.class).size());
        
    }

     /*
     * Following tests are here to check wether we
     * can update a user.
     * Here we shall test if:
     * 
     *  - The user wants to update an existing user
     * tested with updateUser
     *  - The user tries to update a bad user
     * tested with updateUnexistingUser
     */
    
    @Test
    @Transactional
    public void updateUser() throws Exception {
        this.userDao.update(new User(2L, "test", null));
        assertEquals(2, this.userDao.findById(User.class, 2).get().getId());
        assertEquals("test", this.userDao.findById(User.class, 2).get().getUsername());
    }
    
    @Test
    @Transactional
    public void updateUnexistingUser() throws Exception {
        this.userDao.update(new User(2000L, "test", ""));
        assertFalse(this.userDao.findById(User.class, 2000).isPresent());
    }
   
    /*
     * Following test are here to check wether we
     * can delete a user.
     * Here we shall test if:
     * 
     *  - The user wants to delete anb existing user
     * tested with deleteUser
     * 
     *  - The user wants to delete an unexisting user
     * tested with deleteUnexistingUser
     */
    
    @Test
    @Transactional
    public void deleteUser() {
        assertTrue(this.userDao.delete(new User(1, null, null)));
        assertTrue(this.userDao.delete(this.userDao.findById(User.class, 2).get()));
    }
    
    @Test
    @Transactional
    public void deleteUnexistingUser() {
        assertTrue(this.userDao.delete(new User(4, "", "")));
    }
    
    /*
     * Following tests is here to check wether we
     * can get the user table name
     * Here we shall test if:
     * 
     *  - The user wants to get the user table name
     * tested with getUserTableName
     */
    @Test
    public void getUserTableName() throws Exception {
    assertEquals(this.userDao.getTableName(User.class),"user");
    }

}
