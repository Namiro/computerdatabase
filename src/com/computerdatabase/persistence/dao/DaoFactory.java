package com.computerdatabase.persistence.dao;

import com.computerdatabase.persistence.exception.PersistenceException;
import com.computerdatabase.persistence.idao.IDao;
import com.computerdatabase.persistence.idao.IDaoFactory;
import com.computerdatabase.persistence.model.IEntity;

public enum DaoFactory implements IDaoFactory {

	INSTANCE;

	private enum CLAZZ {
		Computer, Company;
	}

	@Override
	public <E extends IEntity> IDao<E> getDao(final Class<E> type) {
		final CLAZZ z = Enum.valueOf(DaoFactory.CLAZZ.class, type.getSimpleName());
		switch (z) {
		case Computer:
			return (IDao<E>) ComputerDao.INSTANCE;
		case Company:
			return (IDao<E>) CompanyDao.INSTANCE;
		default:
			throw new PersistenceException("The DAO for the entity type asked, doesn't exist");
		}
	}

}
