package com.excilys.burleon.computerdatabase.service.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.core.model.enumeration.AccessLevelEnum;
import com.excilys.burleon.computerdatabase.repository.idao.IUserDao;
import com.excilys.burleon.computerdatabase.service.exception.DataValidationException;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IUserService;

/**
 *
 * @author Junior Burleon
 *
 */
@Service
public class UserService extends AModelService<User> implements IUserService {

    static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void checkDataEntity(final User entity) throws ServiceException {
        IUserService.super.checkDataEntity(entity);
        if (entity.getUsername().length() < 3) {
            throw new DataValidationException("The username must be longer then 3 characters");
        }
        if (entity.getPassword().length() < 3) {
            throw new DataValidationException("The password must be longer then 3 characters");
        }
    }

    private boolean exist(final User user) {

        if (((IUserDao) this.dao).findByUsername(user.getUsername()).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> getByUsername(final String username) {
        UserService.LOGGER.trace("getByUsername : username : " + username);
        return ((IUserDao) this.dao).findByUsername(username);
    }

    @Override
    public User login(final User user) throws AuthenticationException {
        UserService.LOGGER.trace("login : user : " + user);
        final Optional<User> userOpt = ((IUserDao) this.dao).findByUsername(user.getUsername());
        if (userOpt.isPresent()) {
            final User _user = userOpt.get();
            if (_user.getPassword().equals(this.passwordEncoder.encode(user.getPassword()))) {
                return user;
            } else {
                UserService.LOGGER.warn("Password inccorect fior the username : " + user.getUsername());
                throw new AuthenticationException(
                        "This username doesn't match with the password given (Password incorrect");
            }

        } else {
            UserService.LOGGER.warn("No user with the username: " + user.getUsername());
            throw new AuthenticationException("There is no user with this username (Username incorrect)");
        }
    }

    @Override
    public void logout(final User user) {
        UserService.LOGGER.trace("logout : user : " + user);
    }

    @Override
    public User register(final User user, final String passwordRepeated) throws AuthenticationException {
        if (this.exist(user)) {
            throw new DataValidationException("This user already exist");
        }
        if (!passwordRepeated.equals(user.getPassword())) {
            throw new DataValidationException("The password repeated isn't identique");
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setAccessLevel(AccessLevelEnum.USER);
        final Optional<User> userOpt = this.save(user);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new ServiceException("Impossible to register the new user");
        }
    }

}
