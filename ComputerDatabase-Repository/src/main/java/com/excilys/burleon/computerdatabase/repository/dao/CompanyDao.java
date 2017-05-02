package com.excilys.burleon.computerdatabase.repository.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.burleon.computerdatabase.core.model.Company;
import com.excilys.burleon.computerdatabase.core.model.enumeration.IOrderEnum;
import com.excilys.burleon.computerdatabase.core.model.enumeration.OrderCompanyEnum;
import com.excilys.burleon.computerdatabase.repository.idao.ICompanyDao;

/**
 * @author Junior Burleon
 *
 */
@Repository
public class CompanyDao extends ADao<Company> implements ICompanyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    @Override
    public List<Company> findRange(final Class<Company> c, final int first, final int nbRecord, String filterWord,
            IOrderEnum<Company> orderBy) {
        CompanyDao.LOGGER.trace("findRange : c : " + c + "\tfirst : " + first + "\tnbRecord : " + nbRecord
                + "\tfilterWord : " + filterWord + "\torderBy : " + orderBy);

        if (filterWord == null) {
            filterWord = "";
        }
        if (orderBy == null) {
            orderBy = OrderCompanyEnum.NAME;
        }

        final TypedQuery<Company> query = this.entityManager
                .createQuery("select company from Company company where company.name like ? order by "
                        + orderBy.toString() + " asc", Company.class);
        query.setParameter(0, filterWord + "%").setMaxResults(nbRecord).setFirstResult(first);

        return query.getResultList();
    }
}
