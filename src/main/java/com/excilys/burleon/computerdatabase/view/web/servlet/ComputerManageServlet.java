/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates and open
 * the template in the editor.
 */
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.tool.Utility;
import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;

/**
 *
 * @author Junior
 */
@WebServlet("/GameManage")
public class ComputerManageServlet extends HttpServlet {

    private static final long serialVersionUID = -922272733938052338L;

    public IComputerService computerService;

    /**
     * Variable working.
     */
    private Computer _computer;

    /* METHODE */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter(Data.COMPUTER_ID) != null && !"".equals(request.getParameter(Data.COMPUTER_ID))) {
            final int id = Integer.parseInt(request.getParameter(Data.COMPUTER_ID));
            this._computer = this.computerService.get(Computer.class, id);
            if (this._computer != null) {
                request.setAttribute(Data.COMPUTER_ID, this._computer.getId());
                request.setAttribute(Data.COMPUTER_INTRODUCE_DATE, this._computer.getIntroduced());
                request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE, this._computer.getDiscontinued());
                request.setAttribute(Data.COMPUTER_NAME, this._computer.getName());
                // request.setAttribute(Data.COMPUTER_COMPANY,
                // this._computer.getCompany());
            } else {
                response.sendRedirect(response.encodeRedirectURL(Servlet.SERVLET_COMPUTER_MANAGE));
                return;
            }
        }

        final List<Computer> listGame = this.computerService.get(Computer.class);

        request.setAttribute(Data.LIST_COMPUTER, listGame);

        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        // If it is an updating or deleting
        if (request.getParameter(Data.COMPUTER_ID) != null && !"".equals(request.getParameter(Data.COMPUTER_ID))) {
            final int id = Integer.parseInt(request.getParameter(Data.COMPUTER_ID));
            this._computer = this.computerService.get(Computer.class, id);
        } else { // If it is a creating
            new ArrayList<>();
            this._computer = new Computer();
        }

        this._computer.setName(request.getParameter(Data.COMPUTER_NAME));
        this._computer
                .setIntroduced(Utility.convertToLocalDateTime(request.getParameter(Data.COMPUTER_INTRODUCE_DATE)));
        this._computer.setIntroduced(
                Utility.convertToLocalDateTime(request.getParameter(Data.COMPUTER_DISCONTINUE_DATE)));
        // this._computer.setCompany(request.getParameter(Data.COMPUTER_COMPANY_ID));

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
                response.sendRedirect(response.encodeRedirectURL(
                        Servlet.SERVLET_COMPUTER_MANAGE + "?computer=" + this._computer.getId()));
            } catch (final ServiceException ex) {
                // If there is an exception (A problem with the data of form
                request.setAttribute(Data.COMPUTER_NAME, this._computer.getName());
                request.setAttribute(Data.COMPUTER_INTRODUCE_DATE, this._computer.getIntroduced());
                request.setAttribute(Data.COMPUTER_DISCONTINUE_DATE, this._computer.getDiscontinued());
                // request.setAttribute(Data.COMPUTER_COMPANY,
                // this._computer.getCompany());

                // Error message
                request.setAttribute(Data.MESSAGE_ERROR, ex.getMessage());
                this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_MANAGE).forward(request,
                        response);
            }
        }
    }
}
