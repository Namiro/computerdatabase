package com.computerdatabase.persistence.idao;

import com.computerdatabase.persistence.model.IEntity;

public interface IDaoFactory {

	/**
	 * To get a DAO to manage an entity
	 *
	 * @param type
	 * @return
	 */
	<E extends IEntity> IDao<E> getDao(Class<E> type);
}
