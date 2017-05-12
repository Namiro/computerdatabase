package com.excilys.burleon.computerdatabase.console.exception;

/**
 * This class allow to trigger an exception for the rest client.
 *
 * @author Junior Burleon
 *
 */
public class ServiceUnvailableException extends RestClientException {
    /**
     *
     */
    private static final long serialVersionUID = -4026635148559085053L;

    /**
     * Default constructor.
     */
    public ServiceUnvailableException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public ServiceUnvailableException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public ServiceUnvailableException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public ServiceUnvailableException(final Throwable e) {
        super(e);
    }

}
