package com.excilys.burleon.computerdatabase.core.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.excilys.burleon.computerdatabase.core.model.enumeration.AccessLevelEnum;

/**
 * This class represent a company records.
 *
 * @author Junior Burleon
 *
 */
@javax.persistence.Entity
@Table(name = "user")
public class User extends AEntity implements IEntity {
    public static class UserBuilder extends EntityBuilder<UserBuilder, User> {
        String username;
        String password;

        @Override
        public User build() {
            return new User(this.id, this.username, this.password);
        }

        /**
         * Password.
         *
         * @param name
         *            The password of the user
         * @return The user
         */
        public UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        /**
         * Username.
         *
         * @param username
         *            The username of the user
         * @return The user
         */
        public UserBuilder username(final String username) {
            this.username = username;
            return this;
        }
    }

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccessLevelEnum accessLevel = AccessLevelEnum.ANONYMOUS;

    /**
     * Default constructor.
     */
    public User() {

    }

    /**
     * Constructor full params.
     *
     * @param id
     *            The id
     * @param username
     *            The username
     * @param password
     *            The password
     */
    public User(final long id, final String username, final String password) {
        super(id);
        this.username = username;
        this.password = password;
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
        final User other = (User) obj;
        if (this.password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!this.password.equals(other.password)) {
            return false;
        }
        if (this.username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    public AccessLevelEnum getAccessLevel() {
        return this.accessLevel;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
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
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
        return result;
    }

    public void setAccessLevel(final AccessLevelEnum accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
