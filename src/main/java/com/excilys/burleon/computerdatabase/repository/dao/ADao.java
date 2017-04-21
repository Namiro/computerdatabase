package com.excilys.burleon.computerdatabase.repository.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.burleon.computerdatabase.repository.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.repository.idao.IDao;
import com.excilys.burleon.computerdatabase.repository.model.IEntity;
import com.zaxxer.hikari.HikariDataSource;

public abstract class ADao<E extends IEntity> implements IDao<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ADao.class);

    protected JdbcTemplate jdbcTemplate;

    @Override
    public boolean delete(final E entity) {
        ADao.LOGGER.trace("delete : entity : " + entity);

        boolean success = true;
        try {
            this.jdbcTemplate.update("DELETE FROM " + this.getTableName(entity.getClass()) + " WHERE id = ?",
                    entity.getId());
            success = true;
        } catch (final DataAccessException e) {
            success = false;
            ADao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return success;
    }

    @Override
    public long getNbRecords(final Class<E> c) {
        ADao.LOGGER.trace("getNbRecords : class : " + c);
        long nbTotal = 0;
        try {
            nbTotal = this.jdbcTemplate.queryForObject("SELECT count(*) as total FROM " + this.getTableName(c),
                    Long.class);
        } catch (final DataAccessException e) {
            ADao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return nbTotal;
    }

    @Autowired
    public void setDataSource(final HikariDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
