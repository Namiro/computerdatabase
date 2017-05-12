package com.excilys.burleon.computerdatabase.console.exception;

/**
 * This class allow to trigger an exception for the rest client.
 *
 * @author Junior Burleon
 *
 */
public class UnexpectedResultException extends RestClientException {
    /**
     *
     */
    private static final long serialVersionUID = -4026635148559085053L;

    /**
     * Default constructor.
     */
    public UnexpectedResultException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public UnexpectedResultException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public UnexpectedResultException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public UnexpectedResultException(final Throwable e) {
        super(e);
    }

}
