/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.OutputConfigManagerException;

/**
 * NEODbController - Contains the constants used to control both the retrieval
 * of information and proper processing of information stored in the Model
 * Control database. It also provides a storage location for all necessary table
 * names used by each TableProcessor. This class is also responsible for
 * initiating and controlling the database processing process.
 * 
 * @author Isaac Griffith
 */
public class NEODbController {

    /**
     * Constant representing the default timestep if one is not specified by the
     * modeler
     */
    protected static final double DEFAULT_TIMESTEP = 1.0d;
    /**
     * Constant representing the default starting time if one is not specified
     * by the modeler
     */
    protected static final double DEFAULT_TIMESEED = 0.0d;
    /**
     * Connection to the Model Control Database used during database processing
     */
    private Connection connection;
    /**
     * Mediator connecting the database processing system to the SimulationModel
     */
    private DbMediator mediator;
    /**
     * The list of TableProcessors used to process the tables of the Model
     * Control Database
     */
    private List<TableProcessor> processors;
    /**
     * name of the Timing Table
     */
    private String timingTableName = "";
    /**
     * name of the cell table
     */
    private String cellTableName = "";
    /**
     * name of the cell type table
     */
    private String cellTypeTableName = "";
    /**
     * name of the edge table
     */
    private String edgeTableName = "";
    /**
     * name of the edge type table
     */
    private String edgeTypeTableName = "";
    /**
     * name of the cell default values table
     */
    private String defaultCellValsTableName = "";
    /**
     * name of the edge default values table
     */
    private String defaultEdgeValsTableName = "";
    /**
     * name of the output configuration table in the database
     */
    private String outputConfigTableName = "";
    /**
     * name of the updatable configuration table in the database
     */
    private String updatableConfigTableName;
    /**
     * Hashmap of initial value table names indexed by their associated behavior
     * names
     */
    private HashMap<String, String> initTables = new HashMap<String, String>();
    /**
     * Properties object containing named sql queries used to extract
     * information from the NEO Input tables.
     */
    private Properties queries;

    /**
     * Constructs a new NEODbController associated with the provided DbMediator
     * 
     * @param mediator
     *            Mediator which provides storage methods for information
     *            retrievied by TableProcessors
     * @param queries
     *            The Properties object containing the named SQL queries used by
     *            the NEO Framework to extract information from a NEO Model
     *            Input Database, in order to build a working model.
     */
    public NEODbController(DbMediator mediator, Properties queries)
    {
        this.mediator = mediator;
        this.queries = queries;
        processors = new ArrayList<TableProcessor>();
    }

