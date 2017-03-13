package com.computerdatabase.model.idao;

import java.util.ArrayList;

import com.computerdatabase.model.entity.Entity;

public interface IDao<E> {

	/**
	 * Method to insert an element in the table of specified entity
	 *
	 * @param entity
	 *            Object of type T
	 * @return Entity
	 */
	E create(E entity);

	/**
	 * Method to delete an element in the table of specified entity
	 *
	 * @param obj
	 *            Object to delete
	 * @return boolean Success -> True else false
	 */
	boolean delete(E obj);

	/**
	 * Method to get all element from the database
	 *
	 * @return Une liste typé T avec tout les records
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
	Entity update(E entity);

}