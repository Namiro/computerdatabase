package com.computerdatabase.service.service;

import java.util.List;

import com.computerdatabase.service.iservice.IService;

/**
 * This class is a page of entities show to the user
 *
 * @author Junior Burleon
 *
 */
public class PageService<E> {
	static final int numberOfRecordsBypage = 3;

	private int number;
	private List<E> records;
	private final IService<E> service;

	public PageService(final IService<E> service) {
		this.service = service;
		this.number = 1;
		this.records = this.service.getPage(1, PageService.numberOfRecordsBypage);
	}

	public int getNumber() {
		return this.number;
	}

	public List<E> getPageRecords() {
		return this.records;
	}

	/**
	 * To go to the next page
	 *
	 * @return The list of record for the new page
	 */
	public List<E> next() {
		this.number++;
		final List<E> records = this.service.getPage(this.number, PageService.numberOfRecordsBypage);
		if (records != null && !records.isEmpty())
			this.records = records;
		else
			this.number--;
		return this.records;
	}

	/**
	 * To go to the a specific page
	 *
	 * @return The list of record for the new page
	 */
	public List<E> page(final int pageNumber) {
		System.out.println(pageNumber);
		if (pageNumber > 0) {
			final List<E> records = this.service.getPage(pageNumber, PageService.numberOfRecordsBypage);
			if (records != null && !records.isEmpty()) {
				this.records = records;
				this.number = pageNumber;
			}
		}
		return this.records;
	}

	/**
	 * To go to the previous page
	 *
	 * @return The list of record for the new page
	 */
	public List<E> previous() {
		if (this.number > 1) {
			this.number--;
			this.records = this.service.getPage(this.number, PageService.numberOfRecordsBypage);
		}
		return this.records;
	}

	/**
	 * To refresh the current page
	 */
	public void refresh() {
		this.records = this.service.getPage(this.number, PageService.numberOfRecordsBypage);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Current page : " + this.number + "\n");
		for (final E record : this.records)
			sb.append(record + "\n");
		return sb.toString();
	}
}
