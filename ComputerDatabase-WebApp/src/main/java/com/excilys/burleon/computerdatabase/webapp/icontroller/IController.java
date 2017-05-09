package com.excilys.burleon.computerdatabase.webapp.icontroller;

import java.util.Map;

import org.springframework.ui.ModelMap;

import com.excilys.burleon.computerdatabase.webapp.constant.Data;
import com.excilys.burleon.computerdatabase.webapp.controller.util.ProcessResult;

public interface IController {

    /**
     * Allow to get the parameters from the JSP.
     *
     * @param params
     *            The params
     * @return
     * @return Return the working variable initialized in function of the data
     *         received in the request.
     */
    Object getProcessVariables(final Map<String, String> params);

    /**
     * Allow to set the parameters for the JSP.
     *
     * @param model
     *            The model
     * @param processVariables
     *            The working variables
     */
    default void populateModel(final ModelMap model, final Object processVariables) {
        this.populateModel(model, processVariables, null);
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
    default void populateModel(final ModelMap model, final Object processVariables,
            final ProcessResult processResult) {

        if (processResult != null) {
            if (processResult.isSuccess) {
                model.addAttribute(Data.MESSAGE_SUCCESS, processResult.message);
            } else {
                model.addAttribute(Data.MESSAGE_ERROR, processResult.message);
            }
        }
    }
}