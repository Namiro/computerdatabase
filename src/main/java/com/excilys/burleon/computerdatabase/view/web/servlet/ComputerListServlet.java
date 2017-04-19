
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.service.service.ComputerService;
import com.excilys.burleon.computerdatabase.service.service.PageService;
import com.excilys.burleon.computerdatabase.view.model.mapper.ComputerMapper;
import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;
import com.excilys.burleon.computerdatabase.view.web.iservlet.IHttpServlet;
import com.excilys.burleon.computerdatabase.view.web.servlet.util.ProcessResult;

/**
 *
 * @author Junior Burléon
 */
@WebServlet("/ComputerList")
public class ComputerListServlet extends HttpServlet implements IHttpServlet {

    /**
     * Represent the working variable that we can receive or send with a
     * request.
     *
     */
    private class ProcessVariables {
        public String filterWord = "";
        public OrderComputerEnum orderBy = OrderComputerEnum.NAME;
        public int recordsByPage = 20;
        public int newCurrentPage = 1;
        public List<Computer> listComputer;
        public String[] split;
    }

    private static final long serialVersionUID = -6681257837248708119L;
    Logger LOGGER = LoggerFactory.getLogger(ComputerListServlet.class);
    private final IPageService<Computer> pageService = new PageService<>(Computer.class, 20);

    private final IComputerService computerService = new ComputerService();

    /**
     * Allow to delete the computer.
     *
     * @param split
     *            The list of computer ids that should be deleted
     */
    private ProcessResult deleteComputersProcess(final String[] split) {
        try {
            for (final String idStr : split) {
                final Optional<
                        Computer> computerOpt = this.computerService.get(Computer.class, Long.parseLong(idStr));
                if (computerOpt.isPresent()) {
                    this.computerService.remove(computerOpt.get());
                }
            }
            this.LOGGER.info("Remove OK for : " + Arrays.toString(split));
            return new ProcessResult(true, "Remove OK");
        } catch (final ServiceException e) {
            this.LOGGER.warn("Impossible to delete the computers", e);
            return new ProcessResult(false, e.getMessage());
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.LOGGER.trace("GET /ComputerList \t" + request.getRequestURI());

        final ProcessVariables processVariables = this.getProcessVariables(request);

        this.pageService.setRecordsByPage(processVariables.recordsByPage);
        this.pageService.setFilterWord(processVariables.filterWord);
        this.pageService.setOrderBy(processVariables.orderBy);
        processVariables.listComputer = this.pageService.page(processVariables.newCurrentPage);

        this.populateRequest(request, processVariables);

        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.LOGGER.trace("POST /ComputerList \t" + request.getRequestURI());

        final ProcessVariables processVariables = this.getProcessVariables(request);

        ProcessResult processResult = new ProcessResult();

        // If it is a deleting
        if (request.getParameter(Data.SUBMIT_DELETE) != null) {
            processResult = this.deleteComputersProcess(processVariables.split);
        }

        processVariables.listComputer = this.pageService.page(processVariables.newCurrentPage);
        this.populateRequest(request, processVariables, processResult);
        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request, response);
    }

    @Override
    public ProcessVariables getProcessVariables(final HttpServletRequest request) {
        final ProcessVariables processVariables = new ProcessVariables();
        if (request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE) != null) {
            request.setAttribute(Data.PAGINATION_RECORDS_BY_PAGE,
                    request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE));
        }
        if (request.getParameter(Data.SEARCH_WORD) != null) {
            request.setAttribute(Data.SEARCH_WORD, request.getParameter(Data.SEARCH_WORD));
        }
        if (request.getParameter(Data.ORDER_BY) != null) {
            request.setAttribute(Data.ORDER_BY, request.getParameter(Data.ORDER_BY));
        }
        if (request.getAttribute(Data.PAGINATION_RECORDS_BY_PAGE) != null) {
            processVariables.recordsByPage = Integer
                    .valueOf((String) request.getAttribute(Data.PAGINATION_RECORDS_BY_PAGE));
        }
        if (request.getParameter(Data.SUBMIT_DELETE) != null) {
            processVariables.split = request.getParameter(Data.SUBMIT_DELETE).split(",");
        }
        processVariables.newCurrentPage = (request.getParameter(Data.PAGINATION_CURRENT_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_CURRENT_PAGE)) : 1;
        if (request.getParameter(Data.SUBMIT_SEARCH) != null) {
            processVariables.newCurrentPage = 1;
        }
        if (request.getAttribute(Data.ORDER_BY) != null) {
            switch ((String) request.getAttribute(Data.ORDER_BY)) {
                case Data.ORDER_BY_1:
                    processVariables.orderBy = OrderComputerEnum.NAME;
                    break;
                case Data.ORDER_BY_2:
                    processVariables.orderBy = OrderComputerEnum.INTRODUCE_DATE;
                    break;
                case Data.ORDER_BY_3:
                    processVariables.orderBy = OrderComputerEnum.DISCONTINUE_DATE;
                    break;
                case Data.ORDER_BY_4:
                    processVariables.orderBy = OrderComputerEnum.COMPANY_NAME;
                    break;
                default:
                    break;
            }
        }

        processVariables.filterWord = (String) request.getAttribute(Data.SEARCH_WORD);

        processVariables.recordsByPage = (request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE))
                : processVariables.recordsByPage;

        return processVariables;
    }

    @Override
    public void populateRequest(final HttpServletRequest request, final Object processVariables,
            final ProcessResult processResult) {
        IHttpServlet.super.populateRequest(request, processVariables, processResult);

        final ProcessVariables _processVariables = (ProcessVariables) processVariables;
        request.setAttribute(Data.LIST_COMPUTER, ComputerMapper.toComputerDTO(_processVariables.listComputer));
        request.setAttribute(Data.SEARCH_NUMBER_RESULTS,
                this.computerService.getTotalRecords(Computer.class, _processVariables.filterWord));
        request.setAttribute(Data.PAGINATION_CURRENT_PAGE, this.pageService.getPageNumber());
        request.setAttribute(Data.PAGINATION_TOTAL_PAGE, this.pageService.getMaxPageNumber());
        request.setAttribute(Data.PAGINATION_RECORDS_BY_PAGE, _processVariables.recordsByPage);
        request.setAttribute(Data.SEARCH_WORD, _processVariables.filterWord);
    }
}