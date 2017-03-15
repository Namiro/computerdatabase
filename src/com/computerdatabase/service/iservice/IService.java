package com.computerdatabase.service.iservice;

import java.util.List;

import com.computerdatabase.model.entity.IEntity;
import com.computerdatabase.service.exception.ServiceException;

/**
 * Interface for the global methods for the services.
 *
 * @author Junior Burleon
 *
 * @param <E>
 */
public interface IService<E> {
	/**
	 * Check the value of each instance variable of entity.
	 *
	 * @param entity
	 * @throws com.sgs.service.exception.ServiceException
	 */
	public void checkDataEntity(E entity) throws ServiceException;

	/**
	 * Get all entities
	 *
	 * @return A entity with a type domaine class or Null if not exist
	 */
	public List<E> get();

	/**
	 * Get one entity
	 *
	 * @param id
	 * @return A entity with a type domaine class or Null if not exist
	 */
	public E get(long id);

	/**
	 * Return the records that are in a page
	 *
	 * @param pageNumber
	 *            Number of page
	 * @param recordsByPage
	 *            Number of record by page
	 * @return
	 */
	public List<E> getPage(final int pageNumber, final int recordsByPage);

	/**
	 * Remove the entity
	 *
	 * @param entity
	 * @return True if OK & False is not OK
	 */
	public boolean remove(final IEntity entity);

	/**
	 * Create or update the entity
	 *
	 * @param entity
	 * @return A entity created or updated with a type domaine class or Null if
	 *         not OK
	 * @throws com.sgs.service.exception.ServiceException
	 */
	public E save(IEntity entity) throws ServiceException;
}
