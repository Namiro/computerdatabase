package com.excilys.burleon.computerdatabase.service.exception;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class RequiredServiceException extends ServiceException {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public RequiredServiceException() {
        super();
    }

    /**
     * @param s
     *            Message to display
     */
    public RequiredServiceException(final String s) {
        super(s);
    }

    /**
     * @param s
     *            Message to display
     * @param e
     *            Previous exception
     */
    public RequiredServiceException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     *            Previous exception
     */
    public RequiredServiceException(final Throwable e) {
        super(e);
    }
}
