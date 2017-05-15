package com.excilys.burleon.computerdatabase.console.exception;

/**
 * This class allow to trigger an exception for the rest client.
 *
 * @author Junior Burleon
 *
 */
public class NotFoundEntityException extends RestClientException {
    /**
     *
     */
    private static final long serialVersionUID = -4026635148559085053L;

    /**
     * Default constructor.
     */
    public NotFoundEntityException() {
        super();
    }

    /**
     *
     * @param s
     *            Message to display
     */
    public NotFoundEntityException(final String s) {
        super(s);
    }

    /**
     *
     * @param s
     *            Message to display
     * @param e
     *            Previous excpetion
     */
    public NotFoundEntityException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     *
     * @param e
     *            Previous excpetion
     */
    public NotFoundEntityException(final Throwable e) {
        super(e);
    }

}
