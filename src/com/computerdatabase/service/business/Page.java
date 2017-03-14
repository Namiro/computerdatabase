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
	static final int numberOfRecordsBypage = 3;

	private int number;
	private List<E> records;
	private final IService<E> service;

	public Page(final IService<E> service) {
		this.service = service;
		this.number = 1;
		this.records = this.service.getPage(1, Page.numberOfRecordsBypage);
	}

	public int getNumber() {
		return this.number;
	}

	public List<E> getPageRecords() {
		return this.records;
	}

	public List<E> next() {
		this.number++;
		final List<E> records = this.service.getPage(this.number, Page.numberOfRecordsBypage);
		if (records != null && !records.isEmpty())
			this.records = records;
		else
			this.number--;
		return this.records;
	}

	public List<E> page(final int pageNumber) {
		System.out.println(pageNumber);
		if (pageNumber > 0) {
			final List<E> records = this.service.getPage(pageNumber, Page.numberOfRecordsBypage);
			if (records != null && !records.isEmpty()) {
				this.records = records;
				this.number = pageNumber;
			}
		}
		return this.records;
	}

	public List<E> previous() {
		if (this.number > 1) {
			this.number--;
			this.records = this.service.getPage(this.number, Page.numberOfRecordsBypage);
		}
		return this.records;
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
