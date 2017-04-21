package com.excilys.burleon.computerdatabase.repository.exception;

/**
 * This class allow to trigger an exception for the persistence.
 *
 * @author Junior Burleon
 *
 */
public class PersistenceException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -4026635148559085053L;

    /**
     * Default constructor.
     */
    public PersistenceException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public PersistenceException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public PersistenceException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public PersistenceException(final Throwable e) {
        super(e);
    }

}
