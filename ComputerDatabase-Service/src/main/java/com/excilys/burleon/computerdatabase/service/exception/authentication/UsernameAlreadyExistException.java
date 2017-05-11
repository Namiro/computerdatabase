/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.authentication;

/**
 * @author Junior Burleon
 *
 */
public class UsernameAlreadyExistException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = 6147221344588037114L;

    /**
     *
     */
    public UsernameAlreadyExistException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public UsernameAlreadyExistException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public UsernameAlreadyExistException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public UsernameAlreadyExistException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
