/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.authentication;

/**
 * @author Junior Burleon
 *
 */
public class UsernameNotFoundException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = 153445465746746L;

    /**
     *
     */
    public UsernameNotFoundException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public UsernameNotFoundException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public UsernameNotFoundException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public UsernameNotFoundException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
