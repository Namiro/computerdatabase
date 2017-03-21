package com.excilys.burleon.computerdatabase.persistence.idao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.burleon.computerdatabase.persistence.dao.DatabaseConnection;
import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.model.IEntity;

public interface IDao<E extends IEntity> {

    /**
     * Method to insert an element in the table of specified entity.
     *
     * @param entity
     *            Object of type E
     * @return Entity
     */
    E create(E entity);

    /**
     * Method to delete an element in the table of specified entity.
     *
     * @param entity
     *            Object to delete
     * @return boolean Success -> True else false.
     */
    default boolean delete(final E entity) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "DELETE FROM " + this.getTableName(entity.getClass()) + " WHERE id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
            DatabaseConnection.INSTANCE.getConnection().setAutoCommit(false);
            statement.execute();
        } catch (final SQLException e) {
            try {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                throw new PersistenceException(e);
            } finally {
                DatabaseConnection.INSTANCE.closeStatement(statement);
                DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
            }
        }
        return true;
    }

    /**
     * Method to get all element from the database.
     *
     * @param c
     *            The type of entity
     *
     * @return A list with all records. Return null if there is a problem.
     *         Else a list (This list can be empty if there is nothing found
     *         by the request).
     */
    ArrayList<E> find(Class<E> c);

    /**
     * Method to research by id an element in the table of specified entity.
     *
     * @param c
     *            The type of entity
     * @param id
     *            The id of the entity you want get
     * @return E Object of type E. Null if nothing found.
     */
    E find(Class<E> c, long id);

    /**
     * Method to get all element between in a limit from the database The
     * limit is based on the number of records found with a simple select.
     *
     * @param c
     *            The type of entity
     * @param first
     *            The number of the first element
     * @param nbRecord
     *            The number of record you want from the first
     * @param filterWord
     *            The word that will be used to filter the results
     * @return A list with the record comprise between first and last.
     */
    ArrayList<E> findRange(Class<E> c, int first, int nbRecord, String filterWord);

    /**
     *
     * @param c
     *            The type of entity
     * @return The number of records for this entity
     */
    default long getNbRecords(final Class<E> c) {
        long nbTotal = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
                    "SELECT count(*) as total FROM " + this.getTableName(c), ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.execute();
            resultSet = statement.getResultSet();
            if (resultSet.first()) {
                nbTotal = resultSet.getLong("total");
            }
        } catch (final SQLException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            throw new PersistenceException(e);
        } finally {
            DatabaseConnection.INSTANCE.closeResultSet(resultSet);
            DatabaseConnection.INSTANCE.closeStatement(statement);
            DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
        }

        return nbTotal;
    }

    /**
     *
     * @param c
     *            The type of entity
     * @param filterWord
     *            To filter the records and get only these that match with the
     *            filter word
     * @return The number of records for this entity and that match with the
     *         filter word
     */
    long getNbRecords(Class<E> c, String filterWord);

    /**
     * Method to get the table name. The table name is the name of the entity
     * class.
     *
     * @param clazz
     *            The entity class
     * @return The table Name
     */
    default String getTableName(final Class<? extends IEntity> clazz) {

        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * Method to update an element in the table of specified entity.
     *
     * @param entity
     *            Object of type E to update
     * @return Entity
     */
    E update(E entity);

}