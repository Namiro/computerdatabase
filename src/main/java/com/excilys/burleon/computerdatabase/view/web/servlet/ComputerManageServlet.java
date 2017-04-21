/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates and open
 * the template in the editor.
 */
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.burleon.computerdatabase.repository.model.Company;
import com.excilys.burleon.computerdatabase.repository.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.view.model.CompanyDTO;
import com.excilys.burleon.computerdatabase.view.model.ComputerDTO;
import com.excilys.burleon.computerdatabase.view.model.mapper.CompanyMapper;
import com.excilys.burleon.computerdatabase.view.model.mapper.ComputerMapper;
import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;
import com.excilys.burleon.computerdatabase.view.web.iservlet.IHttpServlet;
import com.excilys.burleon.computerdatabase.view.web.servlet.util.ProcessResult;

/**
 *
 * @author Junior
 */
@WebServlet("/ComputerManage")
@Controller
public class ComputerManageServlet extends HttpServlet implements IHttpServlet {

    /**
     * Represent the working variable that we can receive or send with a
     * request.
     *
     */
    private class ProcessVariables {
        public ComputerDTO computer;
        public ComputerDTO computerReceived;
    }

    private static final long serialVersionUID = -922272733938052338L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerListServlet.class);

    @Autowired
    public IComputerService computerService;

    @Autowired
    public ICompanyService companyService;

    /* METHODE */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ComputerManageServlet.LOGGER.trace("GET /ComputerManage \t" + request.getRequestURI());

        final ProcessVariables processVariables = this.getProcessVariables(request);

        /* If a computer was selected */
        if (StringUtils.isNotBlank(processVariables.computerReceived.id)) {
            final long id = Long.parseLong(processVariables.computerReceived.id);
            final Optional<Computer> computerOpt = this.computerService.get(Computer.class, id);
            /* If the computer exist, we get its data */
            if (computerOpt.isPresent()) {
                processVariables.computer = ComputerMapper.toComputerDTO(computerOpt.get());
            } else {
                response.sendRedirect(response.encodeRedirectURL(Servlet.SERVLET_COMPUTER_MANAGE));
                return;
            }
        }

        this.populateRequest(request, processVariables);

        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        ComputerManageServlet.LOGGER.trace("POST /ComputerManage \t" + request.getRequestURI());

        final ProcessVariables processVariables = this.getProcessVariables(request);
        ProcessResult processResult;

        // Creation of computer from user entries
        processResult = this.initializeComputersProcess(processVariables);
        if (!processResult.isSuccess) {
            this.populateRequest(request, processVariables, processResult);
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                    response);
        }

        // Delete of computer if asked
        if (request.getParameter(Data.SUBMIT_DELETE) != null) {
            processResult = this.removeComputersProcess(processVariables);
            this.populateRequest(request, processVariables, processResult);
            if (processResult.isSuccess) {
                this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request,
                        response);
            } else {
                this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                        response);
            }
            return;
        } else { // Save of computer if not delete
            processResult = this.saveComputerProcess(processVariables);
            this.populateRequest(request, processVariables, processResult);
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                    response);
        }
    }

    @Override
    public ProcessVariables getProcessVariables(final HttpServletRequest request) {
        final ProcessVariables processVariables = new ProcessVariables();

        processVariables.computerReceived = new ComputerDTO();
        processVariables.computerReceived.id = request.getParameter(Data.COMPUTER_ID);
        processVariables.computerReceived.name = request.getParameter(Data.COMPUTER_NAME);
        processVariables.computerReceived.introduced = request.getParameter(Data.COMPUTER_INTRODUCE_DATE);
        processVariables.computerReceived.discontinued = request.getParameter(Data.COMPUTER_DISCONTINUE_DATE);
        processVariables.computerReceived.company = new CompanyDTO(request.getParameter(Data.COMPUTER_COMPANY_ID),
                "");

        // If it is an updating or deleting
        if (StringUtils.isNotBlank(processVariables.computerReceived.getId())) {
            processVariables.computer = ComputerMapper.toComputerDTO(this.computerService
                    .get(Computer.class, Long.parseLong(processVariables.computerReceived.getId())).get());
        } else { // If it is a creating
            processVariables.computer = new ComputerDTO();
        }

        return processVariables;
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
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
            ComputerManageServlet.LOGGER.info("Construct OK for " + processVariables.computerReceived);
            return new ProcessResult(true, "Construct OK");
        } catch (final ServiceException e) {
            ComputerManageServlet.LOGGER.warn("Impossible to construct a computer with the user entries", e);
            return new ProcessResult(false, e.getMessage());
        }
    }

    @Override
    public void populateRequest(final HttpServletRequest request, final Object processVariables,
            final ProcessResult processResult) {
        IHttpServlet.super.populateRequest(request, processVariables, processResult);
        final ProcessVariables _processVariables = (ProcessVariables) processVariables;

        // If we have information about a computer
        if (_processVariables.computer != null) {
            request.setAttribute(Data.COMPUTER_ID, _processVariables.computer.id);
            if (StringUtils.isNotBlank(_processVariables.computer.introduced)) {
                request.setAttribute(Data.COMPUTER_INTRODUCE_DATE, _processVariables.computer.introduced);
            }
            if (StringUtils.isNotBlank(_processVariables.computer.discontinued)) {
                request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE, _processVariables.computer.discontinued);
            }
            if (StringUtils.isNotBlank(_processVariables.computer.name)) {
                request.setAttribute(Data.COMPUTER_NAME, _processVariables.computer.name);
            }
            if (_processVariables.computer.company != null
                    && StringUtils.isNotBlank(_processVariables.computer.company.id)) {
                request.setAttribute(Data.COMPUTER_COMPANY_ID, _processVariables.computer.company.id);
            }

        }

        request.setAttribute(Data.LIST_COMPANY,
                CompanyMapper.toCompanyDTO(this.companyService.get(Company.class)));
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
            processVariables.computer = ComputerMapper.toComputerDTO(
                    this.computerService.save(ComputerMapper.toComputer(processVariables.computer)).get());
            ComputerManageServlet.LOGGER.info("Save OK for " + processVariables.computer);
            return new ProcessResult(true, "Save OK");
        } catch (final ServiceException e) {
            ComputerManageServlet.LOGGER.warn("Impossible to save computer : " + processVariables.computer, e);
            return new ProcessResult(true, e.getMessage());
        }
    }
}
