package com.excilys.burleon.computerdatabase.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.google.gson.Gson;

/**
 *
 * This class must but extends by all entities from the model.
 *
 * @author Junior Burleon
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AEntity implements IEntity {

    /**
     * For the builder pattern.
     *
     * @author Junior Burleon
     *
     */
    protected abstract static class EntityBuilder<B extends EntityBuilder<?, ?>, T extends AEntity>
            implements IBuild<T> {
        long id;

        /**
         * To build an entity.
         *
         * @param id
         *            The id
         * @return An entity
         */
        @SuppressWarnings("unchecked")
        public B id(final long id) {
            this.id = id;
            return (B) this;
        }
    }

    /**
     * For the builder pattern.
     *
     * @author Junior Burleon
     *
     * @param <T>
     *            An entity type
     */
    interface IBuild<T> {
        /**
         * To build.
         *
         * @return The entity that is building
         */
        T build();
    }

    private static Gson gson = new Gson();

    /**
     * This is the id for all entities.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    protected long id;

    /**
     * Default constructor.
     */
    public AEntity() {

    }

    /**
     * Constructor full params.
     *
     * @param id
     *            The id
     */
    AEntity(final long id) {
        this.id = id;
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
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AEntity other = (AEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.computerdatabase.model.entity.IEntity#getId()
     */
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (this.id ^ (this.id >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.computerdatabase.model.entity.IEntity#setId(int)
     */
    @Override
    public void setId(final long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.computerdatabase.model.entity.IEntity#toString()
     */
    @Override
    public String toString() {
        return AEntity.gson.toJson(this);
    }
}
