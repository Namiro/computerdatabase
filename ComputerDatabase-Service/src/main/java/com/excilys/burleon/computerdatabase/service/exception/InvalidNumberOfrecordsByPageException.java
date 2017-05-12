package com.excilys.burleon.computerdatabase.service.exception;

import com.excilys.burleon.computerdatabase.service.exception.ServiceException;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class InvalidNumberOfrecordsByPageException extends ServiceException {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public InvalidNumberOfrecordsByPageException() {
        super();
    }

    /**
     * @param s
     *            Message to display
     */
    public InvalidNumberOfrecordsByPageException(final String s) {
        super(s);
    }

    /**
     * @param s
     *            Message to display
     * @param e
     *            Previous exception
     */
    public InvalidNumberOfrecordsByPageException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     *            Previous exception
     */
    public InvalidNumberOfrecordsByPageException(final Throwable e) {
        super(e);
    }
}
