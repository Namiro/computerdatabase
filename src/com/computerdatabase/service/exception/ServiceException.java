package com.computerdatabase.service.exception;

/**
 * This class allow to trigger an exception for the services
 *
 * @author Junior Burleon
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {
	public ServiceException() {
		super();
	}

	/**
	 *
	 * @param s
	 *            Message to display
	 */
	public ServiceException(final String s) {
		super(s);
	}

	/**
	 *
	 * @param s
	 *            Message to display
	 * @param e
	 *            Previous excpetion
	 */
	public ServiceException(final String s, final Throwable e) {
		super(s, e);
	}

	/**
	 *
	 * @param e
	 *            Previous excpetion
	 */
	public ServiceException(final Throwable e) {
		super(e);
	}

}
