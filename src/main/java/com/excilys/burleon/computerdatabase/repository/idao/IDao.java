package com.excilys.burleon.computerdatabase.repository.idao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.repository.model.IEntity;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.IOrderEnum;

public interface IDao<E extends IEntity> {

    Logger LOGGER = LoggerFactory.getLogger("IDAO");

    /**
     * Method to insert an element in the table of specified entity.
     *
     * @param entity
     *            Object of type E
     * @return Entity
     */
    Optional<E> create(E entity);

    /**
     * Method to delete an element in the table of specified entity.
     *
     * @param entity
     *            Object to delete
     * 
     * @return boolean Success -> True else false.
     */
    boolean delete(final E entity);

    /**
     * Method to get all element from the database.
     *
     * @param c
     *            The type of entity
     *
     * @return A list with all records. Return null if there is a problem.
     *         Else a list (This list can be empty if there is nothing found
     *         by the request).
     */
    List<E> find(Class<E> c);

    /**
     * Method to research by id an element in the table of specified entity.
     *
     * @param c
     *            The type of entity
     * @param id
     *            The id of the entity you want get
     * @return E Object of type E. Null if nothing found.
     */
    Optional<E> findById(Class<E> c, long id);

    /**
     * Method to get all element between in a limit from the database The
     * limit is based on the number of records found with a simple select.
     *
     * @param c
     *            The type of entity
     * @param first
     *            The number of the first element
     * @param nbRecord
     *            The number of record you want from the first
     * @param filterWord
     *            The word that will be used to filter the results
     * @param orderBy
     *            The field on which one the result will be sort
     * @return A list with the record comprise between first and last.
     */
    List<E> findRange(Class<E> c, int first, int nbRecord, String filterWord, IOrderEnum<E> orderBy);

    /**
     *
     * @param c
     *            The type of entity
     * @return The number of records for this entity
     */
    long getNbRecords(final Class<E> c);

    /**
     *
     * @param c
     *            The type of entity
     * @param filterWord
     *            To filter the records and get only these that match with the
     *            filter word
     * @return The number of records for this entity and that match with the
     *         filter word
     */
    long getNbRecords(Class<E> c, String filterWord);

    /**
     * Method to get the table name. The table name is the name of the entity
     * class.
     *
     * @param clazz
     *            The entity class
     * @return The table Name
     */
    default String getTableName(final Class<? extends IEntity> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * Method to update an element in the table of specified entity.
     *
     * @param entity
     *            Object of type E to update
     * @return Entity
     */
    Optional<E> update(E entity);

}