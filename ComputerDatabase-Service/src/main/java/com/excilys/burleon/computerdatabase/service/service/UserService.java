package com.excilys.burleon.computerdatabase.service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.core.model.enumeration.AccessLevelEnum;
import com.excilys.burleon.computerdatabase.repository.idao.IUserDao;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.exception.authentication.InvalidPasswordException;
import com.excilys.burleon.computerdatabase.service.exception.authentication.UsernameAlreadyExistException;
import com.excilys.burleon.computerdatabase.service.exception.authentication.UsernameNotFoundException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortPasswordException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortUsernameException;
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
    public void checkDataEntity(final User entity) throws TooShortPasswordException, TooShortUsernameException {
        IUserService.super.checkDataEntity(entity);
        if (entity.getUsername().length() < 3) {
            throw new TooShortUsernameException("The username must be longer then 3 characters");
        }
        if (entity.getPassword().length() < 3) {
            throw new TooShortPasswordException("The password must be longer then 3 characters");
        }
    }

    @Transactional(readOnly = true)
    private boolean exist(final User user) {
        if (((IUserDao) this.dao).findByUsername(user.getUsername()).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByUsername(final String username) throws UsernameNotFoundException {
        UserService.LOGGER.trace("getByUsername : username : " + username);
        final Optional<User> userOpt = ((IUserDao) this.dao).findByUsername(username);
        if (userOpt.isPresent()) {
            return userOpt;
        } else {
            throw new UsernameNotFoundException("There is no user with the username : " + username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserService.LOGGER.trace("loadUserByUsername : username : " + username);
        final Optional<User> userOpt = this.getByUsername(username);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new UsernameNotFoundException("There is no user with the username : " + username);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User login(final User user) throws InvalidPasswordException, UsernameNotFoundException {
        UserService.LOGGER.trace("login : user : " + user);
        final Optional<User> userOpt = ((IUserDao) this.dao).findByUsername(user.getUsername());
        if (userOpt.isPresent()) {
            final User _user = userOpt.get();
            if (this.passwordEncoder.matches(user.getPassword(), _user.getPassword())) {
                return user;
            } else {
                UserService.LOGGER.warn("Password inccorect fior the username : " + user.getUsername());
                throw new InvalidPasswordException("Password incorrect");
            }

        } else {
            UserService.LOGGER.warn("No user with the username: " + user.getUsername());
            throw new UsernameNotFoundException("Username incorrect");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void logout(final User user) {
        UserService.LOGGER.trace("logout : user : " + user);
    }

    @Override
    @Transactional
    public User register(final User user, final String passwordRepeated)
            throws UsernameAlreadyExistException, InvalidPasswordException {
        if (this.exist(user)) {
            throw new UsernameAlreadyExistException("This user already exist");
        }
        if (!passwordRepeated.equals(user.getPassword())) {
            throw new InvalidPasswordException("The password repeated isn't identique");
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
