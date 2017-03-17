package com.excilys.burleon.computerdatabase.service.service;

import java.util.List;

import com.excilys.burleon.computerdatabase.persistence.model.IEntity;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;

/**
 * This class is a page of entities show to the user.
 *
 * @author Junior Burleon
 *
 */
public class PageService<E extends IEntity> {
    static final int RECORDS_BY_PAGE = 3;

    private int number;
    private List<E> records;
    private final IModelService<E> service;
    private final Class<E> entityType;

    /**
     * Constructor.
     *
     * @param entityType
     *            The type of entity that this page will manage
     */
    public PageService(final Class<E> entityType) {
        this.entityType = entityType;
        this.service = new ModelService<>();
        this.number = 1;
        this.records = this.service.getPage(this.entityType, this.number, PageService.RECORDS_BY_PAGE);
    }

    public int getNumber() {
        return this.number;
    }

    public List<E> getPageRecords() {
        return this.records;
    }

    /**
     * To go to the next page.
     *
     * @return The list of record for the new page
     */
    public List<E> next() {
        this.number++;
        final List<E> records = this.service.getPage(this.entityType, this.number, PageService.RECORDS_BY_PAGE);
        if (records != null && !records.isEmpty()) {
            this.records = records;
        } else {
            this.number--;
        }
        return this.records;
    }

    /**
     * To go to the a specific page.
     *
     * @param pageNumber
     *            The page number
     * @return The list of record for the new page
     */
    public List<E> page(final int pageNumber) {
        System.out.println(pageNumber);
        if (pageNumber > 0) {
            final List<E> records = this.service.getPage(this.entityType, pageNumber, PageService.RECORDS_BY_PAGE);
            if (records != null && !records.isEmpty()) {
                this.records = records;
                this.number = pageNumber;
            }
        }
        return this.records;
    }

    /**
     * To go to the previous page.
     *
     * @return The list of record for the new page
     */
    public List<E> previous() {
        if (this.number > 1) {
            this.number--;
            this.records = this.service.getPage(this.entityType, this.number, PageService.RECORDS_BY_PAGE);
        }
        return this.records;
    }

    /**
     * To refresh the current page.
     */
    public void refresh() {
        this.records = this.service.getPage(this.entityType, this.number, PageService.RECORDS_BY_PAGE);
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
