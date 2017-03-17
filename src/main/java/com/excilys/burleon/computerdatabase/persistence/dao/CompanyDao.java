package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.idao.ICompanyDao;
import com.excilys.burleon.computerdatabase.persistence.model.Company;

/**
 * @author Junior Burleon
 *
 */
public enum CompanyDao implements ICompanyDao {

    INSTANCE;

    /**
     * Default constructor.
     */
    CompanyDao() {

    }

    @Override
    public Company create(final Company entity) {
        Company tmpEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "INSERT INTO " + this.getTableName(entity.getClass()) + " SET name = ?",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            entity.setId(resultSet.getInt(1));
            tmpEntity = entity;
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
    public ArrayList<Company> find(final Class<Company> c) {
        ArrayList<Company> entities = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT * FROM " + this.getTableName(c), ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.execute();
            resultSet = statement.getResultSet();
            entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(new Company.CompanyBuilder().name(resultSet.getString("name"))
                        .id(resultSet.getInt("id")).build());
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
    public Company find(final Class<Company> c, final long id) {
        Company tmpEntity = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT * FROM " + this.getTableName(c) + " WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, id);
            statement.execute();
            resultSet = statement.getResultSet();
            if (resultSet.first()) {
                tmpEntity = new Company(resultSet.getInt("id"), resultSet.getString("name"));
            }

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
    public ArrayList<Company> findRange(final Class<Company> c, final int first, final int nbRecord) {
        ArrayList<Company> entities = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT * FROM " + this.getTableName(c) + " LIMIT ?,?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, first);
            statement.setInt(2, nbRecord);
            statement.execute();
            resultSet = statement.getResultSet();
            entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));
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
    public Company update(final Company entity) {
        Company tmpEntity = null;
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "UPDATE " + this.getTableName(entity.getClass()) + " SET name = ? WHERE id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();
            tmpEntity = entity;
        } catch (final SQLException e) {
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }
        return tmpEntity;
    }
}
