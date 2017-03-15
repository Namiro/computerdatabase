/**
 *
 */
package com.computerdatabase.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.computerdatabase.model.entity.Entity;
import com.computerdatabase.model.entity.IEntity;
import com.computerdatabase.model.idao.IDao;

/**
 * This class allow to have some basic interactions with the table of specified
 * entity
 *
 * @author Junior Burleon
 *
 * @param <E>
 *            The entity type
 */
public abstract class Dao<E> implements IDao<E> {

	protected String tableName = null;

	public Dao() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.computerdatabase.model.dao.IDao#create(com.computerdatabase.model.
	 * entity.Entity)
	 */
	@Override
	public abstract E create(IEntity entity);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.computerdatabase.model.dao.IDao#delete(com.computerdatabase.model.
	 * entity.Entity)
	 */
	@Override
	public boolean delete(final IEntity obj) {
		PreparedStatement statement = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"DELETE FROM " + this.tableName + " WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.setLong(1, obj.getId());
			statement.executeUpdate();
			DatabaseConnection.INSTANCE.getConnection().setAutoCommit(false);
			statement.execute();
		} catch (final SQLException ex) {
			try {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				return false;
			} finally {
				DatabaseConnection.INSTANCE.closeStatement(statement);
				DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.dao.IDao#find()
	 */
	@Override
	public abstract ArrayList<E> find();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.dao.IDao#find(int)
	 */
	@Override
	public abstract E find(final long id);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.dao.IDao#getNbRecords()
	 */
	@Override
	public int getNbRecords() {
		int nbTotal = 0;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DatabaseConnection.INSTANCE.getConnection().prepareStatement(
					"SELECT count(*) as total FROM " + this.tableName, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.executeUpdate();
			resultSet = statement.getResultSet();
			if (resultSet.first())
				nbTotal = resultSet.getInt("total");
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(resultSet);
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(DatabaseConnection.INSTANCE.getConnection());
		}

		return nbTotal;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.computerdatabase.model.dao.IDao#update(com.computerdatabase.model.
	 * entity.Entity)
	 */
	@Override
	public abstract Entity update(IEntity entity);
}
