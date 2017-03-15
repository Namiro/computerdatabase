package com.computerdatabase.persistence.model;

import java.time.LocalDateTime;

/**
 * This class represent a computer record
 *
 * @author Junior Burleon
 *
 */
public class Computer extends Entity implements IEntity {
	public static class ComputerBuilder extends EntityBuilder<ComputerBuilder, Computer> {
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private Company company;

		@Override
		public Computer build() {
			return new Computer(this.id, this.name, this.introduced, this.discontinued, this.company);
		}

		public ComputerBuilder company(final Company company) {
			this.company = company;
			return this;
		}

		public ComputerBuilder discontinued(final LocalDateTime discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder introduced(final LocalDateTime introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder name(final String name) {
			this.name = name;
			return this;
		}
	}

	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company;

	public Computer() {

	}

	public Computer(final long id, final String name, final LocalDateTime introduced, final LocalDateTime discontinued,
			final Company company) {
		super(id);
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final Computer other = (Computer) obj;
		if (this.company != other.company)
			return false;
		if (this.discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!this.discontinued.equals(other.discontinued))
			return false;
		if (this.introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!this.introduced.equals(other.introduced))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}

	public Company getCompany() {
		return this.company;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.company == null) ? 0 : this.company.hashCode());
		result = prime * result + ((this.discontinued == null) ? 0 : this.discontinued.hashCode());
		result = prime * result + ((this.introduced == null) ? 0 : this.introduced.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public void setCompany(final Company company) {
		this.company = company;
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

	@Override
	public String toString() {
		return this.id + "\t" + this.name + "\t";
	}
}
