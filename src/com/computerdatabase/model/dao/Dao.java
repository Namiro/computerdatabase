/**
 *
 */
package com.computerdatabase.model.dao;

import java.lang.reflect.ParameterizedType;
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
	@SuppressWarnings("unchecked")
	public Dao(final Connection connection) {
		this.connection = connection;
		this.tableName = ((Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]).getSimpleName().toLowerCase();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.computerdatabase.model.dao.IDao#create(com.computerdatabase.model.
	 * entity.Entity)
	 */
	@Override
	public abstract E create(E entity);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.computerdatabase.model.dao.IDao#delete(com.computerdatabase.model.
	 * entity.Entity)
	 */
	@Override
	public boolean delete(final E obj) {
		try {
			final PreparedStatement prepare = this.connection.prepareStatement(
					"DELETE FROM " + this.tableName + " WHERE id = " + ((IEntity) obj).getId(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			this.connection.setAutoCommit(false);
			prepare.execute();
			this.connection.commit();
		} catch (final SQLException ex) {
			try {
				this.connection.rollback();
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				return false;
			} catch (final SQLException ex1) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex1);
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
	public abstract E find(final int id);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.dao.IDao#getNbRecords()
	 */
	@Override
	public int getNbRecords() {
		int nbTotal = 0;
		try {
			final ResultSet result = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT count(*) as total FROM " + this.tableName);

			if (result.first())
				nbTotal = result.getInt("total");
		} catch (final SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
	public abstract Entity update(E entity);

}
