package com.excilys.burleon.computerdatabase.service.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.InvalidDateException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.InvalidDateOrderException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooLongComputerNameException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortComputerNameException;
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
    public void checkDataEntity(final Computer entity)
            throws TooShortComputerNameException, TooLongComputerNameException, InvalidDateOrderException {
        IComputerService.super.checkDataEntity(entity);
        if (entity.getName().length() < 1) {
            throw new TooShortComputerNameException("The name of computer must be longer (More then 1 caracter)");
        }
        if (entity.getName().length() > 250) {
            throw new TooLongComputerNameException("The name of computer must be shorter (Less then 250 caracter)");
        }
        if (entity.getDiscontinued() != null && entity.getIntroduced() != null
                && entity.getDiscontinued().isBefore(entity.getIntroduced())) {
            throw new InvalidDateOrderException("The discontinued date must be after the introduced date");
        }
        if (entity.getIntroduced() != null && entity.getIntroduced().isBefore(LocalDateTime.of(1970, 01, 01, 0, 0, 0))) {
            throw new InvalidDateException("The introduced date must be after 1970-01-01");
        }
        if (entity.getDiscontinued() != null && entity.getDiscontinued().isBefore(LocalDateTime.of(1970, 01, 01, 0, 0, 0))) {
            throw new InvalidDateException("The discontinued date must be after 1970-01-01");
        }
        if (entity.getIntroduced() != null && entity.getIntroduced().isAfter(LocalDateTime.of(2038, 01, 18, 0, 0, 0))) {
            throw new InvalidDateException("The introduced date must be before 2038-01-18");
        }
        if (entity.getDiscontinued() != null && entity.getDiscontinued().isAfter(LocalDateTime.of(2038, 01, 18, 0, 0, 0))) {
            throw new InvalidDateException("The discontinued date must be before 2038-01-18");
        }
        if (entity.getCompany() != null) {
            this.companyService.checkDataEntity(entity.getCompany());
        }
    }
}
