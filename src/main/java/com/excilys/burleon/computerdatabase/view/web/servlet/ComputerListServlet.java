
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
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

    public IPageService<Computer> pageService = new PageService<>(Computer.class, 20);

    /**
     * Variable working.
     */

    /* METHODE */

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final int newCurrentPage = (request.getParameter(Data.PAGINATION_CURRENT_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_CURRENT_PAGE)) : 1;
        final int recordsByPage = (request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE)) : 20;
        this.pageService.setRecordsByPage(recordsByPage);
        final List<Computer> listComputer = this.pageService.page(newCurrentPage);

        request.setAttribute(Data.LIST_COMPUTER, listComputer);
        request.setAttribute(Data.PAGINATION_CURRENT_PAGE, this.pageService.getPageNumber());
        request.setAttribute(Data.PAGINATION_TOTAL_PAGE, this.pageService.getMaxPageNumber());

        this.getServletContext().getNamedDispatcher(Servlet.SERVLET_COMPUTER_LIST).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

    }
}