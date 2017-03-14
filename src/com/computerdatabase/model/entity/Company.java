package com.computerdatabase.model.entity;

/**
 * This class represent a company records
 *
 * @author Junior Burleon
 *
 */
public class Company extends Entity implements IEntity {
	private String name;

	public Company() {

	}

	public Company(final int id, final String name) {
		super(id);
		this.name = name;
	}

	public Company(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.id + "\t" + this.name;
	}

}
