/**
 *
 */
package com.computerdatabase.model.dao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Junior Burleon
 *
 * @param <T>
 *            The entity type
 */
public abstract class Dao<T> {

	/**
	 * The connection to the database
	 */
	protected Connection connection = null;

	/**
	 *
	 * @param connection
	 *            Connection to the database
	 */
	public Dao(final Connection connection) {
		this.connection = connection;
	}

	/**
	 * Method to delete an element in the table of specified entity
	 *
	 * @param obj
	 *            Object to delete
	 * @return boolean Success -> True else false
	 */
	public abstract boolean delete(T obj);

	/**
	 * Method to research by id an element in the table of specified entity
	 *
	 * @param id
	 * @return T Object of type T
	 */
	public abstract T find(int id);

	/**
	 * Method to insert an element in the table of specified entity
	 *
	 * @param obj
	 *            Object of type T
	 * @return boolean Success -> True else false
	 */
	public abstract boolean insert(T obj);

	/**
	 * Method to know the number of element in the table of specified entity
	 *
	 * @return The number of records
	 */
	public abstract int nbRecord();

	/**
	 * Method to get all element from the database
	 *
	 * @return Une liste typé T avec tout les records
	 */
	public abstract ArrayList<T> select();

	/**
	 * Method to update an element in the table of specified entity
	 *
	 * @param obj
	 *            Object of type T to update
	 * @return boolean Success -> True else false
	 */
	public abstract boolean update(T obj);

}
