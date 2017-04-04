/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.persistence.idao.IDao;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.OrderComputerEnum;

/**
 * @author Junior Burleon
 *
 */
public enum ComputerDao implements IComputerDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

    /**
     * Default constructor.
     */
    ComputerDao() {

    }

    @Override
    public Optional<Computer> create(final Computer entity) {
        final Computer centity = entity;
        Computer tmpEntity = null;

        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + this.getTableName(entity.getClass())
                            + " SET name = ?, introduced = ?, discontinued = ?, company_id = ? ",
                    Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, centity.getName());
                if (centity.getIntroduced() == null) {
                    statement.setNull(2, java.sql.Types.TIMESTAMP);
                } else {
                    if (centity.getIntroduced()
                            .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                        throw new PersistenceException(
                                "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                    }
                    statement.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));
                }

                if (centity.getDiscontinued() == null) {
                    statement.setNull(3, java.sql.Types.TIMESTAMP);
                } else {
                    if (centity.getDiscontinued()
                            .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                        throw new PersistenceException(
                                "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                    }
                    statement.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));
                }

                if (centity.getCompany() == null) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setLong(4, centity.getCompany().getId());
                }
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys();) {
                    resultSet.next();
                    entity.setId(resultSet.getInt(1));
                    tmpEntity = entity;
                }
            } catch (final SQLException e) {
                this.LOGGER.error(e.getMessage());
                throw new PersistenceException(e);
            }
        } catch (final SQLException e1) {
            this.LOGGER.error(e1.getMessage());
            throw new PersistenceException(e1);
        }
        return Optional.ofNullable(tmpEntity);
    }

    @Override
    public boolean deleteByCompany(final Company entity, final Connection connection) {
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM computer WHERE company_id = ?",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, entity.getId());
            statement.execute();
            connection.commit();
        } catch (final SQLException e) {
            try {
                connection.rollback();
                success = false;
                IDao.LOGGER.error(e.getMessage());
                throw new PersistenceException(e);
            } catch (final SQLException e1) {
                this.LOGGER.error(e1.getMessage());
                throw new PersistenceException(e1);
            }
        }
        return success;
    }

    @Override
    public List<Computer> find(final Class<Computer> c) {
        final ArrayList<Computer> entities = new ArrayList<>();
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
                                + "company.name as cName FROM " + this.getTableName(c)
                                + " LEFT JOIN company ON computer.company_id=company.id",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                while (resultSet.next()) {
                    entities.add(new Computer.ComputerBuilder().name(resultSet.getString("name"))
                            .id(resultSet.getLong("id"))
                            .introduced((resultSet.getTimestamp("introduced") != null)
                                    ? resultSet.getTimestamp("introduced").toLocalDateTime() : null)
                            .discontinued((resultSet.getTimestamp("discontinued") != null)
                                    ? resultSet.getTimestamp("discontinued").toLocalDateTime() : null)
                            .company(new Company.CompanyBuilder().name(resultSet.getString("cName"))
                                    .id(resultSet.getLong("company_id")).build())
                            .build());
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public Optional<Computer> findById(final Class<Computer> c, final long id) {
        Computer entity = null;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
                                + "company.name as cName FROM " + this.getTableName(c)
                                + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.id = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setLong(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                if (resultSet.first()) {
                    entity = new Computer.ComputerBuilder().name(resultSet.getString("name"))
                            .id(resultSet.getLong("id"))
                            .introduced((resultSet.getTimestamp("introduced") != null)
                                    ? resultSet.getTimestamp("introduced").toLocalDateTime() : null)
                            .discontinued((resultSet.getTimestamp("discontinued") != null)
                                    ? resultSet.getTimestamp("discontinued").toLocalDateTime() : null)
                            .company(new Company.CompanyBuilder().name(resultSet.getString("cName"))
                                    .id(resultSet.getLong("company_id")).build())
                            .build();
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public List<Computer> findRange(final Class<Computer> c, final int first, final int nbRecord,
            String filterWord, IOrderEnum<Computer> orderBy) {
        if (filterWord == null) {
            filterWord = "";
        }
        if (orderBy == null) {
            orderBy = OrderComputerEnum.NAME;
        }

        ArrayList<Computer> entities = new ArrayList<>();

        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
                                + "company.name as cName FROM " + this.getTableName(c)
                                + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.name "
                                + "LIKE ? OR company.name LIKE ? ORDER BY ISNULL(" + orderBy.toString() + "), "
                                + orderBy.toString() + " ASC LIMIT ?,?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setString(1, "%" + filterWord + "%");
            statement.setString(2, "%" + filterWord + "%");
            statement.setInt(3, first);
            statement.setInt(4, nbRecord);
            System.out.println(statement.toString());
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(new Computer.ComputerBuilder().name(resultSet.getString("name"))
                            .id(resultSet.getLong("id"))
                            .introduced((resultSet.getTimestamp("introduced") != null)
                                    ? resultSet.getTimestamp("introduced").toLocalDateTime() : null)
                            .discontinued((resultSet.getTimestamp("discontinued") != null)
                                    ? resultSet.getTimestamp("discontinued").toLocalDateTime() : null)
                            .company(new Company.CompanyBuilder().name(resultSet.getString("cName"))
                                    .id(resultSet.getLong("company_id")).build())
                            .build());
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public long getNbRecords(final Class<Computer> c, final String filterWord) {
        long nbTotal = 0;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT count(*) as total FROM " + this.getTableName(c)
                                + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.name "
                                + "LIKE ? OR company.name LIKE ? ",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setString(1, "%" + filterWord + "%");
            statement.setString(2, "%" + filterWord + "%");
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                if (resultSet.first()) {
                    nbTotal = resultSet.getLong("total");
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return nbTotal;
    }

    @Override
    public Optional<Computer> update(final Computer entity) {
        final Computer centity = entity;
        Computer tmpEntity = null;

        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + this.getTableName(entity.getClass())
                            + " SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
                            + "WHERE computer.id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
                statement.setString(1, centity.getName());
                if (centity.getIntroduced() == null) {
                    statement.setNull(2, java.sql.Types.TIMESTAMP);
                } else {
                    if (centity.getIntroduced()
                            .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                        throw new PersistenceException(
                                "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                    }
                    statement.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));
                }
                if (centity.getDiscontinued() == null) {
                    statement.setNull(3, java.sql.Types.TIMESTAMP);
                } else {
                    if (centity.getDiscontinued()
                            .isAfter(LocalDateTime.of(LocalDate.of(2038, 01, 18), LocalTime.NOON))) {
                        throw new PersistenceException(
                                "Invalid date for TIMESTAMP MySQL. Max is : 2038-01-18 00:00:00");
                    }
                    statement.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));
                }
                if (centity.getCompany() == null) {
                    statement.setNull(4, java.sql.Types.INTEGER);
                } else {
                    statement.setLong(4, centity.getCompany().getId());
                }
                statement.setLong(5, entity.getId());
                statement.executeUpdate();
                tmpEntity = entity;
            } catch (final SQLException e) {
                this.LOGGER.error(e.getMessage());
                throw new PersistenceException(e);
            }
        } catch (final SQLException e1) {
            this.LOGGER.error(e1.getMessage());
            throw new PersistenceException(e1);
        }
        return Optional.ofNullable(tmpEntity);
    }
}
