/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * DatabaseManager - Provides a means for the NEO Framework to create and store
 * database connection associated with a SimulationModel, Interpolator, etc.
 * 
 * @author Isaac Griffith
 */
public class DatabaseManager implements Serializable {

    /**
     * The properties corresponding to the simulation's control database's
     * connection properties
     */
    private DbConfig controlConfig;
    /**
     * Hashmap relating the named database connection to it's associated
     * properties.
     */
    private HashMap<String, DbConfig> knownConnections;

    /**
     * Constructs a new Database Manager
     */
    public DatabaseManager()
    {
        knownConnections = new HashMap<>();
    }

    /**
     * Returns a Connection to a database. The information necessary to make
     * this connection is provided in the provided DbConfig object. This method
     * also associates the provided DbConfig as the DbConfig which provides the
     * information necessary to connect to the input database tables (control
     * tables) for the associated simulation model object.
     * 
     * @param control
     *            The input DbConfig for the associated SimulationModel object.
     * @return a connection, as described in the provided DbConfig, to a
     *         database.
     * @throws SQLException
     *             thrown if no connection could be made.
     */
    public Connection getControlDbConnection(DbConfig control) throws SQLException
    {
        controlConfig = control;

        String drivers = controlConfig.getDriver();
        if (drivers != null && !drivers.equals(System.getProperty("jdbc.drivers")))
            System.setProperty("jdbc.drivers", drivers);

        String url = controlConfig.getUrl();
        String username = controlConfig.getUsername();
        String password = controlConfig.getPassword();

        return JDBCConnectionFactory.openConnection(drivers, url, username, password);
    }

    /**
     * Returns the properties object representing the control database's
     * connection information.
     * 
     * @return The DbConfig representing the connection information to connect
     *         to the associated SimulationModel's input database.
     */
    public DbConfig getControlConfig()
    {
        return controlConfig;
    }

    /**
     * Returns a connection to the control database if and only if the
     * connection properties have previously been provided to the database
     * manager.
     * 
     * @return A connection to the control database.
     * @throws SQLException
     *             if the connection cannot be created.
     * @throws DatabaseManagerException
     *             If the control database's properties have not been previously
     *             set.
     */
    public Connection getControlDbConnection() throws SQLException, DatabaseManagerException
    {
        if (controlConfig != null)
        {
            String driver = controlConfig.getDriver();
            if (driver != null && !System.getProperty("jdbc.drivers").equals(driver))
                System.setProperty("jdbc.drivers", driver);

            String url = controlConfig.getUrl();
            String username = controlConfig.getUsername();
            String password = controlConfig.getPassword();

            return JDBCConnectionFactory.openConnection(driver, url, username, password);
        }
        else
        {
            throw new DatabaseManagerException(
                    "Connection Properties specifying how to connect to the control database have yet to be provided.");
        }
    }

    /**
     * Returns the connection to a database that has been previously associated
     * with the given name.
     * 
     * @param name
     *            Name of a known connection which has been previously
     *            established and stored.
     * @return Connection representing a connection to a database which has
     *         previously been established and whose properties have been stored
     *         under the name provided.
     * @throws SQLException
     *             Thrown if a connection cannot be established using the known
     *             information.
     * @throws DatabaseManagerException
     *             Thrown if the name provided does not reference a known
     *             connection.
     */
    public Connection getConnection(String name) throws SQLException, DatabaseManagerException
    {
        DbConfig config = null;

        if (knownConnections.containsKey(name))
        {
            config = knownConnections.get(name);

            String driver = config.getDriver();
            String url = config.getUrl();
            String username = config.getUsername();
            String password = config.getPassword();

            return JDBCConnectionFactory.openConnection(driver, url, username, password);
        }
        else
        {
            throw new DatabaseManagerException("Named database connection: " + name
                    + " is unknown and therefore a connection cannot be established.");
        }
    }

    /**
     * Creates a connection based on the information contained in the provided
     * DbConfig.
     * 
     * @param dbConfig
     *            DbConfig encapsulating the information required to make a
     *            database connection.
     * @return A connection to a database or null if no connection can be made.
     */
    public Connection createConnection(DbConfig dbConfig)
    {
        if (!knownConnections.containsKey(dbConfig.getName()))
        {
            knownConnections.put(dbConfig.getName(), dbConfig);
        }

        try
        {
            return JDBCConnectionFactory.openConnection(dbConfig.getDriver(), dbConfig.getUrl(),
                    dbConfig.getUsername(), dbConfig.getPassword());
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * Retrieves the current number of known connections managed by this
     * DatabaseManager.
     * 
     * @return The number of created connections
     */
    public int getNumberOfKnownConnections()
    {
        return knownConnections.size();
    }
}
