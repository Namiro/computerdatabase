package com.computerdatabase.model.entity;

import java.time.LocalDate;

public class Computer {
	private int id;
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
