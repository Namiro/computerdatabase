package com.excilys.burleon.computerdatabase.repository.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.core.model.IEntity;
import com.excilys.burleon.computerdatabase.repository.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.repository.idao.IDao;

public abstract class ADao<E extends IEntity> implements IDao<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ADao.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<E> create(final E entity) {
        ADao.LOGGER.trace("create : entity : " + entity);
        this.entityManager.persist(entity);
        this.entityManager.flush();
        this.entityManager.refresh(entity);
        if (entity.getId() > 0) {
            return Optional.ofNullable(entity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(final Class<E> c, final long entityId) {
        ADao.LOGGER.trace("delete : c : " + c + "\tentityId : " + entityId);
        final Optional<E> entity = this.findById(c, entityId);
        if (entity.isPresent()) {
            this.entityManager.remove(entity);
            return true;
        } else {
            ADao.LOGGER.warn("The entity (Type : " + c + " ) with the id : " + entityId + " doesn't exist");
            throw new PersistenceException(
                    "The entity (Type : " + c + " ) with the id : " + entityId + " doesn't exist");
        }
    }

    @Override
    public boolean delete(final E entity) {
        ADao.LOGGER.trace("delete : entity : " + entity);
        this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));
        return true;
    }

    @Override
    public List<E> find(final Class<E> c) {
        ADao.LOGGER.trace("find : entity : " + c);
        return this.entityManager.createQuery("from " + c.getSimpleName(), c).getResultList();
    }

    @Override
    public Optional<E> findById(final Class<E> c, final long id) {
        ADao.LOGGER.trace("findById : c : " + c + "\tid : " + id);
        return Optional.ofNullable(this.entityManager.find(c, id));
    }

    @Override
    public long getNbRecords(final Class<E> c) {
        ADao.LOGGER.trace("getNbRecords : class : " + c);
        final CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(c)));
        return this.entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public long getNbRecordsByName(final Class<E> c, final String filterWord) {
        ADao.LOGGER.trace("getNbRecords : c : " + c + "\tfilterWord : " + filterWord);
        final Query query = this.entityManager
                .createQuery("select count(*) from " + c.getSimpleName() + " e where e.name like ?");
        query.setParameter(0, filterWord + "%");
        return (long) query.getSingleResult();
    }

    @Override
    public Optional<E> update(final E entity) {
        ADao.LOGGER.trace("update : entity : " + entity);
        return Optional.of(this.entityManager.merge(entity));
    }
}
