package ru.akhafiz.domain.config;

import ru.akhafiz.dao.api.DaoConfiguration;
import ru.akhafiz.domain.exceptions.FbDomainException;

import java.io.*;
import java.util.Properties;

/**
 * <p></p>
 *
 * @author akhafiz
 */
final public class FbConfiguration implements DaoConfiguration {

    private static final String CONF_FILE_NAME = "fb.config.properties";

    private static FbConfiguration instance = new FbConfiguration();

    private String jdbcDriverName;

    private String usernameDb;

    private String passwordDb;

    private String urlDb;

    private FbConfiguration() {
        Properties properties = new Properties();

        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(CONF_FILE_NAME);

        if (resourceStream == null) {
            throw new RuntimeException("ERROR FbConfiguration: configuration file not found: " + CONF_FILE_NAME);
        }

        try {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException("ERROR FbConfiguration: can not read configuration file : " + CONF_FILE_NAME,e);
        }


        this.jdbcDriverName = properties.getProperty(DB_DRIVER_NAME);
        this.urlDb = properties.getProperty(DB_URL);
        this.usernameDb = properties.getProperty(DB_USERNAME);
        this.passwordDb = properties.getProperty(DB_PASSWORD);
    }

    public static FbConfiguration getInstance() {
        return instance;
    }

    @Override
    public String getDriverName() {
        return jdbcDriverName;
    }

    @Override
    public String getConnectionUrl() {
        return urlDb;
    }

    @Override
    public String getUsername() {
        return usernameDb;
    }

    @Override
    public String getPassword() {
        return passwordDb;
    }
}
