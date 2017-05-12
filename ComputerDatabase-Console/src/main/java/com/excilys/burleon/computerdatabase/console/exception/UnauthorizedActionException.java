/**
 * 
 */
package com.excilys.burleon.computerdatabase.console.exception;

/**
 * @author Junior Burleon
 *
 */
public class UnauthorizedActionException extends RestClientException {

    /**
     * 
     */
    public UnauthorizedActionException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     */
    public UnauthorizedActionException(String s) {
        super(s);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param s
     * @param e
     */
    public UnauthorizedActionException(String s, Throwable e) {
        super(s, e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public UnauthorizedActionException(Throwable e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

}
