package com.computerdatabase.model.entity;

import com.google.gson.Gson;

/**
 *
 * This class must but extends by all entities from the model.
 *
 * @author Junior Burleon
 *
 */
public abstract class Entity implements IEntity {

	private static Gson gson = new Gson();

	/**
	 * This is the id for all entities.
	 */
	protected int id;

	public Entity() {

	}

	public Entity(final int id) {
		super();
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.entity.IEntity#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.entity.IEntity#setId(int)
	 */
	@Override
	public void setId(final int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.computerdatabase.model.entity.IEntity#toString()
	 */
	@Override
	public String toString() {
		return Entity.gson.toJson(this);
	}

}
