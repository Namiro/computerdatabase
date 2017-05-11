/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

/**
 * @author Junior Burleon
 *
 */
public class TooLongComputerNameException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = -7957693396147112054L;

    /**
     *
     */
    public TooLongComputerNameException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public TooLongComputerNameException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public TooLongComputerNameException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public TooLongComputerNameException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
