package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.List;

import com.excilys.burleon.computerdatabase.core.model.IEntity;
import com.excilys.burleon.computerdatabase.core.model.enumeration.IOrderEnum;

/**
 * This service allow to manage the records with pages.
 *
 * @author Junior Burleon
 *
 * @param <E>
 *            The type of record to manage in the pages
 */
public interface IPageService<E extends IEntity> extends IService {

    /**
     *
     * @return The maximum of page number in function of the number of records
     *         by page. (Be careful, if a FilterWord is set, it will be
     *         account)
     */
    long getMaxPageNumber(Class<E> entityType);

    /**
     *
     * @return The current page number
     */
    int getPageNumber();

    /**
     *
     * @return The records of the page
     */
    List<E> getPageRecords();

    /**
     * To go to the next page.
     *
     * @return The list of record for the new page. (Be careful, if a
     *         FilterWord is set, it will be account)
     */
    List<E> next(Class<E> entityType);

    /**
     * To go to the a specific page.
     *
     * @param pageNumber
     *            The page number
     * @return The list of record for the new page. (Be careful, if a
     *         FilterWord is set, it will be account)
     */
    List<E> page(Class<E> entityType, int pageNumber);

    /**
     * To go to the previous page.
     *
     * @return The list of record for the new page. (Be careful, if a
     *         FilterWord is set, it will be account)
     */
    List<E> previous(Class<E> entityType);

    /**
     * To refresh the current page. (Be careful, if a FilterWord is set, it
     * will be account)
     *
     * @return List of records with the same parameter then the last using of
     *         the service
     */
    List<E> refresh(Class<E> entityType);

    /**
     * Allow to put a filter in the service. It means the results that you
     * will get, will be the result that have the word in their fields.
     *
     * @param filterWord
     *            The word that will be used to filter.
     */
    void setFilterWord(String filterWord);

    /**
     * Allow to set a service that will be used by the PageService. So It's
     * required to set this service.
     *
     * @param modelService
     *            The model service that will be used. The model must match
     *            the type of PageService
     */
    void setModelService(final IModelService<E> modelService);

    /**
     * To set the order.
     *
     * @param orderBy
     *            The order
     */
    void setOrderBy(IOrderEnum<E> orderBy);

    /**
     * To change the number of records by page.
     *
     * @param recordsByPage
     *            The number of records by page
     */
    void setRecordsByPage(int recordsByPage);
}