package com.computerdatabase.model.entity;

import java.time.LocalDate;

/**
 * This class represent a computer record
 *
 * @author Junior Burleon
 *
 */
public class Computer extends Entity {
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int companyId;

	public int getCompanyId() {
		return this.companyId;
	}

	public LocalDate getDiscontinued() {
		return this.discontinued;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public LocalDate getIntroduced() {
		return this.introduced;
	}

	public String getName() {
		return this.name;
	}

	public void setCompanyId(final int companyId) {
		this.companyId = companyId;
	}

	public void setDiscontinued(final LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	public void setIntroduced(final LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
