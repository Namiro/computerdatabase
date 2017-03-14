package com.computerdatabase.service.service;

import com.computerdatabase.model.dao.CompanyDao;
import com.computerdatabase.model.entity.Company;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.ICompanyService;

/**
 *
 * @author Junior Burleon
 *
 */
public class CompanyService extends Service<Company> implements ICompanyService {

	public CompanyService() {
		this.dao = new CompanyDao();
	}

	@Override
	public void checkDataEntity(final Company entity) throws ServiceException {
		if (entity.getName().length() < 1)
			throw new ServiceException("The name of company must be longer (More then 1 caracter)");
		if (entity.getName().length() > 50)
			throw new ServiceException("The name of company must be shorter (Less then 50 caracter)");
	}
}
