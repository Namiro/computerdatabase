package com.excilys.burleon.computerdatabase.persistence.idao;

import java.sql.Connection;

import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;

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
    boolean deleteByCompany(Company entity, Connection connection);
}
