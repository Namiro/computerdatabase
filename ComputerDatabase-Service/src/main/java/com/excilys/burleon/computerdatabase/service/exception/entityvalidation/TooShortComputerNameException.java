/**
 *
 */
package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

import com.excilys.burleon.computerdatabase.service.exception.ServiceException;

/**
 * @author Junior Burleon
 *
 */
public class TooShortComputerNameException extends ServiceException {

    /**
     *
     */
    private static final long serialVersionUID = -2735306612762257425L;

    /**
     *
     */
    public TooShortComputerNameException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public TooShortComputerNameException(final String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public TooShortComputerNameException(final String s, final Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public TooShortComputerNameException(final Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
