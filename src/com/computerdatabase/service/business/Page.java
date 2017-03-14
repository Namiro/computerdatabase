package com.computerdatabase.service.business;

import java.util.List;

import com.computerdatabase.service.iservice.IService;

/**
 * This class is a page of entities show to the user
 *
 * @author Junior Burleon
 *
 */
public class Page<E> {
	static final int numberOfRecordsBypage = 30;

	private int number;
	private List<E> pageRecords;
	private final IService<E> service;

	public Page(final IService<E> service) {
		this.service = service;
		this.number = 1;
	}

	public int getNumber() {
		return this.number;
	}

	public List<E> getPageRecords() {
		return this.pageRecords;
	}

	public List<E> next() {
		this.number++;
		this.pageRecords = this.service.getPage(this.number, Page.numberOfRecordsBypage);
		return this.pageRecords;
	}

	public List<E> page(final int pageNumber) {
		this.number = pageNumber;
		this.pageRecords = this.service.getPage(this.number, Page.numberOfRecordsBypage);
		return this.pageRecords;
	}

	public List<E> previous() {
		this.number--;
		this.pageRecords = this.service.getPage(this.number, Page.numberOfRecordsBypage);
		return this.pageRecords;
	}
}
