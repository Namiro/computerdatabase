package com.excilys.burleon.computerdatabase.service.exception.authentication;

import com.excilys.burleon.computerdatabase.service.exception.ServiceException;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class AuthenticationException extends ServiceException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public AuthenticationException() {
        super();
    }

    /**
     * @param s
     *            Message to display
     */
    public AuthenticationException(final String s) {
        super(s);
    }

    /**
     * @param s
     *            Message to display
     * @param e
     *            Previous exception
     */
    public AuthenticationException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     *            Previous exception
     */
    public AuthenticationException(final Throwable e) {
        super(e);
    }
}
