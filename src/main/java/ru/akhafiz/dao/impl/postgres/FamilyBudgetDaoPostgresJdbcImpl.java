package ru.akhafiz.dao.impl.postgres;

import ru.akhafiz.dao.api.FamilyBudgetDao;
import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.FamilyBudget;

import java.sql.Connection;

/**
 * <p></p>
 *
 * @author akhafiz
 */
class FamilyBudgetDaoPostgresJdbcImpl extends BaseDaoPostgresJdbcImpl<FamilyBudget> implements FamilyBudgetDao {

    @Override
    public FamilyBudget findByFamilyId(Long familyId, Connection connection) throws FbDaoException {
        SearchCriteria criteria = createSearchCriteria(connection);
        criteria.addParameter("familyId",familyId);
        return criteria.getUniqueResult();
    }
}
