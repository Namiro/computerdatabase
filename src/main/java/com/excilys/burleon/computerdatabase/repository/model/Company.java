package com.excilys.burleon.computerdatabase.repository.model;

import javax.persistence.Table;

/**
 * This class represent a company records.
 *
 * @author Junior Burleon
 *
 */
@javax.persistence.Entity
@Table(name = "company")
public class Company extends AEntity implements IEntity {
    public static class CompanyBuilder extends EntityBuilder<CompanyBuilder, Company> {
        String name;

        @Override
        public Company build() {
            return new Company(this.id, this.name);
        }

        /**
         * Constructor with name.
         *
         * @param name
         *            The name of the company
         * @return The company
         */
        public CompanyBuilder name(final String name) {
            this.name = name;
            return this;
        }
    }

    private String name;

    /**
     * Default constructor.
     */
    public Company() {

    }

    /**
     * Constructor full params.
     *
     * @param id
     *            The id
     * @param name
     *            The name
     */
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
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public long getId() {
        return this.id;
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

    @Override
    public void setId(final long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + "\t" + this.name;
    }

}
