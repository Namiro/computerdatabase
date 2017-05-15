/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Junior Burleon
 *
 */
public class TooLongCompanyNameException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = 1599016079663446373L;

    /**
     *
     */
    public TooLongCompanyNameException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public TooLongCompanyNameException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public TooLongCompanyNameException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public TooLongCompanyNameException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
