package com.computerdatabase.service.service;

import java.time.LocalDateTime;

import com.computerdatabase.persistence.dao.CompanyDao;
import com.computerdatabase.persistence.dao.ComputerDao;
import com.computerdatabase.persistence.idao.IDao;
import com.computerdatabase.persistence.model.Company;
import com.computerdatabase.persistence.model.Computer;
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
			throw new ServiceException("The name of computer must be longer (More then 1 caracter)");
		if (entity.getName().length() > 50)
			throw new ServiceException("The name of computer must be shorter (Less then 50 caracter)");
		if (entity.getDiscontinued() != null && entity.getDiscontinued().isBefore(LocalDateTime.now().minusMinutes(20)))
			throw new ServiceException("The discontinued date must be bigger then now");
		if (entity.getIntroduced() != null && entity.getIntroduced().isBefore(LocalDateTime.now().minusMinutes(20)))
			throw new ServiceException("The introduced date must be bigger then now.");
		if (entity.getCompanyId() != 0 && this.companyDao.find(entity.getCompanyId()) == null)
			throw new ServiceException("The company linked to the computer doesn't exist");
	}
}
