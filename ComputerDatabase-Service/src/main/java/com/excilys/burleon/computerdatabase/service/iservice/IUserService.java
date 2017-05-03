package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.excilys.burleon.computerdatabase.core.model.User;

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
     */
    Optional<User> getByUsername(String username);

    /**
     * To log in the user.
     *
     * @param user
     *            The user
     * @return The user that match the username
     * @throws AuthenticationException
     *             If something fail during the login
     */
    User login(User user) throws AuthenticationException;

    /**
     * To log out the user.
     *
     * @param user
     *            The user to log out
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    void logout(User user);

    /**
     * To register a user.
     *
     * @param user
     *            The user
     * @param passwordRepeated
     *            The passwordRepeated
     * @return The user registered
     * @throws AuthenticationException
     *             If there is a problem
     */
    User register(User user, String passwordRepeated) throws AuthenticationException;
}
