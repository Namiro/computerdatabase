/**
 *
 */
package com.computerdatabase.model.dao;

import java.sql.Connection;
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

	/**
	 * The connection to the database
	 */
	protected Connection connection = null;
	protected String tableName = null;

	/**
	 *
	 * @param connection
	 *            Connection to the database
	 */
	public Dao() {

		this.connection = DatabaseConnection.INSTANCE.getConnection();
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
			statement = this.connection.prepareStatement("DELETE FROM ? WHERE id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.setLong(2, obj.getId());
			statement.executeUpdate();
			this.connection.setAutoCommit(false);
			statement.execute();
			this.connection.commit();
		} catch (final SQLException ex) {
			try {
				this.connection.rollback();
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				return false;
			} catch (final SQLException ex1) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex1);
			} finally {
				DatabaseConnection.INSTANCE.closeStatement(statement);
				DatabaseConnection.INSTANCE.closeConnection(this.connection);
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
			statement = this.connection.prepareStatement("SELECT count(*) as total FROM ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, this.tableName);
			statement.executeUpdate();
			resultSet = statement.getResultSet();
			if (resultSet.first())
				nbTotal = resultSet.getInt("total");
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(resultSet);
			DatabaseConnection.INSTANCE.closeStatement(statement);
			DatabaseConnection.INSTANCE.closeConnection(this.connection);
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
