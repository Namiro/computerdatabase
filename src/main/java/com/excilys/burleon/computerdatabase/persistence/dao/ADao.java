package com.excilys.burleon.computerdatabase.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.idao.IDao;
import com.excilys.burleon.computerdatabase.persistence.model.IEntity;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;

public abstract class ADao<E extends IEntity> implements IDao<E> {

    @Autowired
    protected DatabaseConnection databaseConnection;

    @Override
    public boolean delete(final E entity) {
        IModelService.LOGGER.trace("delete : entity : " + entity);

        boolean success = true;
        try (Connection connection = this.databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM " + this.getTableName(entity.getClass()) + " WHERE id = ?",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (final SQLException e) {
            success = false;
            IDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return success;
    }

    @Override
    public long getNbRecords(final Class<E> c) {
        IModelService.LOGGER.trace("getNbRecords : class : " + c);

        long nbTotal = 0;
        try (Connection connection = this.databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT count(*) as total FROM " + this.getTableName(c), ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet();) {
                if (resultSet.first()) {
                    nbTotal = resultSet.getLong("total");
                }
            }
        } catch (final SQLException e) {
            IDao.LOGGER.error(e.getMessage());
            throw new PersistenceException(e);
        }
        return nbTotal;
    }
}
