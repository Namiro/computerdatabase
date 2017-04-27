/**
 *
 */
package com.excilys.burleon.computerdatabase.repository.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.burleon.computerdatabase.repository.exception.PersistenceException;
import com.excilys.burleon.computerdatabase.repository.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.repository.model.Company;
import com.excilys.burleon.computerdatabase.repository.model.Computer;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.repository.model.enumeration.OrderComputerEnum;

/**
 * @author Junior Burleon
 *
 */
@Repository
public class ComputerDao extends ADao<Computer> implements IComputerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

    @Override
    public boolean deleteByCompany(final Company company) {
        ComputerDao.LOGGER.trace("delete : company : " + company);

        boolean success = true;

        final Query query = this.entityManager
                .createQuery("delete from Computer computer where computer.company_id = ?");
        query.setParameter(0, company.getId());
        try {
            query.executeUpdate();
        } catch (final Exception e) {
            success = false;
            throw new PersistenceException(e);
        }

        return success;
    }

    @Override
    public List<Computer> findRange(final Class<Computer> c, final int first, final int nbRecord,
            String filterWord, IOrderEnum<Computer> orderBy) {
        ComputerDao.LOGGER.trace("findRange : c : " + c + "\tfirst : " + first + "\tnbRecord : " + nbRecord
                + "\tfilterWord : " + filterWord + "\torderBy : " + orderBy);

        if (filterWord == null) {
            filterWord = "";
        }
        if (orderBy == null) {
            orderBy = OrderComputerEnum.NAME;
        }

        final TypedQuery<Computer> query = this.entityManager.createQuery(
                "Select c From Computer as c left join c.company as company where c.name like :search or company.name like :search order by :order asc",
                Computer.class);
        query.setParameter("search", filterWord + "%").setParameter("order", orderBy.toString())
                .setMaxResults(nbRecord).setFirstResult(first);
        return query.getResultList();
    }

}
