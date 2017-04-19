package com.excilys.burleon.computerdatabase.view.web.servlet.util;

/**
 * This represent the process result.
 *
 * A process is the traitement when the services are called by the servlet.
 *
 */
public class ProcessResult {
    public boolean isSuccess = false;
    public String message = "The process result wasn't initialized";
    public Object object = null;

    /**
     * The default constructor.
     */
    public ProcessResult() {
    }

    /**
     * The full constructor.
     *
     * @param success
     *            Process succeed or not
     * @param object
     *            The object you want to access later
     */
    public ProcessResult(final boolean success, final Object object) {
        this.isSuccess = success;
        this.message = "Just an object was gaven";
        this.object = object;
    }

    /**
     * The full constructor.
     *
     * @param success
     *            Process succeed or not
     * @param message
     *            The message returned by the process
     */
    public ProcessResult(final boolean success, final String message) {
        this.isSuccess = success;
        this.message = message;
    }

    /**
     * The full constructor.
     *
     * @param success
     *            Process succeed or not
     * @param message
     *            The message returned by the process
     * @param object
     *            The object you want to access later
     */
    public ProcessResult(final boolean success, final String message, final Object object) {
        this.isSuccess = success;
        this.message = message;
        this.object = object;
    }
}