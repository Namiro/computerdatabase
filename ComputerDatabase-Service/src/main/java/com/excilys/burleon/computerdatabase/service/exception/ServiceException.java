package com.excilys.burleon.computerdatabase.service.exception;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class ServiceException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -4026635148559085053L;

    /**
     * Default constructor.
     */
    public ServiceException() {
        super();
    }

    /**
     * @param s
     *            Message to display
     */
    public ServiceException(final String s) {
        super(s);
    }

    /**
     * @param s
     *            Message to display
     * @param e
     *            Previous exception
     */
    public ServiceException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     *            Previous exception
     */
    public ServiceException(final Throwable e) {
        super(e);
    }
}
