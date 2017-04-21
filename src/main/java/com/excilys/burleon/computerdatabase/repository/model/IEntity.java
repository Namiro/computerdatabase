package com.excilys.burleon.computerdatabase.repository.model;

/**
 *
 * @author Junior Burleon
 *
 */
public interface IEntity {

    /**
     * To get the id.
     *
     * @return The entity id
     */
    long getId();

    /**
     * To set the entity id.
     *
     * @param id
     *            The new value for the id
     */
    void setId(long id);

    @Override
    String toString();

}