    /**
     * Initiates and controls the processing of Database Tables in the Model
     * Control Database. The provided string is the location of the file which
     * contains the properties related to establishing the connection to the
     * Model Control Database.
     * 
     * @param control
     *            DbConfig object describing the necessary information to
     *            connect to the NEO Input Database for the model at hand.
     */
    public void processDb(DbConfig control)
    {
        // FIXME Requires Refactoring
        try
        {
            connection = mediator.getControlDbConnection(control);
            Statement statement = connection.createStatement();

            // Process Control Table
            initializeControlTableProcessor();
            processTables(statement);

            // Process Remaining Tables
            initializeRemainingTableProcessors();
            processTables(statement);

            // done retrieving data, close the connection
            statement.close();
            close();
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "Network: SQL Error.", sqlex);
        }
    }

    /**
     * Processes each table in the processors list.
     * 
     * @param statement
     *            Statement to be used to handle database table processing
     */
    private void processTables(Statement statement)
    {
        for (int i = 0; i < processors.size(); i++)
        {
            processors.get(i).processTable(statement);
        }
    }

    /**
     * Initializes the processor list to contain the ControlTableProcessor.
     */
    private void initializeControlTableProcessor()
    {
        processors.add(new ControlTableProcessor(this, mediator));
    }

    /**
     * Initializes the processor list to contain remaining types of
     * TableProcessors
     * 
     * @throws SQLException
     *             if database metadata cannot be read.
     */
    private void initializeRemainingTableProcessors() throws SQLException
    {
        processors.clear();

        processors.add(new CellTableProcessor(this, mediator));
        processors.add(new CellTypesTableProcessor(this, mediator));
        processors.add(new EdgeTableProcessor(this, mediator));
        processors.add(new EdgeTypesTableProcessor(this, mediator));
        processors.add(new DefaultTableProcessor(this, mediator, false));
        processors.add(new DefaultTableProcessor(this, mediator, true));
        processors.add(new TimingTableProcessor(this, mediator));
        processors.add(new OutputConfigTableProcessor(this, mediator));

        if (updatableConfigTableName != null && !updatableConfigTableName.isEmpty())
            processors.add(new UpdatableConfigTableProcessor(this, mediator));

        // Find the resource and initial values tables
        DatabaseMetaData dbmd = connection.getMetaData();
        ResultSet tableNames = dbmd.getTables(connection.getCatalog(), null, "%", null);

        while (tableNames.next())
        {
            String name = tableNames.getString("TABLE_NAME");

            if (name.contains("init_"))
            {
                String behavior = name.substring(name.indexOf("_") + 1).replace("_", ".");
                initTables.put(behavior, name);
                processors.add(new InitTableProcessor(this, mediator, behavior));
            }
            else if (mediator.getCurrencies().contains(name))
            {
                processors.add(new UpdatableConfigTableProcessor(this, null));
            }
        }
        tableNames.close();
    }

    /**
     * Closes the connection to the model control database
     */
    protected void close()
    {
        try
        {
            connection.close();
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(),
                    "SQL Exception on closing connection to the database", sqlex);
        }
    }

    /**
     * Provides a means to retrieve the name of the Matrix Cell Input Table
     * Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The Matrix Cell Table Name
     */
    public String getCellTableName()
    {
        return cellTableName;
    }

    /**
     * Provides a means to retrieve the name of the Matrix Cell Type Input Table
     * Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The Matrix Cell Types Table Name
     */
    public String getCellTypeTableName()
    {
        return cellTypeTableName;
    }

    /**
     * Provides a means to retrieve the name of the Default Cell StateVal Input
     * Table Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The Default Cell Values Table Name
     */
    public String getDefaultCellTableName()
    {
        return defaultCellValsTableName;
    }

    /**
     * Provides a means to retrieve the name of the Default Edge StateVal
     * Information Input Table Name, as stored in the Control Table of the NEO
     * Model Input Tables Database.
     * 
     * @return The Default Edge Values Table Name
     */
    public String getDefaultEdgeTableName()
    {
        return defaultEdgeValsTableName;
    }

    /**
     * Sets the Default Edge Values table name to the name specified.
     * 
     * @param tableName
     *            Name of the default edge values table.
     */
    public void setDefaultEdgeTableName(String tableName)
    {
        this.defaultEdgeValsTableName = tableName;
    }

    /**
     * Provides a means to retrieve the name of the Matrix Edge Input Table
     * Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The Matrix Edge Table Name
     */
    public String getEdgeTableName()
    {
        return this.edgeTableName;
    }

    /**
     * Sets the Matrix DependencyEdge Table name to the provided string.
     * 
     * @param tableName
     *            Name of the Matrix Edge Table
     */
    public void setEdgeTableName(String tableName)
    {
        this.edgeTableName = tableName;
    }

    /**
     * Provides a means to retrieve the name of the Matrix Edge Type Input Table
     * Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The Matrix Edge Type Table Name
     */
    public String getEdgeTypeTableName()
    {
        return this.edgeTypeTableName;
    }

    /**
     * Sets the Matrix Edge Type Table name to be the string provided.
     * 
     * @param tableName
     *            Matrix Edge Type table name.
     */
    public void setEdgeTypeTableName(String tableName)
    {
        this.edgeTypeTableName = tableName;
    }

    /**
     * For the given behavior name this method returns the associated initial
     * values table name.
     * 
     * @param behaviorName
     *            Name of the Behavior whose Initial Values Table name is
     *            required
     * @return Name of the Initial Values Table for the given behavior name, if
     *         no initial values table name has been associated with the
     *         provided behavior name, then a NEODbControllerException is
     *         thrown.
     * @throws NEODbControllerException
     *             Thrown if an initial values table name cannot be found for
     *             the provided behavior name.
     */
    public String getInitValuesTableName(String behaviorName) throws NEODbControllerException
    {
        if (initTables.containsKey(behaviorName))
        {
            return initTables.get(behaviorName);
        }
        else
        {
            throw new NEODbControllerException("Initial Values Table for " + behaviorName + " was not found");
        }
    }

    /**
     * Sets the Matrix Cell Table Name to be the provided string.
     * 
     * @param tableName
     *            New name of the Matrix Cell Table.
     */
    public void setCellTableName(String tableName)
    {
        this.cellTableName = tableName;
    }

    /**
     * Sets the Marix Cell Type Table name to the provided table name.
     * 
     * @param tableName
     *            New Matrix Cell Type table name.
     */
    public void setCellTypeTableName(String tableName)
    {
        this.cellTypeTableName = tableName;
    }

    /**
     * Sets the Default Cell Values Table name to the the string provided
     * 
     * @param tableName
     *            The new Default Cell Values Table name.
     */
    public void setDefaultCellTableName(String tableName)
    {
        this.defaultCellValsTableName = tableName;
    }

    /**
     * Retrieves a query to be used to extract information from the NEO input
     * database.
     * 
     * @param queryName
     *            Name of the query to retrieve.
     * @return An SQL query string, or null if no such query, associated with
     *         the provide name, exists.
     */
    public String getQuery(String queryName)
    {
        return queries.getProperty(queryName);
    }

    /**
     * Provides a means to retrieve the name of the Output Configuration Input
     * Table Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The output configuration table name.
     */
    public String getOutputConfigTableName()
    {
        return outputConfigTableName;
    }

    /**
     * Sets the name of the table to be used to extract output configuration
     * information from. The table with this name must reside within the current
     * NEO input table.
     * 
     * @param tableName
     *            The new name of the output configuration table.
     */
    public void setOutputConfigTableName(String tableName)
    {
        this.outputConfigTableName = tableName;
    }

    /**
     * Provides a means to retrieve the name of the Timing Information Input
     * Table Name, as stored in the Control Table of the NEO Model Input Tables
     * Database.
     * 
     * @return The name of the timing table in the NEO input database
     */
    public String getTimingTableName()
    {
        return this.timingTableName;
    }

    /**
     * Sets the name of the table to be used to extract timing information.
     * 
     * @param tableName
     *            name of the timing table in the database
     */
    public void setTimingTableName(String tableName)
    {
        this.timingTableName = tableName;
    }

    /**
     * Sets the base name of the table to be used to store initial values output
     * 
     * @param tableName
     *            The name of the initial values output table
     */
    public void setOutputInitalValuesTableName(String tableName)
    {
        try
        {
            mediator.getSimulationModel().getOutputConfigManager().setOutputInitialValuesTable(tableName);
        }
        catch (OutputConfigManagerException e)
        {
            NEOLogger.logException(mediator.getSimulationModel(), e.getMessage(), e);
        }
    }

    /**
     * Sets the name of the IUpdatable Configuration database table
     * 
     * @param tableName
     *            The IUpdatable Configuration table name
     */
    public void setUpdatableConfigTableName(String tableName)
    {
        this.updatableConfigTableName = tableName;
    }

    /**
     * Retrieves the name of the table containing the IUpdatable Configuration
     * information
     * 
     * @return IUpdatable Config table name
     */
    public String getUpdatableConfigTableName()
    {
        return this.updatableConfigTableName;
    }
}