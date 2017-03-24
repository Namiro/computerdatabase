package com.excilys.burleon.computerdatabase.service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.model.IEntity;
import com.excilys.burleon.computerdatabase.persistence.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;

/**
 * This class is a page of entities show to the user.
 *
 * @author Junior Burleon
 *
 */
public class PageService<E extends IEntity> implements IPageService<E> {

    static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    private int recordsByPage;
    private int number;
    private List<E> records;
    private final IModelService<E> service;
    private final Class<E> entityType;
    private String filterWord = "";
    private IOrderEnum<E> orderBy = null;

    /**
     * Constructor.
     *
     * @param entityType
     *            The type of entity that this page will manage
     * @param recordsByPage
     *            The number of records by page
     */
    public PageService(final Class<E> entityType, final int recordsByPage) {
        this.entityType = entityType;
        this.service = new ModelService<>();
        this.number = 1;
        this.recordsByPage = recordsByPage;
        this.records = this.service.getPage(this.entityType, this.number, recordsByPage, this.filterWord,
                this.orderBy);
    }

    public String getFilterWord() {
        return this.filterWord;
    }

    @Override
    public long getMaxPageNumber() {
        return (this.service.getTotalRecords(this.entityType, this.filterWord) / this.recordsByPage) + 1;
    }

    public IOrderEnum<E> getOrderBy() {
        return this.orderBy;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.excilys.burleon.computerdatabase.service.service.IPageService#
     * getNumber()
     */
    @Override
    public int getPageNumber() {
        return this.number;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.excilys.burleon.computerdatabase.service.service.IPageService#
     * getPageRecords()
     */
    @Override
    public List<E> getPageRecords() {
        return this.records;
    }

    public int getRecordsByPage() {
        return this.recordsByPage;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.excilys.burleon.computerdatabase.service.service.IPageService#next(
     * )
     */
    @Override
    public List<E> next() {
        this.number++;
        final List<E> records = this.service.getPage(this.entityType, this.number, this.recordsByPage,
                this.filterWord, this.orderBy);
        if (records != null && !records.isEmpty()) {
            this.records = records;
        } else {
            this.number--;
        }
        return this.records;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.excilys.burleon.computerdatabase.service.service.IPageService#page(
     * int)
     */
    @Override
    public List<E> page(final int pageNumber) {
        if (pageNumber > 0) {
            final List<E> records = this.service.getPage(this.entityType, pageNumber, this.recordsByPage,
                    this.filterWord, this.orderBy);
            if (records != null && !records.isEmpty()) {
                this.records = records;
                this.number = pageNumber;
            }
        }
        return this.records;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.excilys.burleon.computerdatabase.service.service.IPageService#
     * previous()
     */
    @Override
    public List<E> previous() {
        if (this.number > 1) {
            this.number--;
            this.records = this.service.getPage(this.entityType, this.number, this.recordsByPage, this.filterWord,
                    this.orderBy);
        }
        return this.records;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.excilys.burleon.computerdatabase.service.service.IPageService#
     * refresh()
     */
    @Override
    public List<E> refresh() {
        return this.service.getPage(this.entityType, this.number, this.recordsByPage, this.filterWord,
                this.orderBy);
    }

    @Override
    public void setFilterWord(final String filterWord) {
        if (filterWord == null) {
            this.filterWord = "";
        } else {
            this.filterWord = filterWord;
        }

    }

    public void setOrderBy(final IOrderEnum<E> orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public void setRecordsByPage(final int recordsByPage) {
        if (recordsByPage < 1) {
            throw new ServiceException("The number of record by page must be upper then 0");
        }
        this.recordsByPage = recordsByPage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Current page : " + this.number + "\n");
        for (final E record : this.records) {
            sb.append(record + "\n");
        }
        return sb.toString();
    }
}
