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
 */
public interface IModelService<E extends IEntity> {

	/**
	 * Check the value of each instance variable of entity.
	 *
	 * @param entity
	 * @throws com.sgs.service.exception.ServiceException
	 */
	default public void checkDataEntity(final E entity) throws ServiceException {
		if (entity == null)
			throw new ServiceException("The object hasn't been initialized");
	}

	/**
	 * Get all entities
	 *
	 * @return A entity with a type domain class or Null if not exist
	 */
	default public List<E> get(final Class<E> T) {
		return DaoFactory.INSTANCE.getDao(T).find(T);
	}

	/**
	 * Get one entity
	 *
	 * @param id
	 * @return A entity with a type domain class or Null if not exist
	 */
	default public E get(final Class<E> T, final long id) {
		if (id > 0)
			return DaoFactory.INSTANCE.getDao(T).find(T, id);
		else
			return null;
	}

	/**
	 *
	 * @param entityType
	 * @param number
	 * @param recordsByPage
	 * @return
	 */
	default public List<E> getPage(final Class<E> T, final int pageNumber, final int recordsByPage) {
		return DaoFactory.INSTANCE.getDao(T).findRange(T, (pageNumber - 1) * recordsByPage, recordsByPage);
	}

	/**
	 * Method to get the table name. The table name is the name of the entity
	 * class.
	 *
	 *
	 * @param clazz
	 *            The entity class
	 * @return The table Name
	 */
	default String getTableName(final Class<? extends IEntity> clazz) {

		return clazz.getSimpleName().toLowerCase();
	}

	/**
	 * Remove the entity
	 *
	 * @param entity
	 * @return True if OK & False is not OK
	 */
	default public boolean remove(final E entity) {
		return ((IDao<E>) DaoFactory.INSTANCE.getDao(entity.getClass())).delete(entity);
	}

	/**
	 * Create or update the entity
	 *
	 * @param entity
	 * @return A entity created or updated with a type domaine class or Null if
	 *         not OK
	 * @throws com.sgs.service.exception.ServiceException
	 */
	default public E save(final E entity) throws ServiceException {

		this.checkDataEntity(entity);

		if (entity.getId() != 0)
			return ((IDao<E>) DaoFactory.INSTANCE.getDao(entity.getClass())).update(entity);
		else
			return ((IDao<E>) DaoFactory.INSTANCE.getDao(entity.getClass())).create(entity);
	}

}
