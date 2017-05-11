package com.excilys.burleon.computerdatabase.service.exception.entityvalidation;

public class EntityWithNegativeIdServiceException extends EntityValidationException {

    /**
     *
     */
    private static final long serialVersionUID = 6404270209221381674L;

    public EntityWithNegativeIdServiceException() {
    }

    public EntityWithNegativeIdServiceException(final String s) {
        super(s);
    }

    public EntityWithNegativeIdServiceException(final String s, final Throwable e) {
        super(s, e);
    }

    public EntityWithNegativeIdServiceException(final Throwable e) {
        super(e);
    }

}
