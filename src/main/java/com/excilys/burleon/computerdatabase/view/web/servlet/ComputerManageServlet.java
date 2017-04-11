/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates and open
 * the template in the editor.
 */
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.service.service.CompanyService;
import com.excilys.burleon.computerdatabase.service.service.ComputerService;
import com.excilys.burleon.computerdatabase.service.service.PageService;
import com.excilys.burleon.computerdatabase.view.model.CompanyDTO;
import com.excilys.burleon.computerdatabase.view.model.ComputerDTO;
import com.excilys.burleon.computerdatabase.view.model.mapper.CompanyMapper;
import com.excilys.burleon.computerdatabase.view.model.mapper.ComputerMapper;
import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;

/**
 *
 * @author Junior
 */
@WebServlet("/ComputerManage")
public class ComputerManageServlet extends HttpServlet {

    private static final long serialVersionUID = -922272733938052338L;

    Logger LOGGER = LoggerFactory.getLogger(ComputerListServlet.class);

    public IComputerService computerService = new ComputerService();
    public ICompanyService companyService = new CompanyService();

    public IPageService<Computer> pageService = new PageService<>(Computer.class, 20);

    /**
     * Variable working.
     */
    private ComputerDTO _computer;

    /* METHODE */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.LOGGER.trace("GET /ComputerManage \t" + request.getRequestURI());

        /* If a computer was selected */
        if (request.getParameter(Data.COMPUTER_ID) != null && !"".equals(request.getParameter(Data.COMPUTER_ID))) {
            final long id = Long.parseLong(request.getParameter(Data.COMPUTER_ID));
            final Optional<Computer> computerOpt = this.computerService.get(Computer.class, id);
            /* If the computer exist, we get its data */
            if (computerOpt.isPresent()) {
                this._computer = ComputerMapper.INSTANCE.toComputerDTO(computerOpt.get());
                request.setAttribute(Data.COMPUTER_ID, this._computer.getId());
                if (this._computer.getIntroduced() != null) {
                    request.setAttribute(Data.COMPUTER_INTRODUCE_DATE, this._computer.getIntroduced());
                }
                if (this._computer.getDiscontinued() != null) {
                    request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE, this._computer.getDiscontinued());
                }
                request.setAttribute(Data.COMPUTER_NAME, this._computer.getName());
                request.setAttribute(Data.COMPUTER_COMPANY_ID, this._computer.getCompany().getId());
            } else {
                response.sendRedirect(response.encodeRedirectURL(Servlet.SERVLET_COMPUTER_MANAGE));
                return;
            }
        }

        request.setAttribute(Data.LIST_COMPANY,
                CompanyMapper.INSTANCE.toCompanyDTO(this.companyService.get(Company.class)));

        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.LOGGER.trace("POST /ComputerManage \t" + request.getRequestURI());

        // If it is an updating or deleting
        if (request.getParameter(Data.COMPUTER_ID) != null && !"".equals(request.getParameter(Data.COMPUTER_ID))
                && !"0".equals(request.getParameter(Data.COMPUTER_ID))) {
            final long id = Long.parseLong(request.getParameter(Data.COMPUTER_ID));
            this._computer = ComputerMapper.INSTANCE
                    .toComputerDTO(this.computerService.get(Computer.class, id).get());
        } else { // If it is a creating
            new ArrayList<>();
            this._computer = new ComputerDTO();
        }

        try {
            this.LOGGER.debug("gatling 1 \t" + request.getParameter(Data.COMPUTER_NAME));

            this._computer.setName(request.getParameter(Data.COMPUTER_NAME));
            this._computer.setIntroduced(request.getParameter(Data.COMPUTER_INTRODUCE_DATE));
            this._computer.setDiscontinued(request.getParameter(Data.COMPUTER_DISCONTINUE_DATE));
            if (request.getParameter(Data.COMPUTER_COMPANY_ID) != null) {
                final Optional<Company> companyOpt = this.companyService.get(Company.class,
                        Long.parseLong(request.getParameter(Data.COMPUTER_COMPANY_ID)));
                if (companyOpt.isPresent()) {
                    final CompanyDTO companyDTO = CompanyMapper.INSTANCE.toCompanyDTO(companyOpt.get());
                    this._computer.setCompany(companyDTO);
                }
            }
        } catch (final ServiceException e) {
            // Error message
            request.setAttribute(Data.MESSAGE_ERROR, e.getMessage());
            this.LOGGER.warn("Impossible to get construct a computer from the user entries", e);
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                    response);
        }

        if (request.getParameter(Data.SUBMIT_DELETE) != null) {
            this.computerService.remove(ComputerMapper.INSTANCE.toComputer(this._computer));
            request.setAttribute(Data.MESSAGE_SUCCESS, "Remove OK");
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                    response);
        } else {
            try {
                // Update and create /!\ For update ID must exist
                this.LOGGER.trace("Just before Create or Update : entity : " + this._computer);
                this._computer = ComputerMapper.INSTANCE.toComputerDTO(
                        this.computerService.save(ComputerMapper.INSTANCE.toComputer(this._computer)).get());
                this.LOGGER.trace("Just after Create or Update : entity : " + this._computer);
                request.setAttribute(Data.MESSAGE_SUCCESS, "Save OK");
            } catch (final ServiceException e) {
                // Error message
                this.LOGGER.warn("Impossible to save computer", e);
                request.setAttribute(Data.MESSAGE_ERROR, e.getMessage());
            } finally {
                request.setAttribute(Data.COMPUTER_NAME, this._computer.getName());
                request.setAttribute(Data.COMPUTER_INTRODUCE_DATE, this._computer.getIntroduced());
                request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE, this._computer.getDiscontinued());
                request.setAttribute(Data.LIST_COMPANY,
                        CompanyMapper.INSTANCE.toCompanyDTO(this.companyService.get(Company.class)));
                if (this._computer.getCompany() != null) {
                    request.setAttribute(Data.COMPUTER_COMPANY_ID, this._computer.getCompany().getId());
                }
                if (!this._computer.getId().equals("")) {
                    request.setAttribute(Data.COMPUTER_ID, this._computer.getId());
                }

                this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                        response);
            }

        }
    }
}
