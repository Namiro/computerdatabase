
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.service.service.ComputerService;
import com.excilys.burleon.computerdatabase.service.service.PageService;
import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;

/**
 *
 * @author Junior Burléon
 */
@WebServlet("/ComputerList")
public class ComputerListServlet extends HttpServlet {

    private static final long serialVersionUID = -6681257837248708119L;

    private final IPageService<Computer> pageService = new PageService<>(Computer.class, 20);
    private final IComputerService computerService = new ComputerService();

    private String filterWord = "";
    private int recordsByPage = 20;

    /**
     * Variable working.
     */

    /* METHODE */

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        this.filterWord = request.getParameter(Data.SEARCH_WORD);

        final int newCurrentPage = (request.getParameter(Data.PAGINATION_CURRENT_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_CURRENT_PAGE)) : 1;
        this.recordsByPage = (request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE)) : this.recordsByPage;
        this.pageService.setRecordsByPage(this.recordsByPage);
        this.pageService.setFilterWord(this.filterWord);
        final List<Computer> listComputer = this.pageService.page(newCurrentPage);

        request.setAttribute(Data.LIST_COMPUTER, listComputer);
        request.setAttribute(Data.SEARCH_NUMBER_RESULTS,
                this.computerService.getTotalRecords(Computer.class, this.filterWord));
        request.setAttribute(Data.PAGINATION_CURRENT_PAGE, this.pageService.getPageNumber());
        request.setAttribute(Data.PAGINATION_TOTAL_PAGE, this.pageService.getMaxPageNumber());
        request.setAttribute(Data.PAGINATION_RECORDS_BY_PAGE, this.recordsByPage);
        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // If it is a deleting
            if (request.getParameter(Data.SUBMIT_DELETE) != null) {
                final String[] split = request.getParameter(Data.SUBMIT_DELETE).split(",");
                for (final String idStr : split) {
                    this.computerService.remove(this.computerService.get(Computer.class, Long.parseLong(idStr)));
                }
            }
        } catch (final ServiceException e) {
            // Error message
            request.setAttribute(Data.MESSAGE_ERROR, e.getMessage());
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request, response);
        } finally {
            request.setAttribute(Data.LIST_COMPUTER, this.pageService.refresh());
            request.setAttribute(Data.SEARCH_NUMBER_RESULTS,
                    this.computerService.getTotalRecords(Computer.class, this.filterWord));
            request.setAttribute(Data.PAGINATION_CURRENT_PAGE, this.pageService.getPageNumber());
            request.setAttribute(Data.PAGINATION_TOTAL_PAGE, this.pageService.getMaxPageNumber());
            request.setAttribute(Data.MESSAGE_SUCCESS, "Remove OK");
            this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request, response);
        }
    }
}