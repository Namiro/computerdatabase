/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Junior Burleon
 *
 */
public class TooShortCompanyNameException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = -847284381900538978L;

    /**
     *
     */
    public TooShortCompanyNameException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public TooShortCompanyNameException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public TooShortCompanyNameException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public TooShortCompanyNameException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
