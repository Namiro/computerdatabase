package com.excilys.burleon.computerdatabase.repository.idao;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.Computer;

public interface IComputerDao extends IDao<Computer> {

    /**
     * Delete computers in function of a company.
     *
     * @param entity
     *            Company that need to have the computer to be deleted
     * @param connection
     *            The db connection
     * @return True if all is ok, else false
     */
    boolean deleteByCompany(Company entity);
}
