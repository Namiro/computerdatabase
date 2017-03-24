package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.idao.ICompanyDao;
import com.excilys.burleon.computerdatabase.persistence.idao.IDao;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.OrderCompanyEnum;

/**
 * @author Junior Burleon
 *
 */
public enum CompanyDao implements ICompanyDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    /**
     * Default constructor.
     */
    CompanyDao() {

    }

    @Override
    public Optional<Company> create(final Company entity) {
        Company tmpEntity = null;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();) {

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + this.getTableName(entity.getClass()) + " SET name = ?",
                    Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, entity.getName());
                statement.executeUpdate();
                connection.commit();
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
    public boolean delete(final Company entity) {
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM " + this.getTableName(entity.getClass()) + " WHERE id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    PreparedStatement statement1 = connection.prepareStatement(
                            "DELETE FROM computer WHERE company_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE)) {
                statement.setLong(1, entity.getId());
                statement1.setLong(1, entity.getId());
                statement1.execute();
                statement.executeUpdate();
                connection.commit();
            } catch (final SQLException e) {
                connection.rollback();
                IDao.LOGGER.error(e.getMessage());
                throw new PersistenceException(e);
            }
        } catch (final SQLException e1) {
            this.LOGGER.error(e1.getMessage());
            throw new PersistenceException(e1);
        }
        return true;

    }

    @Override
    public List<Company> find(final Class<Company> c) {
        final ArrayList<Company> entities = new ArrayList<>();
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name FROM " + this.getTableName(c), ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                while (resultSet.next()) {
                    entities.add(new Company.CompanyBuilder().name(resultSet.getString("name"))
                            .id(resultSet.getInt("id")).build());
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public Optional<Company> find(final Class<Company> c, final long id) {
        Company tmpEntity = null;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name FROM " + this.getTableName(c) + " WHERE id = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setLong(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                if (resultSet.first()) {
                    tmpEntity = new Company(resultSet.getInt("id"), resultSet.getString("name"));
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return Optional.ofNullable(tmpEntity);
    }

    @Override
    public List<Company> findRange(final Class<Company> c, final int first, final int nbRecord, String filterWord,
            IOrderEnum<Company> orderBy) {
        if (filterWord == null) {
            filterWord = "";
        }
        if (orderBy == null) {
            orderBy = OrderCompanyEnum.NAME;
        }

        final ArrayList<Company> entities = new ArrayList<>();
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name FROM " + this.getTableName(c) + " WHERE company.name "
                                + "LIKE ? ORDER BY ISNULL(" + orderBy.toString() + "), " + orderBy.toString()
                                + " ASC LIMIT ?,?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setString(1, "%" + filterWord + "%");
            statement.setInt(2, first);
            statement.setInt(3, nbRecord);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                while (resultSet.next()) {
                    entities.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }
        } catch (final SQLException e) {
            this.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return entities;
    }

    @Override
    public long getNbRecords(final Class<Company> c, final String filterWord) {
        long nbTotal = 0;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT count(*) as total FROM " + this.getTableName(c) + " WHERE name LIKE ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setString(1, "%" + filterWord + "%");
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
    public Optional<Company> update(final Company entity) {
        Company tmpEntity = null;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + this.getTableName(entity.getClass()) + " SET name = ? WHERE id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
                statement.setString(1, entity.getName());
                statement.setLong(2, entity.getId());
                statement.executeUpdate();
                connection.commit();
                tmpEntity = entity;
            } catch (final SQLException e) {
                connection.rollback();
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
