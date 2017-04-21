package com.excilys.burleon.computerdatabase.repository.model;

import com.google.gson.Gson;

/**
 *
 * This class must but extends by all entities from the model.
 *
 * @author Junior Burleon
 *
 */
public abstract class Entity implements IEntity {

    /**
     * For the builder pattern.
     *
     * @author Junior Burleon
     *
     */
    protected abstract static class EntityBuilder<B extends EntityBuilder<?, ?>, T extends Entity>
            implements IBuild<T> {
        long id;

        /**
         * To build an entity.
         *
         * @param id
         *            The id
         * @return An entity
         */
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
    protected long id;

    /**
     * Default constructor.
     */
    public Entity() {

    }

    /**
     * Constructor full params.
     *
     * @param id
     *            The id
     */
    Entity(final long id) {
        super();
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
        final Entity other = (Entity) obj;
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

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
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
        return Entity.gson.toJson(this);
    }

}
