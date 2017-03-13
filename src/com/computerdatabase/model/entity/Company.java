package com.computerdatabase.model.entity;

public class Company extends Entity {
	private int id;
	private String name;

	@Override
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
