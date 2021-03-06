package com.excilys.burleon.computerdatabase.console.exception;

/**
 * This class allow to trigger an exception for the rest client.
 *
 * @author Junior Burleon
 *
 */
public class RestClientException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -4026635148559085053L;

    /**
     * Default constructor.
     */
    public RestClientException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public RestClientException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public RestClientException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public RestClientException(final Throwable e) {
        super(e);
    }

}
