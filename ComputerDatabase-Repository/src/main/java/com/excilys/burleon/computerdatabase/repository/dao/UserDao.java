package com.excilys.burleon.computerdatabase.repository.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.core.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.repository.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.repository.idao.IUserDao;

/**
 * @author Junior Burleon
 *
 */
@Repository
public class UserDao extends ADao<User> implements IUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Override
    public Optional<User> findByUsername(final String username) {
        UserDao.LOGGER.trace("findByUsername : username : " + username);
        User user = null;

        try {
            final TypedQuery<User> query = this.entityManager
                    .createQuery("select user from User user where user.username like ?", User.class);
            query.setParameter(0, username);
            user = query.getSingleResult();
        } catch (final NonUniqueResultException e) {
            UserDao.LOGGER.warn(e.getMessage());
            throw new PersistenceException(e);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findRange(final Class<User> c, final int first, final int nbRecord, final String filterWord,
            final IOrderEnum<User> orderBy) {
        UserDao.LOGGER.error("The findRange(...) wasn't implemented for User entities");
        throw new UnsupportedOperationException("The findRange(...) wasn't implemented");
    }

}
