/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * InitTableProcessor - Provides encapsulation and processing logic for Initial
 * Values tables of the model database
 * 
 * @author Isaac Griffith
 */
public class InitTableProcessor extends TableProcessor {

    /**
     * The SQL Query string to be used to extract information from the database
     * table
     */
    private String defaultQuery = "";
    /**
     * Name of the behavior for this initial values table
     */
    private String behaviorName;
    /**
     * Table name to be used to query for initial stateval information
     */
    private String tableName;

    /**
     * Constructs a new InitTableProcessor associated to the provided
     * NEODbController and DbMediator and for the named behavior.
     * 
     * @param dbControl
     *            NEODbController which contains the needed table name to ensure
     *            processing occurs
     * @param mediator
     *            Mediator which allows the data to be stored in the proper
     *            place
     * @param behaviorName
     *            Behavior name for which this encapsulates initial value
     *            information
     */
    public InitTableProcessor(NEODbController dbControl, DbMediator mediator, String behaviorName)
    {
        super(mediator);
        this.behaviorName = behaviorName;
        try
        {
            tableName = dbControl.getInitValuesTableName(behaviorName);
            defaultQuery = String.format(dbControl.getQuery("INITIAL_VALUES_QUERY"), tableName);
        }
        catch (NEODbControllerException ex)
        {
            NEOLogger.logWarning(mediator.getSimulationModel(), ex.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.database.TableProcessor#processTable(java
     * .sql.Statement)
     */
    public void processTable(Statement statement)
    {
        // FIXME Requires Refactoring
        try
        {
            ResultSet initialEntries = statement.executeQuery(defaultQuery);

            ResultSetMetaData metaData = initialEntries.getMetaData();
            ArrayList<String> columnNames = new ArrayList<String>();

            for (int i = 2; i <= metaData.getColumnCount(); i++)
            {
                try
                {
                    columnNames.add(metaData.getColumnName(i));
                }
                catch (Exception ex)
                {
                    NEOLogger.logWarning(mediator.getSimulationModel(), "Init Table: " + ex.getMessage());
                }
            }

            while (initialEntries.next())
            {
                String holonID = initialEntries.getString(1);

                for (String column : columnNames)
                {
                    String value = initialEntries.getString(column);
                    if (value == null || value.equals(""))
                    {
                        continue;
                    }
                    else
                    {
                        createAndRegisterTriple(initialEntries, metaData, holonID, value, columnNames, column);
                    }
                }
            }
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "Init Table: SQL Error.", sqlex);
        }
    }

    /**
     * Creates and registres a Triple object, an intermediary object used to
     * encapsulate the information necessary to generate StateVals.
     * 
     * @param initialEntries
     *            ResultSet representing the initial value table entries
     * @param metaData
     *            MetaData for the provided ResultSet
     * @param holonID
     *            ID of the holon into which the StateVal will need to be
     *            installed
     * @param value
     *            Actual value of the StateVal
     * @param columnNames
     *            List of the Column Names in the result set.
     * @param column
     *            Column name to be used for extracting the necessary
     *            information.
     * @throws SQLException
     *             Thrown if there is a problem extracting information from the
     *             database.
     */
    private void createAndRegisterTriple(ResultSet initialEntries, ResultSetMetaData metaData, String holonID,
            String value, List<String> columnNames, String column) throws SQLException
    {
        int type = metaData.getColumnType(columnNames.indexOf(column) + 2);
        String svType = null;
        Object svValue = null;

        switch (type)
        {
        case Types.DOUBLE:
            svType = "Double";
            svValue = Double.valueOf(value);
            break;
        case Types.INTEGER:
            svType = "Integer";
            svValue = Integer.valueOf(value);
            break;
        case Types.NVARCHAR:
        case Types.VARCHAR:
            svType = "String";
            svValue = value;
            break;
        default:
            svType = metaData.getColumnTypeName(columnNames.indexOf(column) + 1);
            svValue = initialEntries.getObject(column);
            break;
        }
        mediator.addInitialValueTriple(tableName, holonID, new Triple<String, String, Object>(column, svType, svValue));
    }
}
