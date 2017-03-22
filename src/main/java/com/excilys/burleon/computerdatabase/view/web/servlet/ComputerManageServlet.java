/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates and open
 * the template in the editor.
 */
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.service.service.CompanyService;
import com.excilys.burleon.computerdatabase.service.service.ComputerService;
import com.excilys.burleon.computerdatabase.service.service.PageService;
import com.excilys.burleon.computerdatabase.service.tool.Utility;
import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;

/**
 *
 * @author Junior
 */
@WebServlet("/ComputerManage")
public class ComputerManageServlet extends HttpServlet {

    private static final long serialVersionUID = -922272733938052338L;

    public IComputerService computerService = new ComputerService();
    public ICompanyService companyService = new CompanyService();

    public IPageService<Computer> pageService = new PageService<>(Computer.class, 20);

    /**
     * Variable working.
     */
    private Computer _computer;

    /* METHODE */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        /* If a computer was selected */
        if (request.getParameter(Data.COMPUTER_ID) != null && !"".equals(request.getParameter(Data.COMPUTER_ID))) {
            final long id = Long.parseLong(request.getParameter(Data.COMPUTER_ID));
            this._computer = this.computerService.get(Computer.class, id);
            /* If the computer exist, we get its data */
            if (this._computer != null) {
                request.setAttribute(Data.COMPUTER_ID, this._computer.getId());
                if (this._computer.getIntroduced() != null) {
                    request.setAttribute(Data.COMPUTER_INTRODUCE_DATE,
                            this._computer.getIntroduced().toLocalDate());
                }
                if (this._computer.getDiscontinued() != null) {
                    request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE,
                            this._computer.getDiscontinued().toLocalDate());
                }
                request.setAttribute(Data.COMPUTER_NAME, this._computer.getName());
                request.setAttribute(Data.COMPUTER_COMPANY_ID, this._computer.getCompany().getId());
            } else {
                response.sendRedirect(response.encodeRedirectURL(Servlet.SERVLET_COMPUTER_MANAGE));
                return;
            }
        }

        request.setAttribute(Data.LIST_COMPANY, this.companyService.get(Company.class));

        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // If it is an updating or deleting
        if (request.getParameter(Data.COMPUTER_ID) != null && !"".equals(request.getParameter(Data.COMPUTER_ID))
                && !"0".equals(request.getParameter(Data.COMPUTER_ID))) {
            final long id = Long.parseLong(request.getParameter(Data.COMPUTER_ID));
            this._computer = this.computerService.get(Computer.class, id);
        } else { // If it is a creating
            new ArrayList<>();
            this._computer = new Computer();
        }

        try {
            this._computer.setName(request.getParameter(Data.COMPUTER_NAME));
            this._computer.setIntroduced(
                    Utility.convertStringDateToLocalDateTime(request.getParameter(Data.COMPUTER_INTRODUCE_DATE)));
            this._computer.setDiscontinued(Utility
                    .convertStringDateToLocalDateTime(request.getParameter(Data.COMPUTER_DISCONTINUE_DATE)));
            if (request.getParameter(Data.COMPUTER_COMPANY_ID) != null) {
                final Company company = this.companyService.get(Company.class,
                        Long.parseLong(request.getParameter(Data.COMPUTER_COMPANY_ID)));
                this._computer.setCompany(company);
            }
        } catch (final ServiceException e) {
            // Error message
            request.setAttribute(Data.MESSAGE_ERROR, e.getMessage());
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                    response);
        }

        if (request.getParameter(Data.SUBMIT_DELETE) != null) {
            this.computerService.remove(this._computer);
            request.setAttribute(Data.MESSAGE_SUCCESS, "Remove OK");
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                    response);
        } else {
            try {
                // Update and create /!\ For update ID must exist
                this._computer = this.computerService.save(this._computer);
                request.setAttribute(Data.MESSAGE_SUCCESS, "Save OK");
            } catch (final ServiceException e) {
                // Error message
                request.setAttribute(Data.MESSAGE_ERROR, e.getMessage());
            } finally {
                request.setAttribute(Data.COMPUTER_NAME, this._computer.getName());
                request.setAttribute(Data.COMPUTER_INTRODUCE_DATE,
                        Utility.convertToStringDate(this._computer.getIntroduced()));
                request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE,
                        Utility.convertToStringDate(this._computer.getDiscontinued()));
                if (this._computer.getCompany() != null) {
                    request.setAttribute(Data.COMPUTER_COMPANY_ID, this._computer.getCompany().getId());
                }
                request.setAttribute(Data.LIST_COMPANY, this.companyService.get(Company.class));
                if (this._computer.getId() != 0) {
                    request.setAttribute(Data.COMPUTER_ID, this._computer.getId());
                }

                this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                        response);
            }
        }
    }
}
