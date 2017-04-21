/**
 *
 */
package com.excilys.burleon.computerdatabase.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.excilys.burleon.computerdatabase.repository.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.repository.model.Company;
import com.excilys.burleon.computerdatabase.repository.model.Computer;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.OrderComputerEnum;

/**
 * @author Junior Burleon
 *
 */
@Repository
public class ComputerDao extends ADao<Computer> implements IComputerDao {

    private static final class ComputerMapper implements RowMapper<Computer> {
        @Override
        public Computer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Computer computer = new Computer.ComputerBuilder().name(rs.getString("name"))
                    .id(rs.getLong("id"))
                    .introduced((rs.getTimestamp("introduced") != null)
                            ? rs.getTimestamp("introduced").toLocalDateTime() : null)
                    .discontinued((rs.getTimestamp("discontinued") != null)
                            ? rs.getTimestamp("discontinued").toLocalDateTime() : null)
                    .company(new Company.CompanyBuilder().name(rs.getString("cName")).id(rs.getLong("company_id"))
                            .build())
                    .build();
            return computer;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

    @Override
    public Optional<Computer> create(final Computer entity) {
        ComputerDao.LOGGER.trace("create : entity : " + entity);

        final Computer centity = entity;
        Computer tmpEntity = null;

        try {
            final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                    .withTableName(this.getTableName(entity.getClass())).usingGeneratedKeyColumns("id");
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", entity.getName());
            if (centity.getIntroduced() == null) {
                parameters.put("introduced", java.sql.Types.TIMESTAMP);
            } else {
                if (centity.getIntroduced()
                        .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                    throw new PersistenceException(
                            "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                }
                parameters.put("introduced", Timestamp.valueOf(centity.getIntroduced()));
            }

            if (centity.getDiscontinued() == null) {
                parameters.put("discontinued", java.sql.Types.TIMESTAMP);
            } else {
                if (centity.getDiscontinued()
                        .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                    throw new PersistenceException(
                            "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                }
                parameters.put("discontinued", Timestamp.valueOf(centity.getDiscontinued()));
            }

            if (centity.getCompany() == null) {
                parameters.put("company_id", java.sql.Types.INTEGER);
            } else {
                parameters.put("company_id", centity.getCompany().getId());
            }
            final Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
            tmpEntity = entity;
            tmpEntity.setId(newId.longValue());
        } catch (final Exception e) {
            tmpEntity = null;
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }

    @Override
    public boolean deleteByCompany(final Company entity) {
        ComputerDao.LOGGER.trace("delete : entity : " + entity);

        boolean success = true;
        try {
            this.jdbcTemplate.update("DELETE FROM computer WHERE company_id = ?", entity.getId());
            success = true;
        } catch (final DataAccessException e) {
            success = false;
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return success;
    }

    @Override
    public List<Computer> find(final Class<Computer> c) {
        ComputerDao.LOGGER.trace("find : class : " + c);

        List<Computer> entities = new ArrayList<>();
        try {
            entities = this.jdbcTemplate
                    .query("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, "
                            + "company.name as cName FROM " + this.getTableName(c)
                            + " LEFT JOIN company ON computer.company_id=company.id", new ComputerMapper());
        } catch (final DataAccessException e) {
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public Optional<Computer> findById(final Class<Computer> c, final long id) {
        ComputerDao.LOGGER.trace("findById : class : " + c + "\tid : " + id);

        Computer tmpEntity = null;
        try {
            tmpEntity = this.jdbcTemplate.queryForObject(
                    "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, "
                            + "company.name as cName FROM " + this.getTableName(c)
                            + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.id = ?",
                    new ComputerMapper(), id);
        } catch (final DataAccessException e) {
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }

    @Override
    public List<Computer> findRange(final Class<Computer> c, final int first, final int nbRecord,
            String filterWord, IOrderEnum<Computer> orderBy) {
        ComputerDao.LOGGER.trace("findRange : c : " + c + "\tfirst : " + first + "\tnbRecord : " + nbRecord
                + "\tfilterWord : " + filterWord + "\torderBy : " + orderBy);

        if (filterWord == null) {
            filterWord = "";
        }
        if (orderBy == null) {
            orderBy = OrderComputerEnum.NAME;
        }

        List<Computer> entities = new ArrayList<>();
        try {
            entities = this.jdbcTemplate.query(
                    "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, "
                            + "company.name as cName FROM " + this.getTableName(c)
                            + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.name "
                            + "LIKE ? OR company.name LIKE ? ORDER BY " + orderBy.toString() + " ASC LIMIT ?,?",
                    new ComputerMapper(), filterWord + "%", filterWord + "%", first, nbRecord);
        } catch (final DataAccessException e) {
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public long getNbRecords(final Class<Computer> c, final String filterWord) {
        ComputerDao.LOGGER.trace("getNbRecords : c : " + c + "\tfilterWord : " + filterWord);

        long nbTotal = 0;
        try {
            nbTotal = this.jdbcTemplate.queryForObject("SELECT count(*) as total FROM " + this.getTableName(c)
                    + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.name "
                    + "LIKE ? OR company.name LIKE ? ", Long.class, filterWord + "%", filterWord + "%");
        } catch (final DataAccessException e) {
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return nbTotal;
    }

    @Override
    public Optional<Computer> update(final Computer entity) {
        ComputerDao.LOGGER.trace("update : entity : " + entity);

        final Computer centity = entity;
        Computer tmpEntity = null;

        try {
            final Object[] params = new Object[5];

            params[0] = centity.getName();
            if (centity.getIntroduced() == null) {
                params[1] = java.sql.Types.TIMESTAMP;
            } else {
                if (centity.getIntroduced()
                        .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                    throw new PersistenceException(
                            "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                }
                params[1] = Timestamp.valueOf(centity.getIntroduced());
            }

            if (centity.getDiscontinued() == null) {
                params[2] = java.sql.Types.TIMESTAMP;
            } else {
                if (centity.getDiscontinued()
                        .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                    throw new PersistenceException(
                            "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                }
                params[2] = Timestamp.valueOf(centity.getDiscontinued());
            }
            if (centity.getCompany() == null) {
                params[3] = java.sql.Types.INTEGER;
            } else {
                params[3] = centity.getCompany().getId();
            }
            params[4] = entity.getId();

            this.jdbcTemplate.update("UPDATE " + this.getTableName(entity.getClass())
                    + " SET name = ?, introduced = ?, discontinued = ?, company_id = ? " + "WHERE computer.id = ?",
                    params);
            tmpEntity = entity;
        } catch (final DataAccessException e) {
            ComputerDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }
}
