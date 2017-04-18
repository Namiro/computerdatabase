package com.excilys.burleon.computerdatabase.view.web.servlet.util;

/**
 * This represent the process result.
 *
 * A process is the traitement when the services are called by the servlet.
 *
 */
public class ProcessResult {
    public boolean success = false;
    public String message = "The process result wasn't initialized";

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
     * @param message
     *            The message returned by the process
     */
    public ProcessResult(final boolean success, final String message) {
        this.success = success;
        this.message = message;
    }
}