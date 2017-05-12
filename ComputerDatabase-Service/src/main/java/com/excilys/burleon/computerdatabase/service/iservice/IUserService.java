package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.service.exception.authentication.InvalidPasswordException;
import com.excilys.burleon.computerdatabase.service.exception.authentication.UsernameAlreadyExistException;
import com.excilys.burleon.computerdatabase.service.exception.authentication.UsernameNotFoundException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortPasswordException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortUsernameException;

/**
 *
 * @author Junior Burleon
 *
 */
public interface IUserService extends IModelService<User>, UserDetailsService {

    /**
     * Get a user by its username.
     *
     * @param username
     *            The username
     * @return The user that match the username
     *
     * @throws UsernameNotFoundException
     */
    Optional<User> getByUsername(String username) throws UsernameNotFoundException;

    /**
     * To log in the user.
     *
     * @param user
     *            The user
     * @return The user that match the username
     *
     * @throws InvalidPasswordException
     * @throws UsernameNotFoundException
     */
    User login(User user) throws InvalidPasswordException, UsernameNotFoundException;

    /**
     * To log out the user.
     *
     * @param user
     *            The user to log out
     */
    void logout(User user);

    /**
     * To register a user.
     *
     * @param user
     *            The user
     * @param passwordRepeated
     *            The passwordRepeated
     * @return The user registered
     *
     * @throws UsernameAlreadyExistException
     * @throws InvalidPasswordException
     */
    User register(User user, String passwordRepeated)
            throws UsernameAlreadyExistException, InvalidPasswordException;

    @Override
    public Optional<User> save(User entity) throws TooShortPasswordException, TooShortUsernameException;
}
