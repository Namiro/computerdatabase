/**
 *
 */
package com.excilys.burleon.computerdatabase.console.exception;

/**
 * @author Junior Burleon
 *
 */
public class UnauthorizedActionException extends RestClientException {

    /**
     *
     */
    private static final long serialVersionUID = -6409573896326623669L;

    /**
     *
     */
    public UnauthorizedActionException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public UnauthorizedActionException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public UnauthorizedActionException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public UnauthorizedActionException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
