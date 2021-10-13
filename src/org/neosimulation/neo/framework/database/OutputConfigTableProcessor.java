/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.neosimulation.neo.framework.config.OutputConfig;
import org.neosimulation.neo.framework.config.OutputConfigException;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.OutputConfigManager;
import org.neosimulation.neo.framework.manager.OutputConfigManagerException;

/**
 * OutputConfigTableProcessor - Provides an encapsulation and processing logic
 * for the Output Configuration Table of the Model Database
 * 
 * @author Isaac Griffith
 */
public class OutputConfigTableProcessor extends TableProcessor {

    /**
     * The SQL Query string to be used to extract information from the database
     * table
     */
    private String defaultQuery = "";
    /**
     * The associated OutputConfigManager to which the read in OutputConfigs
     * will be stored.
     */
    private OutputConfigManager manager;
    /**
     * Table name to be used to query for config information
     */
    private String tableName;

    /**
     * Constructs a new OutputConfigTableProcessor instance
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public OutputConfigTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);
        manager = mediator.getOutputConfigManager();
        tableName = dbControl.getOutputConfigTableName();
        defaultQuery = String.format(dbControl.getQuery("OUTPUT_CONFIG_QUERY"), tableName, tableName);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.database.TableProcessor#processTable(
     * java.sql.Statement)
     */
    @Override
    public void processTable(Statement statement)
    {
        try (ResultSet configEntries = statement.executeQuery(defaultQuery))
        {
            verifyCanProcess(configEntries);

            while (configEntries.next())
            {
                createConfigEntry(configEntries);
            }
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logException(mediator.getSimulationModel(), "OutputConfig Table SQL Error.", sqlex);
        }
    }

    /**
     * Creates a new OutputConfig object from the information in the next result
     * of the provided result set object.
     * 
     * @param configEntries
     *            ResultSet object.
     * @throws SQLException
     *             Thrown if a problem is encountered when extracting
     *             information from the ResultSet object.
     */
    private void createConfigEntry(ResultSet configEntries) throws SQLException
    {
        String UID = configEntries.getString("UID");
        String table = getTableName(configEntries);
        int timeInterval = configEntries.getInt(3);
        String holonType = configEntries.getString(4);
        String holon = configEntries.getString(5);
        String currency = configEntries.getString(6);
        String behavior = configEntries.getString(7);
        String stateVal = configEntries.getString(8);
        boolean active = configEntries.getBoolean(9);

        if (active)
        {
            try
            {
                OutputConfig newConfig = new OutputConfig(this.mediator.getCurrencyManager(), timeInterval, UID, table,
                        holonType, holon, currency, behavior, stateVal);
                manager.addConfig(newConfig);
            }
            catch (OutputConfigException e)
            {
                NEOLogger.logException(mediator.getSimulationModel(), "Could not create output configuration: " + UID
                        + ".\n" + e.getMessage(), e);
            }
            catch (OutputConfigManagerException e)
            {
                NEOLogger.logException(mediator.getSimulationModel(), "Could not add the output configruation: " + UID
                        + ", to the output configuration manager for model run: "
                        + mediator.getSimulationModel().getName() + ". ", e);
            }

        }
    }

    /**
     * Retrieves the table name for the next entry in the provided result set.
     * 
     * @param configEntries
     *            ResultSet to be read
     * @return TableName to be associated with the config in the next entry of
     *         the result set.
     * @throws SQLException
     *             Thrown if a problem is encountered reading information from
     *             the provided result set object.
     */
    private String getTableName(ResultSet configEntries) throws SQLException
    {
        if (mediator.isUniqueTableNames())
        {
            return this.mediator.getModelName() + "_" + configEntries.getString(2);
        }
        else
        {
            return this.mediator.getModelName().substring(0, mediator.getModelName().lastIndexOf("-")) + "_"
                    + configEntries.getString(2);
        }
    }

    /**
     * Tests whether the provided result set object can be processed.
     * 
     * @param configEntries
     *            ResultSet representing a connection to the OutputConfig Table
     * @throws SQLException
     *             Thrown if the a problem is encountered when processing the
     *             table.
     */
    private void verifyCanProcess(ResultSet configEntries) throws SQLException
    {
        // TODO Validate that throwing an error is the correct decision here

        ResultSetMetaData metaData = configEntries.getMetaData();
        if (metaData.getColumnCount() < 9)
        {
            NEOLogger.logException(mediator.getSimulationModel(), "Output Configuration Table has too few columns",
                    new OutputConfigTableProcessorException());
        }
    }
}
