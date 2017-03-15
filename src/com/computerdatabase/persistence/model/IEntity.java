package com.computerdatabase.persistence.model;

/**
 *
 * @author Junior Burleon
 *
 */
public interface IEntity {

	public long getId();

	public void setId(long id);

	@Override
	public String toString();

}