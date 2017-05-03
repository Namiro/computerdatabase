/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates and open
 * the template in the editor.
 */
package com.excilys.burleon.computerdatabase.webapp.servlet;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.webapp.constant.Data;
import com.excilys.burleon.computerdatabase.webapp.constant.View;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.CompanyDTO;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.ComputerDTO;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.mapper.CompanyMapper;
import com.excilys.burleon.computerdatabase.webapp.dtomodel.mapper.ComputerMapper;
import com.excilys.burleon.computerdatabase.webapp.iservlet.IHttpServlet;
import com.excilys.burleon.computerdatabase.webapp.servlet.util.ProcessResult;
import com.google.gson.Gson;

/**
 *
 * @author Junior
 */
@Controller
@RequestMapping("/" + View.VIEW_COMPUTER_MANAGE)
public class ComputerManageServlet implements IHttpServlet {

    /**
     * Represent the working variable that we can receive or send with a
     * request.
     *
     */
    private class ProcessVariables {
        public ComputerDTO computer;
        public ComputerDTO computerReceived;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerListServlet.class);

    @Autowired
    public IComputerService computerService;

    @Autowired
    public ICompanyService companyService;

    /* METHODE */

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(final ModelMap model, @RequestParam final Map<String, String> params) {
        ComputerManageServlet.LOGGER.trace("GET /ComputerManage \t");

        final ProcessVariables processVariables = this.getProcessVariables(params);

        /* If a computer was selected */
        if (StringUtils.isNotBlank(processVariables.computerReceived.id)) {
            final long id = Long.parseLong(processVariables.computerReceived.id);
            final Optional<Computer> computerOpt = this.computerService.get(Computer.class, id);
            /* If the computer exist, we get its data */
            if (computerOpt.isPresent()) {
                processVariables.computer = ComputerMapper.toComputerDTO(computerOpt.get());
            } else {
                return "redirect:/" + View.VIEW_COMPUTER_MANAGE;
            }
        }

        this.populateModel(model, processVariables);

        return View.VIEW_COMPUTER_MANAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(final ModelMap model, @RequestParam final Map<String, String> params) {
        ComputerManageServlet.LOGGER.trace("POST /ComputerManage \t");

        final ProcessVariables processVariables = this.getProcessVariables(params);

        // Creation of computer from user entries
        ProcessResult processResult = this.initializeComputersProcess(processVariables);
        if (!processResult.isSuccess) {
            this.populateModel(model, processVariables, processResult);
            return View.VIEW_COMPUTER_MANAGE;
        }

        // Delete of computer if asked
        if (params.get(Data.SUBMIT_DELETE) != null) {
            processResult = this.removeComputersProcess(processVariables);
            this.populateModel(model, processVariables, processResult);
            if (processResult.isSuccess) {
                return View.VIEW_COMPUTER_LIST;
            } else {
                return View.VIEW_COMPUTER_MANAGE;
            }
        } else { // Save of computer if not delete
            processResult = this.saveComputerProcess(processVariables);
            this.populateModel(model, processVariables, processResult);
            return View.VIEW_COMPUTER_MANAGE;
        }
    }

    @Override
    public ProcessVariables getProcessVariables(final Map<String, String> params) {
        final ProcessVariables processVariables = new ProcessVariables();

        processVariables.computerReceived = new ComputerDTO();
        processVariables.computerReceived.id = params.get(Data.COMPUTER_ID);
        processVariables.computerReceived.name = params.get(Data.COMPUTER_NAME);
        processVariables.computerReceived.introduced = params.get(Data.COMPUTER_INTRODUCE_DATE);
        processVariables.computerReceived.discontinued = params.get(Data.COMPUTER_DISCONTINUE_DATE);
        processVariables.computerReceived.company = new CompanyDTO(params.get(Data.COMPUTER_COMPANY_ID), "");

        // If it is an updating or deleting
        if (StringUtils.isNotBlank(processVariables.computerReceived.getId())) {
            processVariables.computer = ComputerMapper.toComputerDTO(this.computerService
                    .get(Computer.class, Long.parseLong(processVariables.computerReceived.getId())).get());
        } else { // If it is a creating
            processVariables.computer = new ComputerDTO();
        }

        ComputerManageServlet.LOGGER.trace("getProcessVariables : " + new Gson().toJson(processVariables));
        return processVariables;
    }

    /**
     * Allow to update the computer object.
     *
     * @param processVariables
     *            The process variables
     *
     * @return The process results
     */
    private ProcessResult initializeComputersProcess(final ProcessVariables processVariables) {
        try {
            processVariables.computer.id = processVariables.computerReceived.id;
            processVariables.computer.name = processVariables.computerReceived.name;
            processVariables.computer.introduced = processVariables.computerReceived.introduced;
            processVariables.computer.discontinued = processVariables.computerReceived.discontinued;
            if (processVariables.computerReceived.company.id != null) {
                final Optional<Company> companyOpt = this.companyService.get(Company.class,
                        Long.parseLong(processVariables.computerReceived.company.id));
                if (companyOpt.isPresent()) {
                    final CompanyDTO companyDTO = CompanyMapper.toCompanyDTO(companyOpt.get());
                    processVariables.computer.setCompany(companyDTO);
                }
            }
            ComputerManageServlet.LOGGER
                    .trace("Construct OK for " + new Gson().toJson(processVariables.computerReceived));
            return new ProcessResult(true, "Construct OK");
        } catch (final ServiceException e) {
            ComputerManageServlet.LOGGER.warn("Impossible to construct a computer with the user entries", e);
            return new ProcessResult(false, e.getMessage());
        }
    }

    @Override
    public void populateModel(final ModelMap model, final Object processVariables,
            final ProcessResult processResult) {
        IHttpServlet.super.populateModel(model, processVariables, processResult);
        final ProcessVariables _processVariables = (ProcessVariables) processVariables;

        // If we have information about a computer
        if (_processVariables.computer != null) {
            model.addAttribute(Data.COMPUTER_ID, _processVariables.computer.id);
            if (StringUtils.isNotBlank(_processVariables.computer.introduced)) {
                model.addAttribute(Data.COMPUTER_INTRODUCE_DATE, _processVariables.computer.introduced);
            }
            if (StringUtils.isNotBlank(_processVariables.computer.discontinued)) {
                model.addAttribute(Data.COMPUTER_DISCONTINUE_DATE, _processVariables.computer.discontinued);
            }
            if (StringUtils.isNotBlank(_processVariables.computer.name)) {
                model.addAttribute(Data.COMPUTER_NAME, _processVariables.computer.name);
            }
            if (_processVariables.computer.company != null
                    && StringUtils.isNotBlank(_processVariables.computer.company.id)) {
                model.addAttribute(Data.COMPUTER_COMPANY_ID, _processVariables.computer.company.id);
            }

        }

        model.addAttribute(Data.LIST_COMPANY, CompanyMapper.toCompanyDTO(this.companyService.get(Company.class)));
    }

    /**
     * Allow to remove the computer object.
     *
     * @param processVariables
     *            The process variables
     *
     * @return The process results
     */
    private ProcessResult removeComputersProcess(final ProcessVariables processVariables) {
        try {
            ComputerManageServlet.LOGGER.info("Remove OK for " + processVariables.computerReceived);
            return new ProcessResult(
                    this.computerService.remove(ComputerMapper.toComputer(processVariables.computer)),
                    "Remove OK");
        } catch (final ServiceException e) {
            ComputerManageServlet.LOGGER.warn("Remove KO for " + processVariables.computerReceived, e);
            return new ProcessResult(false, e.getMessage());
        }
    }

    /**
     * Allow to save a computer.
     *
     * @param processVariables
     *            The process variables
     *
     * @return The process results
     */
    private ProcessResult saveComputerProcess(final ProcessVariables processVariables) {
        try {
            Computer computer = ComputerMapper.toComputer(processVariables.computer);
            computer = this.computerService.save(computer).get();
            processVariables.computer = ComputerMapper.toComputerDTO(computer);
            ComputerManageServlet.LOGGER.info("Save OK for " + processVariables.computer);
            return new ProcessResult(true, "Save OK");
        } catch (final ServiceException e) {
            ComputerManageServlet.LOGGER.warn("Impossible to save computer : " + processVariables.computer, e);
            return new ProcessResult(false, e.getMessage());
        }
    }
}
