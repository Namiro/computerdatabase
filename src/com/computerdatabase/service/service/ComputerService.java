package com.computerdatabase.service.service;

import java.time.LocalDateTime;

import com.computerdatabase.persistence.model.Company;
import com.computerdatabase.persistence.model.Computer;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.IComputerService;
import com.computerdatabase.service.iservice.IModelService;

/**
 *
 * @author Junior Burleon
 *
 */
public class ComputerService extends ModelService<Computer> implements IComputerService {

	private final IModelService<Company> companyService = new CompanyService();

	public ComputerService() {

	}

	@Override
	public void checkDataEntity(final Computer entity) throws ServiceException {
		IComputerService.super.checkDataEntity(entity);
		if (entity.getName().length() < 1)
			throw new ServiceException("The name of computer must be longer (More then 1 caracter)");
		if (entity.getName().length() > 50)
			throw new ServiceException("The name of computer must be shorter (Less then 50 caracter)");
		if (entity.getDiscontinued() != null && entity.getDiscontinued().isBefore(LocalDateTime.now().minusMinutes(20)))
			throw new ServiceException("The discontinued date must be bigger then now");
		if (entity.getIntroduced() != null && entity.getIntroduced().isBefore(LocalDateTime.now().minusMinutes(20)))
			throw new ServiceException("The introduced date must be bigger then now.");
		if (entity.getCompany() != null)
			this.companyService.checkDataEntity(entity.getCompany());
	}
}
