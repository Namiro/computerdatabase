package com.computerdatabase.service.service;

import com.computerdatabase.persistence.model.Company;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.ICompanyService;

/**
 *
 * @author Junior Burleon
 *
 */
public class CompanyService extends ModelService<Company> implements ICompanyService {

	public CompanyService() {

	}

	@Override
	public void checkDataEntity(final Company entity) throws ServiceException {
		ICompanyService.super.checkDataEntity(entity);
		if (entity.getName().length() < 1)
			throw new ServiceException("The name of company must be longer (More then 1 caracter)");
		if (entity.getName().length() > 50)
			throw new ServiceException("The name of company must be shorter (Less then 50 caracter)");
	}
}
