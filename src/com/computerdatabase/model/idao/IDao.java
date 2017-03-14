package com.computerdatabase.model.idao;

import java.util.ArrayList;

import com.computerdatabase.model.entity.IEntity;

public interface IDao<E> {

	/**
	 * Method to insert an element in the table of specified entity
	 *
	 * @param entity
	 *            Object of type T
	 * @return Entity
	 */
	E create(IEntity entity);

	/**
	 * Method to delete an element in the table of specified entity
	 *
	 * @param entity
	 *            Object to delete
	 * @return boolean Success -> True else false
	 */
	boolean delete(IEntity entity);

	/**
	 * Method to get all element from the database
	 *
	 * @return A list with all records. Return null if there is a problem. Else
	 *         a list (This list can be empty if there is nothing found by the
	 *         request).
	 */
	ArrayList<E> find();

	/**
	 * Method to research by id an element in the table of specified entity
	 *
	 * @param id
	 * @return T Object of type T. Null if nothing found.
	 */
	E find(int id);

	int getNbRecords();

	/**
	 * Method to update an element in the table of specified entity
	 *
	 * @param entity
	 *            Object of type T to update
	 * @return Entity
	 */
	IEntity update(IEntity entity);

}