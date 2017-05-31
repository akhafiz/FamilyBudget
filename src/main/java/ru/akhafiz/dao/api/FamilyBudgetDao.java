package ru.akhafiz.dao.api;

import ru.akhafiz.dao.exceptions.FbDaoException;
import ru.akhafiz.domain.model.FamilyBudget;

import java.sql.Connection;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public interface FamilyBudgetDao extends Dao<FamilyBudget> {

    FamilyBudget findByFamilyId(Long familyId, Connection connection) throws FbDaoException;

}
