package com.computerdatabase.service.service;

import java.util.List;

import com.computerdatabase.model.entity.IEntity;
import com.computerdatabase.model.idao.IDao;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.IService;

/**
 *
 * @author Junior Burleon
 *
 * @param <E>
 */
public abstract class Service<E> implements IService<E> {
	protected IDao<E> dao = null;

	@Override
	public List<E> get() {
		return this.dao.find();
	}

	@Override
	public E get(final int id) {
		return this.dao.find(id);
	}

	@Override
	public List<E> getPage(final int pageNumber, final int recordsByPage) {
		return this.dao.findRange((pageNumber - 1) * recordsByPage, pageNumber * recordsByPage);
	}

	@Override
	public boolean remove(final IEntity entity) {
		return this.dao.delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E save(final IEntity entity) throws ServiceException {

		this.checkDataEntity((E) entity);

		if (entity.getId() != 0)
			return (E) this.dao.update(entity);
		else
			return this.dao.create(entity);

	}
}
