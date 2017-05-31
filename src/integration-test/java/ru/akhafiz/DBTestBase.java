package ru.akhafiz;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import ru.akhafiz.dao.api.DaoFactory;
import ru.akhafiz.dao.impl.postgres.DaoFactoryPostgresJdbcImpl;
import ru.akhafiz.domain.config.FbConfiguration;

/**
 * <p></p>
 *
 * @author akhafiz
 */
abstract public class DBTestBase extends DBTestCase {

    private DaoFactory daoFactory;

    private IDataSet beforeDataSet;

    public DBTestBase(String name) {
        super(name);

        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,FbConfiguration.getInstance().getDriverName());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,FbConfiguration.getInstance().getConnectionUrl());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,FbConfiguration.getInstance().getUsername());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,FbConfiguration.getInstance().getPassword());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        if (beforeDataSet == null)
            beforeDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream(getDataSetFileName()));

        return beforeDataSet;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    public DaoFactory getDaoFactory() throws Exception {
        if (daoFactory == null)
            daoFactory = new DaoFactoryPostgresJdbcImpl(FbConfiguration.getInstance());

        return daoFactory;
    }

    abstract protected String getDataSetFileName();

    protected ITable getActualTable(String tableName) throws Exception {
        return getConnection().createDataSet().getTable(tableName);
    }

    protected ITable getExpectedTable(String tableName,String expectedDataSetName) throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream(expectedDataSetName)).
                getTable(tableName);
    }

//    abstract protected String getLiquibaseChageLogName();

//    private void prepareDb() {
//        try {
//            DatabaseConnection con = new JdbcConnection(getConnection().getConnection());
//            ResourceAccessor resAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
//            Liquibase liquibase = new Liquibase(getLiquibaseChageLogName(), resAccessor, con);
//            liquibase.update("");
//        } catch (Exception e) {
//            throw new RuntimeException("error prepare db",e);
//        }
//    }
}
