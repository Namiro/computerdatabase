/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Corentin Arnaud
 *
 */
public class InvalidDateException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = -4809162430609957052L;

    /**
     *
     */
    public InvalidDateException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public InvalidDateException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public InvalidDateException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public InvalidDateException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
