package com.excilys.burleon.computerdatabase.service.exception;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class AuthenticationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8237870712450648471L;

    /**
     * Default constructor.
     */
    public AuthenticationException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public AuthenticationException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public AuthenticationException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public AuthenticationException(final Throwable e) {
        super(e);
    }

}
