/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Junior Burleon
 *
 */
public class TooShortPasswordException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = -1031799457482980790L;

    /**
     *
     */
    public TooShortPasswordException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public TooShortPasswordException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public TooShortPasswordException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public TooShortPasswordException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
