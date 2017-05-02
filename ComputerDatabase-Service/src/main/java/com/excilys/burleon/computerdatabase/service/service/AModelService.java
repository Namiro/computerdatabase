package com.excilys.burleon.computerdatabase.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.burleon.computerdatabase.core.model.IEntity;
import com.excilys.burleon.computerdatabase.core.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.repository.idao.IDao;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;

public abstract class AModelService<E extends IEntity> implements IModelService<E> {

    @Autowired
    protected IDao<E> dao;

    @Override
    @Transactional(readOnly = true)
    public List<E> get(final Class<E> entityType) {
        IModelService.LOGGER.trace("get : entityType : " + entityType);
        return this.dao.find(entityType);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> get(final Class<E> entityType, final long id) {
        IModelService.LOGGER.trace("get : entityType : " + entityType + "\tid : " + id);
        if (id > 0) {
            IModelService.LOGGER.trace("get : entityType : " + entityType + "\tid : " + id + " FIND OK");
            return this.dao.findById(entityType, id);
        } else {
            IModelService.LOGGER.trace("get : entityType : " + entityType + "\tid : " + id + " FIND KO");
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> getPage(final Class<E> entityType, final int pageNumber, final int recordsByPage,
            final String filterWord, final IOrderEnum<E> orderBy) {
        IModelService.LOGGER
                .trace("getPage : entityType " + entityType + "\tpageNumber : " + pageNumber + "\trecordsByPage : "
                        + recordsByPage + "\torderBy : " + orderBy + "\tfilterWord : " + filterWord);
        final List<E> list = this.dao.findRange(entityType, (pageNumber - 1) * recordsByPage, recordsByPage,
                filterWord, orderBy);

        if (list == null || list.isEmpty()) {
            IModelService.LOGGER.error(
                    "getPage : entityType " + entityType + "\tpageNumber : " + pageNumber + "\trecordsByPage : "
                            + recordsByPage + "\torderBy : " + orderBy + "\tfilterWord : " + filterWord);
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalRecords(final Class<E> entityType, String filterWord) {
        IModelService.LOGGER
                .trace("getTotalRecords : entityType : " + entityType + "\tfilterWord : " + filterWord);
        if (filterWord == null) {
            filterWord = "";
        }
        return this.dao.getNbRecordsByName(entityType, filterWord);

    }

    @Override
    @Transactional
    public boolean remove(final E entity) {
        IModelService.LOGGER.trace("remove : entity : " + entity);
        if (entity != null && entity.getId() > 0) {
            return this.dao.delete(entity);
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Optional<E> save(final E entity) throws ServiceException {
        IModelService.LOGGER.trace("save : entity : " + entity);

        this.checkDataEntity(entity);

        if (entity.getId() != 0) {
            return this.dao.update(entity);
        } else {
            return this.dao.create(entity);
        }
    }
}
