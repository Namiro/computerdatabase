package com.excilys.burleon.computerdatabase.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;

/**
 *
 * @author Junior Burleon
 *
 */
public class CompanyService extends ModelService<Company> implements ICompanyService {

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    /**
     * Default constructor.
     */
    public CompanyService() {

    }

    @Override
    public void checkDataEntity(final Company entity) throws ServiceException {
        ICompanyService.super.checkDataEntity(entity);
        if (entity.getName().length() < 1) {
            throw new ServiceException("The name of company must be longer (More then 1 caracter)");
        }
        if (entity.getName().length() > 50) {
            throw new ServiceException("The name of company must be shorter (Less then 50 caracter)");
        }
    }
}
