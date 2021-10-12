/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * JDBCConnectionFactory - Provides a means to generate connections to a
 * database
 * 
 * @author Isaac Griffith
 */
public class JDBCConnectionFactory {

    /**
     * Opens a connection based on the specific properties stored in the
     * provided file.
     * 
     * @param dbConfig
     *            DbConfig object describing the parameters for the database
     *            conection
     * @return SQL Connection to the database.
     * @throws IOException
     *             thrown if the file cannot be read
     * @throws SQLException
     *             thrown if the database connection cannot be made
     */
    public static Connection openConnection(DbConfig dbConfig) throws IOException, SQLException
    {
        Properties props = new Properties();
        
        // Read in the properties
        String drivers = dbConfig.getDriver();
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);

        String url = dbConfig.getUrl();
        String username = dbConfig.getUsername();
        String password = dbConfig.getPassword();

        // Create the connection
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Opens a connection to the database at the provided URL using the provided
     * driver and user credentials
     * 
     * @param driver
     *            name of the JDBC Driver class to be used
     * @param url
     *            URL to the database
     * @param user
     *            Username to be used to connect to the database
     * @param password
     *            Password used to be used to connect to the database
     * @return SQL Connection to the database.
     * @throws SQLException
     *             thrown if the database connection cannot be made
     */
    public static Connection openConnection(String driver, String url, String user, String password)
            throws SQLException
    {
        if (driver != null)
            System.setProperty("jdbc.drivers", driver);
 
  /*      try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    */    
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Opens a connection to the database at the provided URL using default
     * ODBC:JDBC driver and user credentials
     * 
     * @param url
     *            URL to the database
     * @param user
     *            Username to be used to connect to the database
     * @param password
     *            Password used to be used to connect to the database
     * @return SQL Connection to the database.
     * @throws SQLException
     *             thrown if the database connection cannot be made
     */
    public static Connection openConnection(String url, String user, String password) throws SQLException
    {
        if (user == null)
        {
            user = "";
        }

        if (password == null)
        {
            password = "";
        }

        return DriverManager.getConnection(url, user, password);
    }
}
