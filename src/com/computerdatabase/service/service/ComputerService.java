package com.computerdatabase.service.service;

import java.time.LocalDateTime;

import com.computerdatabase.model.dao.CompanyDao;
import com.computerdatabase.model.dao.ComputerDao;
import com.computerdatabase.model.entity.Company;
import com.computerdatabase.model.entity.Computer;
import com.computerdatabase.model.idao.IDao;
import com.computerdatabase.service.exception.ServiceException;
import com.computerdatabase.service.iservice.IComputerService;

/**
 *
 * @author Junior Burleon
 *
 */
public class ComputerService extends Service<Computer> implements IComputerService {

	private final IDao<Company> companyDao = new CompanyDao();

	public ComputerService() {
		this.dao = new ComputerDao();
	}

	@Override
	public void checkDataEntity(final Computer entity) throws ServiceException {
		if (entity.getName().length() < 1)
			throw new ServiceException("The name of company must be longer (More then 1 caracter)");
		if (entity.getName().length() > 50)
			throw new ServiceException("The name of company must be shorter (Less then 50 caracter)");
		if (entity.getDiscontinued().isBefore(LocalDateTime.now().minusMinutes(20)))
			throw new ServiceException("The name of company must be longer (More then 1 caracter)");
		if (entity.getIntroduced().isBefore(LocalDateTime.now().minusMinutes(20)))
			throw new ServiceException("The name of company must be shorter (Less then 50 caracter)");
		if (entity.getCompanyId() != null && this.companyDao.find(entity.getCompanyId()) == null)
			throw new ServiceException("The company linked to the computer doesn't exist");
	}
}
