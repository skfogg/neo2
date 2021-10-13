/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.mtis.UpdatableConfig;
import org.neosimulation.neo.framework.mtis.UpdatableConfigException;
import org.neosimulation.neo.framework.mtis.UpdatableConfigManager;
import org.neosimulation.neo.framework.mtis.UpdatableConfigManagerException;

/**
 * UpdatableConfigTableProcessor - A SQL Table Processor that handles the
 * Updatable Config Table of a NEO Model Definition Database.
 * 
 * @author Isaac
 */
public class UpdatableConfigTableProcessor extends TableProcessor {

    /**
     * The SQL Query string to be used to extract information from the database
     * table
     */
    private String defaultQuery = "";
    /**
     * Table name to be used to query for config information
     */
    private String tableName;
    /**
     * The associated UpdatableConfigManager to which the read in
     * UpdatableConfigs will be stored.
     */
    private UpdatableConfigManager manager;

    /**
     * Constructs a new UpdatableConfigTableProcessor instance
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public UpdatableConfigTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);
        manager = mediator.getUpdatableConfigManager();
        tableName = dbControl.getOutputConfigTableName();
        defaultQuery = String.format(dbControl.getQuery("UPDATABLE_CONFIG_QUERY"), tableName, tableName);
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
     * Creates a new UpdatableConfig object from the information in the next
     * result of the provided result set object.
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
        int timeInterval = configEntries.getInt("TimeInterval");
        String holon = configEntries.getString("Holon");
        String holonType = configEntries.getString("HolonType");
        String currency = configEntries.getString("Currency");
        String behavior = configEntries.getString("Behavior");
        boolean active = configEntries.getBoolean("Active");
        String dynam = configEntries.getString("Dynam");

        if (active)
        {
            try
            {
                UpdatableConfig newConfig = new UpdatableConfig(mediator.getCurrencyManager(), timeInterval, UID,
                        holonType, holon, currency, behavior, dynam);
                manager.addConfig(newConfig);
            }
            catch (UpdatableConfigException e)
            {
                NEOLogger.logException(mediator.getSimulationModel(), "Could not create updatable configuration: "
                        + UID + ".\n" + e.getMessage(), e);

            }
            catch (UpdatableConfigManagerException e)
            {
                NEOLogger.logException(mediator.getSimulationModel(), "Could not add the updatable configruation: "
                        + UID + ", to the updatable configuration manager for model run: "
                        + mediator.getSimulationModel().getName() + ". ", e);
            }
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
                    new UpdatableConfigTableProcessorException());
        }
    }
}
