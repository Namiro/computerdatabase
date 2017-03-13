package com.computerdatabase.model.entity;

import com.google.gson.Gson;

/**
 *
 * This class must but extends by all entities from the model.
 *
 * @author Junior Burleon
 *
 */
public abstract class Entity {

	private static Gson gson = new Gson();

	/**
	 * This is the id for all entities.
	 */
	public int id;

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return Entity.gson.toJson(this);
	}

}
