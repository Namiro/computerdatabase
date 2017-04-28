
package com.excilys.burleon.computerdatabase.webapp.servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.webapp.constant.Data;
import com.excilys.burleon.computerdatabase.webapp.constant.Servlet;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.mapper.ComputerMapper;
import com.excilys.burleon.computerdatabase.webapp.iservlet.IHttpServlet;
import com.excilys.burleon.computerdatabase.webapp.servlet.util.ProcessResult;
import com.google.gson.Gson;

/**
 *
 * @author Junior Burléon
 */
@Controller
@RequestMapping("/" + Servlet.SERVLET_COMPUTER_LIST)
public class ComputerListServlet implements IHttpServlet {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerListServlet.class);

    @Autowired
    private IPageService<Computer> pageService;

    @Autowired
    private IComputerService computerService;

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
            ComputerListServlet.LOGGER.info("Remove OK for : " + Arrays.toString(split));
            return new ProcessResult(true, "Remove OK");
        } catch (final ServiceException e) {
            ComputerListServlet.LOGGER.warn("Impossible to delete the computers", e);
            return new ProcessResult(false, e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(final ModelMap model, @RequestParam final Map<String, String> params) {
        ComputerListServlet.LOGGER.trace("GET /ComputerList \t");

        final ProcessVariables processVariables = this.getProcessVariables(params);
        this.pageService.setModelService(this.computerService);
        this.pageService.setRecordsByPage(processVariables.recordsByPage);
        this.pageService.setFilterWord(processVariables.filterWord);
        this.pageService.setOrderBy(processVariables.orderBy);
        processVariables.listComputer = this.pageService.page(Computer.class, processVariables.newCurrentPage);

        this.populateModel(model, processVariables);
        return Servlet.SERVLET_COMPUTER_LIST;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(final ModelMap model, @RequestParam final Map<String, String> params) {
        ComputerListServlet.LOGGER.trace("POST /ComputerList \t");

        this.pageService.setModelService(this.computerService);
        final ProcessVariables processVariables = this.getProcessVariables(params);

        ProcessResult processResult = new ProcessResult();

        // If it is a deleting
        if (model.get(Data.SUBMIT_DELETE) != null) {
            processResult = this.deleteComputersProcess(processVariables.split);
        }

        processVariables.listComputer = this.pageService.page(Computer.class, processVariables.newCurrentPage);
        this.populateModel(model, processVariables, processResult);
        return Servlet.SERVLET_COMPUTER_LIST;
    }

    @Override
    public ProcessVariables getProcessVariables(final Map<String, String> params) {
        final ProcessVariables processVariables = new ProcessVariables();
        if (params.get(Data.PAGINATION_RECORDS_BY_PAGE) != null) {
            processVariables.recordsByPage = Integer.valueOf(params.get(Data.PAGINATION_RECORDS_BY_PAGE));
        }
        if (params.get(Data.SEARCH_WORD) != null) {
            processVariables.filterWord = params.get(Data.SEARCH_WORD);
        }
        if (params.get(Data.SUBMIT_DELETE) != null) {
            processVariables.split = params.get(Data.SUBMIT_DELETE).split(",");
        }
        processVariables.newCurrentPage = (params.get(Data.PAGINATION_CURRENT_PAGE) != null)
                ? Integer.parseInt(params.get(Data.PAGINATION_CURRENT_PAGE)) : 1;
        if (params.get(Data.SUBMIT_SEARCH) != null) {
            processVariables.newCurrentPage = 1;
        }
        if (params.get(Data.ORDER_BY) != null) {
            switch (params.get(Data.ORDER_BY)) {
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

        processVariables.recordsByPage = (params.get(Data.PAGINATION_RECORDS_BY_PAGE) != null)
                ? Integer.parseInt(params.get(Data.PAGINATION_RECORDS_BY_PAGE)) : processVariables.recordsByPage;

        ComputerListServlet.LOGGER.trace("getProcessVariables : " + new Gson().toJson(processVariables));
        return processVariables;
    }

    @Override
    public void populateModel(final ModelMap model, final Object processVariables,
            final ProcessResult processResult) {
        IHttpServlet.super.populateModel(model, processVariables, processResult);

        final ProcessVariables _processVariables = (ProcessVariables) processVariables;
        model.addAttribute(Data.LIST_COMPUTER, ComputerMapper.toComputerDTO(_processVariables.listComputer));
        model.addAttribute(Data.SEARCH_NUMBER_RESULTS,
                this.computerService.getTotalRecords(Computer.class, _processVariables.filterWord));
        model.addAttribute(Data.PAGINATION_CURRENT_PAGE, this.pageService.getPageNumber());
        model.addAttribute(Data.PAGINATION_TOTAL_PAGE, this.pageService.getMaxPageNumber(Computer.class));
        model.addAttribute(Data.PAGINATION_RECORDS_BY_PAGE, _processVariables.recordsByPage);
        model.addAttribute(Data.SEARCH_WORD, _processVariables.filterWord);
    }
}