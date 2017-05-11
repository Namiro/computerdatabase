/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.authentication;

/**
 * @author Junior Burleon
 *
 */
public class InvalidPasswordException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -4508797879755189101L;

    /**
     *
     */
    public InvalidPasswordException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public InvalidPasswordException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public InvalidPasswordException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public InvalidPasswordException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
