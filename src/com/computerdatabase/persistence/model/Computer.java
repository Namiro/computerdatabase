package com.computerdatabase.persistence.model;

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
	private long companyId;

	public Computer() {

	}

	public Computer(final long id, final String name, final LocalDateTime introduced, final LocalDateTime discontinued,
			final long companyId) {
		super(id);
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Computer(final String name, final LocalDateTime introduced, final LocalDateTime discontinued,
			final long companyId) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
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
		if (this.companyId != other.companyId)
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

	public long getCompanyId() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (this.companyId ^ (this.companyId >>> 32));
		result = prime * result + ((this.discontinued == null) ? 0 : this.discontinued.hashCode());
		result = prime * result + ((this.introduced == null) ? 0 : this.introduced.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public void setCompanyId(final long l) {
		this.companyId = l;
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
