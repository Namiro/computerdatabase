package com.excilys.burleon.computerdatabase.service.exception;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class DataValidationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8237870712450648471L;

    /**
     * Default constructor.
     */
    public DataValidationException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public DataValidationException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public DataValidationException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public DataValidationException(final Throwable e) {
        super(e);
    }

}