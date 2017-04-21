package com.excilys.burleon.computerdatabase.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.excilys.burleon.computerdatabase.repository.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.repository.idao.ICompanyDao;
import com.excilys.burleon.computerdatabase.repository.model.Company;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.OrderCompanyEnum;

/**
 * @author Junior Burleon
 *
 */
@Repository
public class CompanyDao extends ADao<Company> implements ICompanyDao {

    private static final class CompanyMapper implements RowMapper<Company> {
        @Override
        public Company mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Company company = new Company();
            company.setId(rs.getInt("id"));
            company.setName(rs.getString("name"));
            return company;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    @Override
    public Optional<Company> create(final Company entity) {
        CompanyDao.LOGGER.trace("create : entity : " + entity);

        Company tmpEntity = null;
        try {
            final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                    .withTableName(this.getTableName(entity.getClass())).usingGeneratedKeyColumns("id");
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", entity.getName());
            final Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
            tmpEntity = entity;
            tmpEntity.setId(newId.longValue());
        } catch (final Exception e) {
            tmpEntity = null;
            CompanyDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }

    @Override
    public List<Company> find(final Class<Company> c) {
        CompanyDao.LOGGER.trace("find : class : " + c);

        List<Company> entities = new ArrayList<>();
        try {
            entities = this.jdbcTemplate.query("SELECT id, name FROM " + this.getTableName(c),
                    new CompanyMapper());
        } catch (final DataAccessException e) {
            CompanyDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public Optional<Company> findById(final Class<Company> c, final long id) {
        CompanyDao.LOGGER.trace("findById : class : " + c + "\tid : " + id);

        Company tmpEntity = null;
        try {
            tmpEntity = this.jdbcTemplate.queryForObject(
                    "SELECT id, name FROM " + this.getTableName(c) + " WHERE id = ?", new CompanyMapper(), id);
        } catch (final DataAccessException e) {
            CompanyDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }

    @Override
    public List<Company> findRange(final Class<Company> c, final int first, final int nbRecord, String filterWord,
            IOrderEnum<Company> orderBy) {
        CompanyDao.LOGGER.trace("findRange : c : " + c + "\tfirst : " + first + "\tnbRecord : " + nbRecord
                + "\tfilterWord : " + filterWord + "\torderBy : " + orderBy);

        if (filterWord == null) {
            filterWord = "";
        }
        if (orderBy == null) {
            orderBy = OrderCompanyEnum.NAME;
        }

        List<Company> entities = new ArrayList<>();
        try {
            entities = this.jdbcTemplate.query(
                    "SELECT id, name FROM " + this.getTableName(c) + " WHERE company.name " + "LIKE ? ORDER BY "
                            + orderBy.toString() + " ASC LIMIT ?,?",
                    new CompanyMapper(), filterWord + "%", first, nbRecord);
        } catch (final DataAccessException e) {
            CompanyDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }

        return entities;
    }

    @Override
    public long getNbRecords(final Class<Company> c, final String filterWord) {
        CompanyDao.LOGGER.trace("getNbRecords : c : " + c + "\tfilterWord : " + filterWord);

        long nbTotal = 0;
        try {
            nbTotal = this.jdbcTemplate.queryForObject(
                    "SELECT count(*) as total FROM " + this.getTableName(c) + " WHERE name LIKE ?", Long.class,
                    filterWord + "%");
        } catch (final DataAccessException e) {
            CompanyDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return nbTotal;
    }

    @Override
    public Optional<Company> update(final Company entity) {
        CompanyDao.LOGGER.trace("update : entity : " + entity);

        Company tmpEntity = null;
        try {
            this.jdbcTemplate.update(
                    "UPDATE " + this.getTableName(entity.getClass()) + "UPDATE "
                            + this.getTableName(entity.getClass()) + " SET name = ? WHERE id = ?",
                    entity.getName(), entity.getId());
            tmpEntity = entity;
        } catch (final DataAccessException e) {
            CompanyDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }
}
