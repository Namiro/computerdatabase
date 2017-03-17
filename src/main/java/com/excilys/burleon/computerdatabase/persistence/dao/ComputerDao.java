/**
 *
 */
package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;

/**
 * @author Junior Burleon
 *
 */
public enum ComputerDao implements IComputerDao {

    INSTANCE;

    /**
     * Default constructor.
     */
    ComputerDao() {

    }

    @Override
    public Computer create(final Computer entity) {

        final Computer centity = entity;
        Computer tmpEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "INSERT INTO " + this.getTableName(entity.getClass())
                            + " SET name = ?, introduced = ?, discontinued = ?, company_id = ? ",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, centity.getName());
            if (centity.getIntroduced() == null) {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
            } else {
                statement.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));
            }

            if (centity.getDiscontinued() == null) {
                statement.setNull(3, java.sql.Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));
            }

            if (centity.getCompany() == null) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setLong(4, centity.getCompany().getId());
            }
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            entity.setId(resultSet.getInt(1));
            tmpEntity = centity;
        } catch (final SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeResultSet(resultSet);
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }
        return tmpEntity;
    }

    @Override
    public ArrayList<Computer> find(final Class<Computer> c) {
        ArrayList<Computer> entities = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
                            + "company.name as cName FROM " + this.getTableName(c)
                            + " LEFT JOIN company ON computer.company_id=company.id",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.execute();
            resultSet = statement.getResultSet();
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

        } catch (final SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeResultSet(resultSet);
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }
        return entities;
    }

    @Override
    public Computer find(final Class<Computer> c, final long id) {
        Computer entity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
                            + "company.name as cName FROM " + this.getTableName(c)
                            + " LEFT JOIN company ON computer.company_id=company.id WHERE computer.id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, id);
            statement.execute();
            resultSet = statement.getResultSet();
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

        } catch (final SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeResultSet(resultSet);
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }
        return entity;
    }

    @Override
    public ArrayList<Computer> findRange(final Class<Computer> c, final int first, final int nbRecord) {
        ArrayList<Computer> entities = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT computer.id, computer.name, introduced, discontinued, company_id, "
                            + "company.name as cName FROM " + this.getTableName(c)
                            + " LEFT JOIN company ON computer.company_id=company.id LIMIT ?,?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, first);
            statement.setInt(2, nbRecord);
            statement.execute();
            resultSet = statement.getResultSet();
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

        } catch (final SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeResultSet(resultSet);
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }
        return entities;
    }

    @Override
    public Computer update(final Computer entity) {
        final Computer centity = entity;
        Computer tmpEntity = null;
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "UPDATE " + this.getTableName(entity.getClass())
                            + " SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, centity.getName());
            if (centity.getIntroduced() == null) {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
            } else {
                statement.setTimestamp(2, Timestamp.valueOf(centity.getIntroduced()));
            }

            if (centity.getDiscontinued() == null) {
                statement.setNull(3, java.sql.Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(centity.getDiscontinued()));
            }

            if (centity.getCompany() == null) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setLong(4, centity.getCompany().getId());
            }

            statement.setLong(5, entity.getId());
            statement.executeUpdate();
            tmpEntity = centity;
        } catch (final Exception e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }
        return tmpEntity;
    }
}
