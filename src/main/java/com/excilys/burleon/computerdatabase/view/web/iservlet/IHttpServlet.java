package com.excilys.burleon.computerdatabase.view.web.iservlet;

import javax.servlet.http.HttpServletRequest;

import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.servlet.util.ProcessResult;

public interface IHttpServlet {

    /**
     * Allow to get the parameters from the JSP.
     *
     * @param request
     *            The request
     * @return
     * @return Return the working variable initialized in function of the data
     *         received in the request.
     */
    Object getProcessVariables(final HttpServletRequest request);

    /**
     * Allow to set the parameters for the JSP.
     *
     * @param request
     *            The request
     * @param processVariables
     *            The working variables
     */
    default void populateRequest(final HttpServletRequest request, final Object processVariables) {
        this.populateRequest(request, processVariables, null);
    }

    /**
     * Allow to set the parameters for the JSP.
     *
     * @param request
     *            The request
     * @param processVariables
     *            The working variables
     * @param processResult
     *            The result of a process
     */
    default void populateRequest(final HttpServletRequest request, final Object processVariables,
            final ProcessResult processResult) {

        if (processResult != null) {
            if (processResult.isSuccess) {
                request.setAttribute(Data.MESSAGE_SUCCESS, processResult.message);
            } else {
                request.setAttribute(Data.MESSAGE_ERROR, processResult.message);
            }
        }
    }
}