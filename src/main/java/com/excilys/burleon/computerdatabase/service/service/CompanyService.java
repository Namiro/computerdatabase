package com.excilys.burleon.computerdatabase.service.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.burleon.computerdatabase.persistence.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.persistence.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.persistence.model.Company;
import com.excilys.burleon.computerdatabase.service.exception.ServiceException;
import com.excilys.burleon.computerdatabase.service.iservice.ICompanyService;

/**
 *
 * @author Junior Burleon
 *
 */
@Service
public class CompanyService extends AModelService<Company> implements ICompanyService {

    static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    IComputerDao computerDao;

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

    /**
     * Remove the company and all of the computer linked with it.
     *
     * @param entity
     *            The entity to remove
     * @return True if OK & False is not OK
     */
    @Override
    public boolean remove(final Company entity) {
        boolean success = true;
        CompanyService.LOGGER.trace("remove : entity : " + entity);
        if (entity != null && entity.getId() > 0) {
            try (Connection connection = this.databaseConnection.getConnection();) {
                connection.setAutoCommit(false);
                try {
                    this.computerDao.deleteByCompany(entity, connection);
                    this.dao.delete(entity, connection);
                    connection.commit();
                    success = true;
                } catch (final PersistenceException e) {
                    success = false;
                    connection.rollback();
                    CompanyService.LOGGER.error("Impossible to delete the company (Company ID : " + entity.getId()
                            + ") and the computer linked with it", e);
                } finally {
                    connection.setAutoCommit(true);
                }
            } catch (final SQLException e) {
                success = false;
                CompanyService.LOGGER.error("Impossible to get a db connection", e);
            }
        } else {
            success = false;
        }
        return success;
    }
}
