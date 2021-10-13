package org.neosimulation.neo.framework.database;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.populator.PackageRegistrar;

/**
 * ControlTableProcessor - Provides a strategy to process and verify the data
 * stored in the control table of the Model control database.
 * 
 * @author Isaac Griffith
 */
public class ControlTableProcessor extends TableProcessor {

    /**
     * The NEO Db Controller object which is used to contain a majority of
     * control/environment specific information from the control table.
     */
    private NEODbController dbControl;

    /**
     * Constructs a new ControlTableProcessor associated to the provided
     * DatabaseManager.
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public ControlTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);
        this.dbControl = dbControl;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.database.TableProcessor#processTable(java
     * .sql.Statement)
     */
    public void processTable(Statement statement)
    {
        try
        {
            retrieveTableNames(statement);
            retrieveCurrency(statement);
            registerCurrency(statement);
        }
        catch (SQLException | IOException ex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "Control Table Exception", ex);
        }
    }

    /**
     * Looks up the table names for each of the matrix tables (edge type, cell
     * type, edge, and cell) for use in retrieving information to build the
     * matrix
     * 
     * @param statement
     *            Statement object which provides a connection to the database
     * @throws SQLException
     *             if there is a problem connecting to or querying from the
     *             database
     */
    private void retrieveTableNames(Statement statement) throws SQLException
    {
        dbControl.setCellTableName(getTableName(dbControl.getQuery("CELL_TABLE_NAME_QUERY"), statement));
        dbControl.setEdgeTableName(getTableName(dbControl.getQuery("EDGE_TABLE_NAME_QUERY"), statement));
        dbControl.setCellTypeTableName(getTableName(dbControl.getQuery("CELL_TYPE_TABLE_NAME_QUERY"), statement));
        dbControl.setEdgeTypeTableName(getTableName(dbControl.getQuery("EDGE_TYPE_TABLE_NAME_QUERY"), statement));
        dbControl.setDefaultCellTableName(getTableName(dbControl.getQuery("DEFAULT_VALS_CELL_TABLE_NAME_QUERY"),
                statement));
        dbControl.setDefaultEdgeTableName(getTableName(dbControl.getQuery("DEFAULT_VALS_EDGE_TABLE_NAME_QUERY"),
                statement));
        dbControl.setTimingTableName(getTableName(dbControl.getQuery("TIMING_TABLE_NAME_QUERY"), statement));
        dbControl
                .setOutputConfigTableName(getTableName(dbControl.getQuery("OUTPUT_CONFIG_TABLE_NAME_QUERY"), statement));
        dbControl.setOutputInitalValuesTableName(getTableName(
                dbControl.getQuery("OUTPUT_INITIAL_VALUES_TABLE_NAME_QUERY"), statement));
    }

    /**
     * Retrieves the tablename from the control table using the specified query
     * string and statement object.
     * 
     * @param query
     *            SQL query string related to extracting the required table name
     *            from the control table
     * @param statement
     *            Statement which is used to execute the SQL query
     * @return String representing the table name desired, or null if no such
     *         table name is found.
     * @throws SQLException
     *             If there is a problem executing the SQL query
     */
    private String getTableName(String query, Statement statement) throws SQLException
    {
        ResultSet control = statement.executeQuery(query);
        control.next();
        String retVal = control.getString("Val");
        control.close();

        return retVal;
    }

    /**
     * Registers all currencies that are active for the given model. That is it
     * adds each individual currency's package location to the System class
     * loader via the PackageRegistrar.
     * 
     * @param state
     *            The Statement object which provides a connection to the
     *            database in order to facilitate gaining necessary information
     *            to register currency classes
     * @throws IOException
     *             Thrown when the package register cannot locate the specific
     *             currency's file location
     * @throws SQLException
     *             Thrown if there is a problem connecting to or querying from
     *             the database
     */
    private void registerCurrency(Statement state) throws IOException, SQLException
    {
        String relPath;
        if (!(relPath = getRelPath(state)).isEmpty())
        {
            extractAndRegisterACurrency(state, dbControl.getQuery("CURRENCY_LOCATION_RELATIVE_PATH_QUERY"), true,
                    relPath);
        }

        // Determine the location of the package from the Control table
        extractAndRegisterACurrency(state, dbControl.getQuery("CURRENCY_LOCATION_FULL_PATH_QUERY"), false, null);
    }

    /**
     * Extracts a currency using the provided SQL query string and statement
     * object. If the relative flag is provided then the relpath string is used
     * as the base path for the currency to be registerd. Otherwise, the path
     * found is treated as the full path to the currency package.
     * 
     * @param state
     *            Statement object used to execute the SQL query string.
     * @param query
     *            SQL query string
     * @param relative
     *            Flag indicating whether relative paths are used or not.
     * @param relPath
     *            Relative path base path (if relative paths are in use)
     * @throws SQLException
     *             Thrown if there is a problem executing the SQL query
     * @throws IOException
     *             Thrown if there is a problem accessing the currency location.
     */
    private void extractAndRegisterACurrency(Statement state, String query, boolean relative, String relPath)
            throws SQLException, IOException
    {
        ResultSet control = state.executeQuery(query);
        while (control.next())
        {
            String currencyName = control.getString("Key");
            Currency currency = extractCurrencyNameAndGetCurrency(currencyName);

            String packageLoc = control.getString("Val");

            if (relative)
            {
                if (packageLoc.startsWith(File.separator))
                    packageLoc = packageLoc.substring(1);

                packageLoc = relPath + packageLoc;
            }

            registerCurrency(currency, packageLoc);
        }
        control.close();
    }

    /**
     * Retrieves the relative path for currencies, if such a path exists.
     * 
     * @param state
     *            Statement on which to execute the SQL query for relative base
     *            paths.
     * @return The relative base path is such a path exists, null otherwise.
     * @throws SQLException
     *             Thrown if there is a problem executing the SQL query string.
     */
    private String getRelPath(Statement state) throws SQLException
    {
        String relPath = "";
        ResultSet control = state.executeQuery(dbControl.getQuery("CURRENCY_BASEPATH_QUERY"));
        if (control.next())
        {
            relPath = control.getString("Val");
            if (!relPath.endsWith(File.separator))
            {
                relPath = relPath + File.separator;
            }
        }
        control.close();
        return relPath;
    }

    /**
     * Given the provided value from the control table, which describes a
     * currency location key. We need to extract the name of the currency from
     * that key, retrieve the currency, and then return that currency to the
     * caller.
     * 
     * @param value
     *            key from the control table containing the currency name, where
     *            the key is of the form .*'.'<currency-name>'.'.*
     * @return The currency object with the same name which has been extracted.
     */
    private Currency extractCurrencyNameAndGetCurrency(String value)
    {
        int index1 = value.indexOf(".");
        int index2 = value.indexOf(".", index1 + 1);
        value = value.substring(index1 + 1, index2);

        Currency currency = mediator.getCurrency(value);
        return currency;
    }

    /**
     * Attempts to register the provided currency to the provided package
     * location.
     * 
     * @param currency
     *            Currency to actually be registered
     * @param packageLoc
     *            The location of the package containing this currency
     * @throws IOException
     *             Thrown if the package location is not accessible or does not
     *             exist.
     */
    private void registerCurrency(Currency currency, String packageLoc) throws IOException
    {
        currency.setLocation(packageLoc);

        // Register the package with the package registrar
        if (packageLoc.endsWith(".jar"))
        {
            PackageRegistrar.registerJar(currency, new File(packageLoc));
        }
        else
        {
            PackageRegistrar.registerDirectory(currency, new File(packageLoc));
        }
    }

    /**
     * Gets all the active resources from the control table
     * 
     * @param statement
     *            Statement object that provides a connection to the database
     * @throws SQLException
     *             if there is a problem connecting to or querying from the
     *             database
     */
    private void retrieveCurrency(Statement statement) throws SQLException
    {
        ResultSet results = statement.executeQuery(dbControl.getQuery("AVAILABLE_CURRENCIES_QUERY"));
        
        while (results.next())
        {
            String temp = results.getString("Val");
//            String key = results.getString("Key");
//            System.out.printf("Key: %s\tCurrency Found: %s\n", key, temp);
            if (temp != null)
            {
                mediator.registerCurrency(temp);
            }
        }
        results.close();

        results = statement.executeQuery(dbControl.getQuery("CURRENCY_BASEPKG_QUERY"));

        while (results.next())
        {
            String temp = results.getString("Key");
            for (Currency currency : mediator.getCurrencies())
            {
                if (temp.contains("." + currency + "."))
                {
                    String t = results.getString("Val") + "." + currency;
                    currency.setBasePackage(t);
                }
            }
        }

        results.close();
    }
}
