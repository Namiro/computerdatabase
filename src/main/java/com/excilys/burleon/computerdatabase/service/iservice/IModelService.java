package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.model.IEntity;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;

/**
 * Interface for the global methods for the services.
 *
 * @author Junior Burleon
 *
 * @param <E>
 *            The type of service you want.
 */
public interface IModelService<E extends IEntity> extends IService {

    Logger LOGGER = LoggerFactory.getLogger("IModelService");

    /**
     * Check the value of each instance variable of entity.
     *
     * @param entity
     *            The entity to check
     * @throws ServiceException
     *             If the entity is null
     */
    default void checkDataEntity(final E entity) throws ServiceException {
        IModelService.LOGGER.trace("checkDataEntity : Entity : " + entity);
        if (entity == null) {
            throw new ServiceException("The object hasn't been initialized");
        }
        if (entity.getId() < 0) {
            throw new ServiceException("The entity can't have a negative id");
        }
    }

    /**
     * Get all entities.
     *
     * @param entityType
     *            The entity type
     * @return A entity with a type domain class or Null if not exist
     */
    List<E> get(final Class<E> entityType);

    /**
     * Get one entity.
     *
     * @param entityType
     *            The entity type
     * @param id
     *            The entity id
     * @return A entity with a type domain class or Null if not exist
     */
    Optional<E> get(final Class<E> entityType, final long id);

    /**
     * To get a page.
     *
     * @param entityType
     *            The entity type
     * @param pageNumber
     *            The number of page
     * @param recordsByPage
     *            The number of record on one page
     * @param filterWord
     *            The word that will be used to filter the result
     * @param orderBy
     *            The field on which one the result will be sort
     * @return A list of entity that match with the page asked and the filter
     *         word.
     */
    List<E> getPage(final Class<E> entityType, final int pageNumber, final int recordsByPage,
            final String filterWord, final IOrderEnum<E> orderBy);

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
     * Get the total number of records.
     *
     * @param entityType
     *            The entity type
     * @param filterWord
     *            The word that will be used to filter the result
     * @return the total number of records that match with the filter word
     */
    long getTotalRecords(final Class<E> entityType, String filterWord);

    /**
     * Remove the entity.
     *
     * @param entity
     *            The entity to remove
     * @return True if OK & False is not OK
     */
    boolean remove(final E entity);

    /**
     * Create or update the entity.
     *
     * @param entity
     *            The entity to save
     * @return A entity created or updated with a type domain class or Null if
     *         not OK
     * @throws ServiceException
     *             If the check data fail
     */
    Optional<E> save(final E entity) throws ServiceException;
}
