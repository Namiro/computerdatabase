package com.excilys.burleon.computerdatabase.webapp.controller;

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
import com.excilys.burleon.computerdatabase.core.model.User;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderComputerEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.service.iservice.IUserService;
import com.excilys.burleon.computerdatabase.webapp.constant.Data;
import com.excilys.burleon.computerdatabase.webapp.constant.View;
import com.excilys.burleon.computerdatabase.webapp.controller.util.ProcessResult;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.mapper.ComputerMapper;
import com.excilys.burleon.computerdatabase.webapp.icontroller.IController;
import com.google.gson.Gson;

/**
 *
 * @author Junior Burléon
 */
@Controller
@RequestMapping(value = { "/", "/" + View.VIEW_COMPUTER_LIST })
public class ComputerListController implements IController {

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
        public String username = "";
        public String password = "";
        public String passworrepeated = "";
        public String popup = "";
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerListController.class);

    @Autowired
    private IPageService<Computer> pageService;

    @Autowired
    private IComputerService computerService;

    @Autowired
    private IUserService userService;

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
            ComputerListController.LOGGER.info("Remove OK for : " + Arrays.toString(split));
            return new ProcessResult(true, "Remove OK");
        } catch (final ServiceException e) {
            ComputerListController.LOGGER.warn("Impossible to delete the computers", e);
            return new ProcessResult(false, e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(final ModelMap model, @RequestParam final Map<String, String> params) {
        ComputerListController.LOGGER.trace("GET /ComputerList \t");

        final ProcessVariables processVariables = this.getProcessVariables(params);
        this.pageService.setModelService(this.computerService);
        this.pageService.setRecordsByPage(processVariables.recordsByPage);
        this.pageService.setFilterWord(processVariables.filterWord);
        this.pageService.setOrderBy(processVariables.orderBy);
        processVariables.listComputer = this.pageService.page(Computer.class, processVariables.newCurrentPage);

        this.populateModel(model, processVariables);
        return View.VIEW_COMPUTER_LIST;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(final ModelMap model, @RequestParam final Map<String, String> params) {
        ComputerListController.LOGGER.trace("POST /ComputerList \t");

        this.pageService.setModelService(this.computerService);
        final ProcessVariables processVariables = this.getProcessVariables(params);
        ProcessResult processResult = new ProcessResult();
        // If it is a deleting
        if (params.get(Data.SUBMIT_DELETE) != null) {
            ComputerListController.LOGGER.trace("POST /ComputerList \t SUBMIT_DELETE");
            processResult = this.deleteComputersProcess(processVariables.split);
        }
        // If it is a login
        else if (params.get(Data.SUBMIT_LOGIN) != null) {
            ComputerListController.LOGGER.trace("POST /ComputerList \t SUBMIT_LOGIN");
            processResult = this.loginProcess(processVariables);
            if (!processResult.isSuccess) {
                processVariables.popup = Data.POPUP_LOGIN;
            }
        }
        // If it is a sign up
        else if (params.get(Data.SUBMIT_SIGNUP) != null) {
            ComputerListController.LOGGER.trace("POST /ComputerList \t SUBMIT_SIGNUP");
            processResult = this.signupProcess(processVariables);
            if (!processResult.isSuccess) {
                processVariables.popup = Data.POPUP_SIGNUP;
            }
        }

        processVariables.listComputer = this.pageService.page(Computer.class, processVariables.newCurrentPage);
        this.populateModel(model, processVariables, processResult);
        return View.VIEW_COMPUTER_LIST;
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
        if (params.get(Data.USER_USERNAME) != null) {
            processVariables.username = params.get(Data.USER_USERNAME);
        }
        if (params.get(Data.USER_PASSWORD) != null) {
            processVariables.password = params.get(Data.USER_PASSWORD);
        }
        if (params.get(Data.USER_PASSWORD2) != null) {
            processVariables.passworrepeated = params.get(Data.USER_PASSWORD2);
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

        ComputerListController.LOGGER.trace("getProcessVariables : " + new Gson().toJson(processVariables));
        return processVariables;
    }

    private ProcessResult loginProcess(final ProcessVariables processVariables) {
        try {
            final User user = this.userService.login(new User.UserBuilder().username(processVariables.username)
                    .password(processVariables.password).build());
            return new ProcessResult(true, "Login successful", user);
        } catch (final ServiceException e) {
            ComputerListController.LOGGER.info(e.getMessage());
            return new ProcessResult(false, "Login fail. " + e.getMessage());
        }
    }

    @Override
    public void populateModel(final ModelMap model, final Object processVariables,
            final ProcessResult processResult) {
        ComputerListController.LOGGER.trace("populateModel /ComputerList \t");
        IController.super.populateModel(model, processVariables, processResult);

        final ProcessVariables _processVariables = (ProcessVariables) processVariables;
        model.addAttribute(Data.LIST_COMPUTER, ComputerMapper.toDto(_processVariables.listComputer));
        model.addAttribute(Data.SEARCH_NUMBER_RESULTS, this.computerService.getTotalRecords(Computer.class, _processVariables.filterWord));
        switch(_processVariables.orderBy) {
			default:
        	case NAME : model.addAttribute(Data.ORDER_BY, Data.ORDER_BY_1); break;
        	case INTRODUCE_DATE : model.addAttribute(Data.ORDER_BY, Data.ORDER_BY_2); break;
        	case DISCONTINUE_DATE : model.addAttribute(Data.ORDER_BY, Data.ORDER_BY_3); break;
        	case COMPANY_NAME : model.addAttribute(Data.ORDER_BY, Data.ORDER_BY_4); break;
        }
        model.addAttribute(Data.PAGINATION_CURRENT_PAGE, this.pageService.getPageNumber());
        model.addAttribute(Data.PAGINATION_TOTAL_PAGE, this.pageService.getMaxPageNumber(Computer.class));
        model.addAttribute(Data.PAGINATION_RECORDS_BY_PAGE, _processVariables.recordsByPage);
        model.addAttribute(Data.SEARCH_WORD, _processVariables.filterWord);
        model.addAttribute(Data.POPUP, _processVariables.popup);
    }

    private ProcessResult signupProcess(final ProcessVariables processVariables) {
        try {
            final User user = this.userService.register(new User.UserBuilder().username(processVariables.username)
                    .password(processVariables.password).build(), processVariables.passworrepeated);
            return new ProcessResult(true, "Signup successful", user);
        } catch (final ServiceException e) {
            ComputerListController.LOGGER.info(e.getMessage());
            return new ProcessResult(false, "Signup fail. " + e.getMessage());
        }
    }
}