package com.excilys.burleon.computerdatabase.service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.burleon.computerdatabase.core.model.IEntity;
import com.excilys.burleon.computerdatabase.core.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.service.exception.RequiredServiceException;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;

/**
 * This class is a page of entities show to the user.
 *
 * @author Junior Burleon
 *
 */
@Service
@Scope("prototype")
public class PageService<E extends IEntity> implements IPageService<E> {

    static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    private int recordsByPage = 20;
    private int number = 1;
    private List<E> records;
    private String filterWord = "";
    private IOrderEnum<E> orderBy = null;

    private IModelService<E> modelService;

    public String getFilterWord() {
        return this.filterWord;
    }

    @Override
    public long getMaxPageNumber(final Class<E> entityType) {
        PageService.LOGGER.trace("getMaxPageNumber");
        return (this.modelService.getTotalRecords(entityType, this.filterWord) / this.recordsByPage) + 1;
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
        PageService.LOGGER.trace("getMaxPageNumber");
        return this.records;
    }

    public int getRecordsByPage() {
        PageService.LOGGER.trace("getRecordsByPage");
        return this.recordsByPage;
    }

    private void hasSetModelService() {
        if (this.modelService == null) {
            throw new RequiredServiceException("A ModelService must be set before using this function");
        }
    }

    @Override
    public List<E> next(final Class<E> entityType) {
        PageService.LOGGER.trace("next");
        this.hasSetModelService();
        this.number++;
        final List<E> records = this.modelService.getPage(entityType, this.number, this.recordsByPage,
                this.filterWord, this.orderBy);
        if (records != null) {
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
    public List<E> page(final Class<E> entityType, final int pageNumber) {
        PageService.LOGGER.trace("page");
        this.hasSetModelService();
        if (pageNumber > 0) {
            final List<E> records = this.modelService.getPage(entityType, pageNumber, this.recordsByPage,
                    this.filterWord, this.orderBy);
            if (records != null) {
                this.records = records;
                this.number = pageNumber;
            }
        }
        return this.records;
    }

    @Override
    public List<E> previous(final Class<E> entityType) {
        PageService.LOGGER.trace("previous");
        this.hasSetModelService();
        if (this.number > 1) {
            this.number--;
            this.records = this.modelService.getPage(entityType, this.number, this.recordsByPage, this.filterWord,
                    this.orderBy);
        }
        return this.records;
    }

    @Override
    public List<E> refresh(final Class<E> entityType) {
        PageService.LOGGER.trace("refresh");
        this.hasSetModelService();
        this.records = this.modelService.getPage(entityType, this.number, this.recordsByPage, this.filterWord,
                this.orderBy);
        return this.records;
    }

    @Override
    public void setFilterWord(final String filterWord) {
        if (filterWord == null) {
            this.filterWord = "";
        } else {
            this.filterWord = filterWord;
        }

    }

    @Override
    public void setModelService(final IModelService<E> modelService) {
        this.modelService = modelService;
    }

    @Override
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
