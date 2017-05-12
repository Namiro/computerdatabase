package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

public class ObjectNotInitializedException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = 5666001391664754874L;

    /**
     * Default constructor.
     */
    public ObjectNotInitializedException() {
        super();
    }

    /**
     * @param s
     *            Message to display
     */
    public ObjectNotInitializedException(final String s) {
        super(s);
    }

    /**
     * @param s
     *            Message to display
     * @param e
     *            Previous exception
     */
    public ObjectNotInitializedException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     *            Previous exception
     */
    public ObjectNotInitializedException(final Throwable e) {
        super(e);
    }
}
