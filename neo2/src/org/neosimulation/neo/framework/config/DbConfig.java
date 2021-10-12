/**
 * 
 */
package org.neosimulation.neo.framework.config;

/**
 * DbConfig - A class encapsulating the necessary information to generate a
 * connection to a database source using the JDBC.
 * 
 * @author Isaac Griffith
 */
public class DbConfig {

    /**
     * Name of this connection
     */
    private String name;
    /**
     * URL for this connection
     */
    private String url;
    /**
     * Username for access to this connection
     */
    private String username;
    /**
     * Password for access to this connection
     */
    private String password;
    /**
     * JDBC driver to be used to access this connection
     */
    private String driver;
    /**
     * Flag that determines if this dbConfig should be used
     */
    private boolean active;

    /**
     * Creates a new DbConfig
     * 
     * @param name
     *            Identifying name of this configuration
     * @param url
     *            URL of the data source
     * @param username
     *            user name used to access the data source (if required)
     * @param password
     *            password used to access the data source (if required)
     * @param driver
     *            fully qualified class name of the class utilized to access the
     *            data source
     * @param isActive
     *            boolean flag used to determine if this configuration is to be
     *            used or not
     */
    public DbConfig(String name, String url, String username, String password, String driver, boolean isActive)
    {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.active = isActive;
    }

    /**
     * Retrieves the name used to identify this DbConfig to the SimulationModel
     * to which it is associated
     * 
     * @return the identifying name for this data source, used to distinguish
     *         between data sources for a model.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Retrieves the URL pointing to the database to be connected to using this
     * DbConfig
     * 
     * @return the url of the data source to be accessed.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Retrieves the Username credential for the connection to the database
     * specified by this DbConfig
     * 
     * @return the username used to access the database.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Retrieves the Password credential for the connection to the database
     * specified by this DbConfig.
     * 
     * @return the password used to access the database.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Retrieves the fully qualified Java class name used as a JDBC driver for
     * the connection.
     * 
     * @return the fully qualified class name of the classed used to access the
     *         database
     */
    public String getDriver()
    {
        return driver;
    }

    /**
     * Tests whether this DbConfig should be considered for use or not.
     * 
     * @return true if this DbConfig is to actually be used, false otherwise.
     */
    public boolean isActive()
    {
        return active;
    }
}
