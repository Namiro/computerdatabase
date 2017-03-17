package com.excilys.burleon.computerdatabase.service.service;

import java.time.LocalDateTime;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;

/**
 *
 * @author Junior Burleon
 *
 */
public class ComputerService extends ModelService<Computer> implements IComputerService {

    /**
     * A service for the companies.
     */
    private final IModelService<Company> companyService = new CompanyService();

    /**
     * Default constructor.
     */
    public ComputerService() {

    }

    @Override
    public void checkDataEntity(final Computer entity) throws ServiceException {
        IComputerService.super.checkDataEntity(entity);
        if (entity.getName().length() < 1) {
            throw new ServiceException("The name of computer must be longer (More then 1 caracter)");
        }
        if (entity.getName().length() > 50) {
            throw new ServiceException("The name of computer must be shorter (Less then 50 caracter)");
        }
        if (entity.getDiscontinued() != null
                && entity.getDiscontinued().isBefore(LocalDateTime.now().minusMinutes(20))) {
            throw new ServiceException("The discontinued date must be bigger then now");
        }
        if (entity.getIntroduced() != null
                && entity.getIntroduced().isBefore(LocalDateTime.now().minusMinutes(20))) {
            throw new ServiceException("The introduced date must be bigger then now.");
        }
        if (entity.getCompany() != null) {
            this.companyService.checkDataEntity(entity.getCompany());
        }
    }
}
