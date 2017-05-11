/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Junior Burleon
 *
 */
public class InvalidDateOrderException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = -4809162430609957052L;

    /**
     *
     */
    public InvalidDateOrderException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public InvalidDateOrderException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public InvalidDateOrderException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public InvalidDateOrderException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
