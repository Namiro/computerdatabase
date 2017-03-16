package com.excilys.burleon.computerdatabase.persistence.model;

/**
 * This class represent a company records
 *
 * @author Junior Burleon
 *
 */
public class Company extends Entity implements IEntity {
	public static class CompanyBuilder extends EntityBuilder<CompanyBuilder, Company> {
		String name;

		@Override
		public Company build() {
			return new Company(this.id, this.name);
		}

		public CompanyBuilder name(final String name) {
			this.name = name;
			return this;
		}
	}

	private String name;

	public Company() {

	}

	public Company(final long id, final String name) {
		super(id);
		this.name = name;
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
		final Company other = (Company) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
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
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.id + "\t" + this.name;
	}

}