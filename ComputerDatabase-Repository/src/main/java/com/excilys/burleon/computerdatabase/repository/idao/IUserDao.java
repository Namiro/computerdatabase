package com.excilys.burleon.computerdatabase.repository.idao;

import java.util.Optional;

import com.excilys.burleon.computerdatabase.core.model.User;

public interface IUserDao extends IDao<User> {

    /**
     * Get a user by its username
     *
     * @param username
     *            The username
     * @return The user that match with the username
     */
    Optional<User> findByUsername(String username);
}
