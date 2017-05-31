package ru.akhafiz.dao.impl.postgres;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import ru.akhafiz.DBTestBase;
import ru.akhafiz.dao.api.UserDao;
import ru.akhafiz.domain.model.User;

import java.math.BigInteger;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class ITUserDaoPostgresJdbcImpl extends DBTestBase {

    private static final String USER_DATASET_FILENAME = "../test-classes/ru.akhafiz.domain.model.user/user-data.xml";
    private static final String USER_EXPECTED_DELETE_DATASET_FILENAME = "../test-classes/ru.akhafiz.domain.model.user/user-delete-expected-data.xml";
    private static final String USER_EXPECTED_SAVE_DATASET_FILENAME = "../test-classes/ru.akhafiz.domain.model.user/user-save-expected-data.xml";
    private static final String USER_EXPECTED_UPDATE_DATASET_FILENAME = "../test-classes/ru.akhafiz.domain.model.user/user-update-expected-data.xml";
    private static final String USER_TABLE_NAME = "FB_USER";

    public ITUserDaoPostgresJdbcImpl(String name) {
        super(name);
    }

    public void testCreateOrUpdateOne() throws Exception {
        User user = new User();
        user.setLogin("akhafiz");
        user.setEmail("akhafiz@akhafiz.ru");
        user.setPassword("akhafiz");

        try {
            getDaoFactory().openConnection();
            User savedUser = getDaoFactory().createDao(UserDao.class).
                    createOrUpdate(user, getDaoFactory().getCurrentConnection());
            assertNotNull(savedUser);
            assertNotNull(savedUser.getId());
        } finally {
            getDaoFactory().closeConnection();
        }

        //TODO пробоема автоитнремента primrty key
        /*Assertion.assertEquals(getExpectedTable(USER_TABLE_NAME,USER_EXPECTED_SAVE_DATASET_FILENAME),
                getActualTable(USER_TABLE_NAME));*/
    }

    public void testCreateOrUpdateTwo() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setLogin("akhafiz");
        user.setEmail("akhafiz@akhafiz.ru");
        user.setPassword("akhafiz");

        try {
            getDaoFactory().openConnection();
            User savedUser = getDaoFactory().createDao(UserDao.class).
                    createOrUpdate(user, getDaoFactory().getCurrentConnection());
        } finally {
            getDaoFactory().closeConnection();
        }

        Assertion.assertEquals(getExpectedTable(USER_TABLE_NAME,USER_EXPECTED_UPDATE_DATASET_FILENAME),
                getActualTable(USER_TABLE_NAME));

    }

    public void testDeleteById() throws Exception {
        try {
            getDaoFactory().openConnection();
            getDaoFactory().createDao(UserDao.class).deleteById(1L, getDaoFactory().getCurrentConnection());
        } finally {
            getDaoFactory().closeConnection();
        }

        Assertion.assertEquals(getExpectedTable(USER_TABLE_NAME,USER_EXPECTED_DELETE_DATASET_FILENAME),
                getActualTable(USER_TABLE_NAME));
    }

    public void testFindById() throws Exception {
        try {
            getDaoFactory().openConnection();
            User user = getDaoFactory().createDao(UserDao.class).
                    findById(1L, getDaoFactory().getCurrentConnection());
            assertNotNull(user);
        } finally {
            getDaoFactory().closeConnection();
        }

    }

    public void testFindAll() throws Exception {

    }

    public void testFindByLogin() throws Exception {

    }

    @Override
    protected String getDataSetFileName() {
        return USER_DATASET_FILENAME;
    }
}
