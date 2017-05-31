package ru.akhafiz.dao.api;

/**
 * @author akhafiz
 */
public interface DaoConfiguration {

    String DB_DRIVER_NAME = "db.driver.name";

    String DB_URL = "db.url";

    String DB_USERNAME = "db.username";

    String DB_PASSWORD = "db.password";

    /**
     * @return jdbc driver name
     */
    String getDriverName();

    /**
     * @return url of db connection
     */
    String getConnectionUrl();

    /**
     * @return username db
     */
    String getUsername();

    /**
     * @return password db
     */
    String getPassword();

}
