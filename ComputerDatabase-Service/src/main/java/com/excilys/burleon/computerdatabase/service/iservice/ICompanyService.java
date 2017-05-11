package com.excilys.burleon.computerdatabase.service.iservice;

import java.util.Optional;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooLongCompanyNameException;
import com.excilys.burleon.computerdatabase.service.exception.entityvalidation.TooShortCompanyNameException;

/**
 *
 * @author Junior Burleon
 *
 */
public interface ICompanyService extends IModelService<Company> {

    @Override
    public Optional<Company> save(Company entity) throws TooShortCompanyNameException, TooLongCompanyNameException;
}
