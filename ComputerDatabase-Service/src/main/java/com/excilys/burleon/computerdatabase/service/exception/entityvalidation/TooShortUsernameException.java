/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Junior Burleon
 *
 */
public class TooShortUsernameException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = 7469916844627192667L;

    /**
     *
     */
    public TooShortUsernameException() {
    }

    /**
     * @param s
     */
    public TooShortUsernameException(final String s) {
        super(s);
    }

    /**
     * @param s
     * @param e
     */
    public TooShortUsernameException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     */
    public TooShortUsernameException(final Throwable e) {
        super(e);
    }

}
