package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.List;

import com.excilys.burleon.computerdatabase.persistence.dao.DaoFactory;
import com.excilys.burleon.computerdatabase.persistence.idao.IDao;
import com.excilys.burleon.computerdatabase.persistence.model.IEntity;
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

    /**
     * Check the value of each instance variable of entity.
     *
     * @param entity
     *            The entity to check
     * @throws ServiceException
     *             If the entity is null
     */
    default void checkDataEntity(final E entity) throws ServiceException {
        if (entity == null) {
            throw new ServiceException("The object hasn't been initialized");
        }
    }

    /**
     * Get all entities.
     *
     * @param entityType
     *            The entity type
     * @return A entity with a type domain class or Null if not exist
     */
    default List<E> get(final Class<E> entityType) {
        return DaoFactory.INSTANCE.getDao(entityType).find(entityType);
    }

    /**
     * Get one entity.
     *
     * @param entityType
     *            The entity type
     * @param id
     *            The entity id
     * @return A entity with a type domain class or Null if not exist
     */
    default E get(final Class<E> entityType, final long id) {
        if (id > 0) {
            return DaoFactory.INSTANCE.getDao(entityType).find(entityType, id);
        } else {
            return null;
        }
    }

    /**
     * To get a page.
     *
     * @param entityType
     *            The entity type
     * @param pageNumber
     *            The number of page
     * @param recordsByPage
     *            The number of record on one page
     * @return A list of entity that match with the page asked.
     */
    default List<E> getPage(final Class<E> entityType, final int pageNumber, final int recordsByPage) {
        return DaoFactory.INSTANCE.getDao(entityType).findRange(entityType, (pageNumber - 1) * recordsByPage,
                recordsByPage);
    }

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
     * @return the total number of records
     */
    default int getTotalRecords(final Class<E> entityType) {
        return DaoFactory.INSTANCE.getDao(entityType).getNbRecords(entityType);
    }

    /**
     * Remove the entity.
     *
     * @param entity
     *            The entity to remove
     * @return True if OK & False is not OK
     */
    default boolean remove(final E entity) {
        return ((IDao<E>) DaoFactory.INSTANCE.getDao(entity.getClass())).delete(entity);
    }

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
    default E save(final E entity) throws ServiceException {

        this.checkDataEntity(entity);

        if (entity.getId() != 0) {
            return ((IDao<E>) DaoFactory.INSTANCE.getDao(entity.getClass())).update(entity);
        } else {
            return ((IDao<E>) DaoFactory.INSTANCE.getDao(entity.getClass())).create(entity);
        }
    }

}
