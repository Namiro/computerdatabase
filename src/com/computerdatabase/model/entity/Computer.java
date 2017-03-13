package com.computerdatabase.model.entity;

import java.time.LocalDateTime;

/**
 * This class represent a computer record
 *
 * @author Junior Burleon
 *
 */
public class Computer extends Entity implements IEntity {
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Integer companyId;

	public Computer() {

	}

	public Computer(final int id, final String name, final LocalDateTime introduced, final LocalDateTime discontinued,
			final Integer companyId) {
		super(id);
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Computer(final String name, final LocalDateTime introduced, final LocalDateTime discontinued,
			final Integer companyId) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public LocalDateTime getDiscontinued() {
		return this.discontinued;
	}

	public LocalDateTime getIntroduced() {
		return this.introduced;
	}

	public String getName() {
		return this.name;
	}

	public void setCompanyId(final Integer companyId) {
		this.companyId = companyId;
	}

	public void setDiscontinued(final LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}

	public void setIntroduced(final LocalDateTime introduced) {
		this.introduced = introduced;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
