package com.computerdatabase.persistence.idao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.persistence.dao.DatabaseConnection;
import com.computerdatabase.persistence.exception.PersistenceException;
import com.computerdatabase.persistence.model.IEntity;

public interface IDao<E extends IEntity> {

	/**
	 * Method to insert an element in the table of specified entity
	 *
	 * @param entity
	 *            Object of type E
	 * @return Entity
	 */
	E create(E entity);

	/**
	 * Method to delete an element in the table of specified entity
	 *
	 * @param entity
	 *            Object to delete
	 * @return boolean Success -> True else false
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
	 * Method to get all element from the database
	 *
	 * @return A list with all records. Return null if there is a problem. Else
	 *         a list (This list can be empty if there is nothing found by the
	 *         request).
	 */
	ArrayList<E> find(final Class<E> c);

	/**
	 * Method to research by id an element in the table of specified entity
	 *
	 * @param id
	 * @return E Object of type E. Null if nothing found.
	 */
	E find(final Class<E> t, long id);

	/**
	 * Method to get all element between in a limit from the database The limit
	 * is based on the number of records found with a simple select.
	 *
	 * @param first
	 * @param nbRecord
	 * @return A list with the record comprise between first and last.
	 */
	ArrayList<E> findRange(final Class<E> c, int first, int nbRecord);

	/**
	 *
	 * @return The number of records for this entity
	 */
	default int getNbRecords(final Class<E> c) {
		int nbTotal = 0;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"SELECT count(*) as total FROM " + this.getTableName(c), ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.executeUpdate();
			resultSet = statement.getResultSet();
			if (resultSet.first())
				nbTotal = resultSet.getInt("total");
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
	 * Method to get the table name. The table name is the name of the entity
	 * class.
	 *
	 * @param class1
	 *
	 *
	 * @param clazz
	 *            The entity class
	 * @return The table Name
	 */
	default String getTableName(final Class<? extends IEntity> clazz) {

		return clazz.getSimpleName().toLowerCase();
	}

	/**
	 * Method to update an element in the table of specified entity
	 *
	 * @param entity
	 *            Object of type E to update
	 * @return Entity
	 */
	E update(E entity);

}