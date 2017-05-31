package ru.akhafiz.dao.impl.postgres;

import ru.akhafiz.dao.api.FamilyMemberDao;
import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.FamilyMember;

import java.sql.Connection;
import java.util.List;

/**
 * <p></p>
 *
 * @author akhafiz
 */
class FamilyMemberDaoPostgresJdbcImpl extends BaseDaoPostgresJdbcImpl<FamilyMember> implements FamilyMemberDao {

    @Override
    public FamilyMember findByUserId(Long userId, Connection connection) throws FbDaoException {
        SearchCriteria criteria = createSearchCriteria(connection);
        criteria.addParameter("userId",userId);
        return criteria.getUniqueResult();
    }

    @Override
    public List<FamilyMember> findByFamilyId(Long familyId, Connection connection) throws FbDaoException {
        SearchCriteria criteria = createSearchCriteria(connection);
        criteria.addParameter("familyId",familyId);
        return criteria.getResultList();
    }
}
