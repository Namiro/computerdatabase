package com.excilys.burleon.computerdatabase.persistence.idao;

import com.excilys.burleon.computerdatabase.persistence.model.IEntity;

public interface IDaoFactory {

    /**
     * To get a DAO to manage an entity.
     *
     * @param type
     *            The type of entity. In function of this type you will
     *            receive the correct DAO
     * @param <E>
     *            The type of entity
     * @return A DAO to manage the persistence of a type of entity
     */
    <E extends IEntity> IDao<E> getDao(Class<E> type);
}
