package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.Optional;

import com.excilys.burleon.computerdatabase.core.model.Computer;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.InvalidDateException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.InvalidDateOrderException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooLongComputerNameException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortComputerNameException;

/**
 *
 * @author Junior Burleon
 *
 */
public interface IComputerService extends IModelService<Computer> {

    @Override
    public Optional<Computer> save(Computer entity)
            throws TooShortComputerNameException, TooLongComputerNameException, InvalidDateOrderException, InvalidDateException;
}
