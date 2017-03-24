
package com.excilys.burleon.computerdatabase.view.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/**
 *
 * @author Junior Burléon
 */
@WebServlet("/ComputerList")
public class ComputerListServlet extends HttpServlet {

    private static final long serialVersionUID = -6681257837248708119L;

    private final IPageService<Computer> pageService = new PageService<>(Computer.class, 20);
    private final IComputerService computerService = new ComputerService();

    /**
     * Variable working.
     */
    private String filterWord = "";
    private OrderComputerEnum orderBy = OrderComputerEnum.NAME;
    private int recordsByPage = 20;

    /* METHODE */

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession session = request.getSession();

        if (request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE) != null) {
            session.setAttribute(Data.PAGINATION_RECORDS_BY_PAGE,
                    request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE));
        }
        if (request.getParameter(Data.SEARCH_WORD) != null) {
            session.setAttribute(Data.SEARCH_WORD, request.getParameter(Data.SEARCH_WORD));
        }
        if (request.getParameter(Data.ORDER_BY) != null) {
            session.setAttribute(Data.ORDER_BY, request.getParameter(Data.ORDER_BY));
        }

        this.filterWord = (String) session.getAttribute(Data.SEARCH_WORD);
        if (session.getAttribute(Data.PAGINATION_RECORDS_BY_PAGE) != null) {
            this.recordsByPage = Integer.valueOf((String) session.getAttribute(Data.PAGINATION_RECORDS_BY_PAGE));
        }
        if (session.getAttribute(Data.ORDER_BY) != null) {
            switch ((String) session.getAttribute(Data.ORDER_BY)) {
                case Data.ORDER_BY_1:
                    this.orderBy = OrderComputerEnum.NAME;
                    break;
                case Data.ORDER_BY_2:
                    this.orderBy = OrderComputerEnum.INTRODUCE_DATE;
                    break;
                case Data.ORDER_BY_3:
                    this.orderBy = OrderComputerEnum.DISCONTINUE_DATE;
                    break;
                case Data.ORDER_BY_4:
                    this.orderBy = OrderComputerEnum.COMPANY_NAME;
                    break;
                default:
                    break;
            }
        }

        final int newCurrentPage = (request.getParameter(Data.PAGINATION_CURRENT_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_CURRENT_PAGE)) : 1;
        this.recordsByPage = (request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE) != null)
                ? Integer.parseInt(request.getParameter(Data.PAGINATION_RECORDS_BY_PAGE)) : this.recordsByPage;
        this.pageService.setRecordsByPage(this.recordsByPage);
        this.pageService.setFilterWord(this.filterWord);
        this.pageService.setOrderBy(this.orderBy);
        final List<Computer> listComputer = this.pageService.page(newCurrentPage);

        request.setAttribute(Data.LIST_COMPUTER, ComputerMapper.INSTANCE.toComputerDTO(listComputer));
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
                    final Optional<Computer> computerOpt = this.computerService.get(Computer.class,
                            Long.parseLong(idStr));
                    if (computerOpt.isPresent()) {
                        this.computerService.remove(computerOpt.get());
                    }
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