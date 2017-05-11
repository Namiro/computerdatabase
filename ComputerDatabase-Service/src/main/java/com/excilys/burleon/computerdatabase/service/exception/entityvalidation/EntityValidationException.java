package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

import com.excilys.burleon.computerdatabase.service.exception.ServiceException;

/**
 * This class allow to trigger an exception for the services.
 *
 * @author Junior Burleon
 *
 */
public class EntityValidationException extends ServiceException {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public EntityValidationException() {
        super();
    }

    /**
     * @param s
     *            Message to display
     */
    public EntityValidationException(final String s) {
        super(s);
    }

    /**
     * @param s
     *            Message to display
     * @param e
     *            Previous exception
     */
    public EntityValidationException(final String s, final Throwable e) {
        super(s, e);
    }

    /**
     * @param e
     *            Previous exception
     */
    public EntityValidationException(final Throwable e) {
        super(e);
    }
}
