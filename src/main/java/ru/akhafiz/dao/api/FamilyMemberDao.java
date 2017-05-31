package ru.akhafiz.dao.api;

import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.FamilyMember;

import java.sql.Connection;
import java.util.List;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public interface FamilyMemberDao extends Dao<FamilyMember> {

    FamilyMember findByUserId(Long userId, Connection connection) throws FbDaoException;

    List<FamilyMember> findByFamilyId(Long familyId, Connection connection) throws FbDaoException;

}
