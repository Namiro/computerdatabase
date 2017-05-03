package com.excilys.burleon.computerdatabase.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.DataValidationException;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IModelService;

/**
 *
 * @author Junior Burleon
 *
 */
@Service
public class ComputerService extends AModelService<Computer> implements IComputerService {

    static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    /**
     * A service for the companies.
     */
    private final IModelService<Company> companyService = new CompanyService();

    @Override
    public void checkDataEntity(final Computer entity) throws ServiceException {
        IComputerService.super.checkDataEntity(entity);
        if (entity.getName().length() < 1) {
            throw new DataValidationException("The name of computer must be longer (More then 1 caracter)");
        }
        if (entity.getName().length() > 550) {
            throw new DataValidationException("The name of computer must be shorter (Less then 50 caracter)");
        }
        if (entity.getDiscontinued() != null && entity.getIntroduced() != null
                && entity.getDiscontinued().isBefore(entity.getIntroduced())) {
            throw new DataValidationException("The discontinued date must be after the introduced date");
        }
        if (entity.getDiscontinued() != null && entity.getIntroduced() != null
                && entity.getIntroduced().isAfter(entity.getDiscontinued())) {
            throw new DataValidationException("The introduced date must be before the discontinued date.");
        }
        if (entity.getCompany() != null) {
            this.companyService.checkDataEntity(entity.getCompany());
        }
    }
}