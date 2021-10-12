/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.BehaviorPair;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.behcurr.CurrencyManager;
import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Edge;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.OutputConfigManager;
import org.neosimulation.neo.framework.model.Matrix;
import org.neosimulation.neo.framework.model.MatrixCreationException;
import org.neosimulation.neo.framework.model.Network;
import org.neosimulation.neo.framework.model.NetworkException;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.mtis.UpdatableConfigManager;
import org.neosimulation.neo.framework.solver.StoppingCondition;

/**
 * DbMediator - Provides a connection between the Database Subsystem and the
 * Simulation Framework. This piece provides the ability to decouple the reading
 * of information and the creation of objects based on that information. By
 * providing a means to pass messages through the mediator we can prevent the
 * Database Processing classes from having to know to much about the system as a
 * whole.
 * 
 * @author Isaac Griffith
 */
public class DbMediator {

    /**
     * The Matrix object to be populated at initialization.
     */
    private Matrix matrix;
    /**
     * The Model representing the simulation and controller of the system
     */
    private SimulationModel model;
    /**
     * The Database Connection Manager which provides a means to establish
     * connections to the database
     */
    private DatabaseManager manager;
    /**
     * The object representing the control information from the database
     */
    private NEODbController dbControl;
    /**
     * The network object to be used to populate the matrix.
     */
    private Network network;

    /**
     * Creates a new DbMediator for the provided SimulationModel which will
     * connect the provided Matrix and Network with the DatabaseProcessing
     * subsystem.
     * 
     * @param model
     *            Simulation Model for which this DbMediator is meant for
     * @param matrix
     *            Matrix to be created from the information in the Database
     * @param network
     *            Network to be populated based on information in the Database.
     */
    public DbMediator(Properties queries, SimulationModel model, Matrix matrix, Network network)
    {
        this.model = model;
        this.matrix = matrix;
        this.network = network;
        this.dbControl = new NEODbController(this, queries);
        this.manager = new DatabaseManager();
    }

    /**
     * Returns the set of all known Cell IDs from the Matrix Cell Table in the
     * Database.
     * 
     * @return Set of Unique Strings representing Cell IDs
     */
    public Set<String> getCellIDs()
    {
        return matrix.getCellIDs();
    }

    /**
     * Returns the associated Cell Type of the cell with the specified unique
     * Cell ID as stored in the Matrix Tables of the Database.
     * 
     * @param cellID
     *            a Unique Cell ID
     * @return a Cell Type
     */
    public String getCellType(String cellID)
    {
        return matrix.getCellType(cellID);
    }

    /**
     * Returns a List of Triple Objects representing the information used to
     * created and populate default statevals for the provided Cell Type.
     * 
     * @param cellType
     *            a known Cell Type
     * @return List of Triples representing the default stateval for the
     *         provided cell type
     */
    public List<Triple<String, String, Object>> getDefaultCellTriples(String cellType)
    {
        return network.getDefaultCellTriples(cellType);
    }

    /**
     * Returns a List of Triple Objects representing the information used to
     * created and populate default statevals for the provided edge type's to
     * side.
     * 
     * @param edgeType
     *            a known DependencyEdge Type
     * @return List of Triples representing the default stateval for the
     *         provided edge type
     */
    public List<Triple<String, String, Object>> getDefaultToEdgeTriples(String edgeType)
    {
        return network.getDefaultToEdgeTriples(edgeType);
    }

    /**
     * Returns a List of Triple Objects representing the information used to
     * created and populate default statevals for the provided edge type's from
     * side.
     * 
     * @param edgeType
     *            a known DependencyEdge Type
     * @return List of Triples representing the default stateval for the
     *         provided edge type
     */
    public List<Triple<String, String, Object>> getDefaultFromEdgeTriples(String edgeType)
    {
        return network.getDefaultFromEdgeTriples(edgeType);
    }

    /**
     * Returns the set of all known DependencyEdge IDs from the Matrix
     * DependencyEdge Table in the Database.
     * 
     * @return Set of Unique Strings representing DependencyEdge IDs
     */
    public Set<String> getEdgeIDs()
    {
        return matrix.getEdgeIDs();
    }

    /**
     * Returns the associated DependencyEdge Type of the cell with the specified
     * unique DependencyEdge ID as stored in the Matrix Tables of the Database.
     * 
     * @param edgeID
     *            a Unique DependencyEdge ID
     * @return a Cell Type
     */
    public String getEdgeType(String edgeID)
    {
        return matrix.getEdgeType(edgeID);
    }

    /**
     * Returns the associated Cell ID of the cell on the from side of the edge
     * with the specified edge id.
     * 
     * @param edgeID
     *            a Unique DependencyEdge ID
     * @return a Cell ID
     */
    public String getFromCellID(String edgeID)
    {
        return matrix.getFromCellID(edgeID);
    }

    /**
     * Returns a List of Triple Objects representing the stateval creation
     * information for a given holon id. The provided table name defines the
     * table in the control database where this information is stored.
     * 
     * @param tableName
     *            Name of the table from where the data was retrieved from.
     * @param holonID
     *            Holon ID of the holon to retrieve state val information for.
     * @return List of Triples representing stateval definitions.
     */
    public List<Triple<String, String, Object>> getInitialValueTriples(String tableName, String holonID)
    {
        return network.getInitialValueTriples(tableName, holonID);
    }

    /**
     * Retrieves the currency manager that is associated with the
     * SimulationModel attached to this DbMediator.
     * 
     * @return The Currency Manager for the associated Simulation Model.
     */
    public CurrencyManager getCurrencyManager()
    {
        return model.getCurrencyManager();
    }

    /**
     * Returns the associated Cell ID of the cell on the to side of the edge
     * with the specified edge id.
     * 
     * @param edgeID
     *            a Unique DependencyEdge ID
     * @return a Cell ID
     */
    public String getToCellID(String edgeID)
    {
        return matrix.getToCellID(edgeID);
    }

    /**
     * Returns the connection to the control database as specified by provided
     * DbConfig instance.
     * 
     * @param control
     *            NEO Input database connection DbConfig instance.
     * @return A connection to the database.
     */
    public Connection getControlDbConnection(DbConfig control)
    {
        Connection retVal = null;
        try
        {
            retVal = manager.getControlDbConnection(control);
        }
        catch (SQLException e)
        {
            NEOLogger.logException(model,
                    "Could not open a connection to the input database for model: " + model.getName(), e);
        }

        return retVal;
    }

    /**
     * Retrieves the connection to the control database, if such a connection
     * has previously been created.
     * 
     * @return The connection to the control database, or null if no connection
     *         could be made.
     */
    public Connection getControlDbConnection()
    {
        Connection retVal = null;
        try
        {
            retVal = manager.getControlDbConnection();
        }
        catch (SQLException | DatabaseManagerException e)
        {
            NEOLogger.logException(model,
                    "Could not open a connection to the input database for model: " + model.getName(), e);
        }

        return retVal;
    }

    /**
     * Retrieves the SimulationModel attached to this DbMediator
     * 
     * @return The simulation model object connected to this mediator.
     */
    public SimulationModel getSimulationModel()
    {
        return model;
    }

    /**
     * Creates a new resource with the specified name and registers it with the
     * current Currency Manager.
     * 
     * @param name
     *            Name of the new Currency.
     * @return The newly created resource.
     */
    public Currency registerCurrency(String name)
    {
        CurrencyManager currManager = model.getCurrencyManager();
        return currManager.registerCurrency(name);
    }

    /**
     * Retrieves a list of all known currencies currently registered with the
     * currency manager of the attached SimulationModel.
     * 
     * @return All currently registered resources associated with the current
     *         simulation model.
     */
    public List<Currency> getCurrencies()
    {
        CurrencyManager currManager = model.getCurrencyManager();
        return currManager.getCurrencies();
    }

    /**
     * Retrieves the currency with the given name, if such a currency exists.
     * 
     * @param name
     *            Name of the currency to be retrieved.
     * @return A Currency Object with the given name, if no such currency
     *         exists, null is returned.
     */
    public Currency getCurrency(String name)
    {
        CurrencyManager currManager = model.getCurrencyManager();
        return currManager.get(name);
    }

    /**
     * Starts the process of retrieving information from the database and
     * initializing the model by building both the matrix and the network.
     * 
     * @param control
     *            The controlling database configuration
     */
    public void processDb(DbConfig control)
    {
        dbControl.processDb(control);
    }

    /**
     * Adds a cell with the provided unique id and provided cell type to the
     * matrix.
     * 
     * @param cellID
     *            A unique id
     * @param cellType
     *            type of the cell
     */
    public void addCell(String cellID, String cellType)
    {
        try
        {
            this.matrix.addCell(cellID, cellType);
        }
        catch (MatrixCreationException e)
        {
            NEOLogger.logException(model, "Could not add Cell with ID: " + cellID + " and Type: " + cellType
                    + " to the Matrix of Model: " + model.getName() + ".", e);
        }
    }

    /**
     * Adds an edge to the matrix with the given unique id and given type. The
     * edge exists between the two to and from cells with the provided ids.
     * 
     * @param edgeID
     *            id of the edge
     * @param edgeType
     *            type of the edge
     * @param fromCellID
     *            from cell id
     * @param toCellID
     *            to cell id
     */
    public void addEdge(String edgeID, String edgeType, String fromCellID, String toCellID)
    {
        try
        {
            this.matrix.addEdge(edgeID, edgeType, fromCellID, toCellID);
        }
        catch (MatrixCreationException e)
        {
            NEOLogger.logException(model, "Could not add Edge with ID: " + edgeID + ", Type: " + edgeType
                    + ", From Cell ID: " + fromCellID + ", and To Cell ID: " + toCellID + " to the Matrix for Model: "
                    + model.getName(), e);
        }
    }

    /**
     * Retrieves the behaviors associated with a cell of the given type.
     * 
     * @param type
     *            cell type
     * @return Set containing all the behaviors which are to be installed in a
     *         cell of the given type, or an empty set if such a type does not
     *         exist.
     */
    public Set<Behavior> getCellBehaviors(String type)
    {
        return matrix.getCellBehaviors(type);
    }

    /**
     * Retrieves the cell associated with the uniqueID from the matrix
     * 
     * @param uniqueID
     *            Cell ID
     * @return The cell associated with the uniqueID from the matrix.
     */
    public Cell getCell(String uniqueID)
    {
        return matrix.getCell(uniqueID);
    }

    /**
     * Retrieves the edge associates with the ID from the matrix
     * 
     * @param edgeID
     *            The edge id
     * @return The edge with the associated ID that links two cells together
     */
    public Edge getEdge(String edgeID)
    {
        return matrix.getEdge(edgeID);
    }

    /**
     * Retrieves the behaviors associated with the from side of an edge of the
     * given edge type.
     * 
     * @param type
     *            edge type
     * @return Set containing all the behaviors which are to be installed in the
     *         from side of the given edge type, or an empty set if such a type
     *         does not exist.
     */
    public Set<Behavior> getFromBehaviors(String type)
    {
        return matrix.getFromBehaviors(type);
    }

    /**
     * Retrieves the behaviors associated with the to side of an edge of the
     * given edge type.
     * 
     * @param type
     *            edge type
     * @return Set containing all the behaviors which are to be installed in the
     *         to side of the given edge type, or an empty set if such a type
     *         does not exist.
     */
    public Set<Behavior> getToBehaviors(String type)
    {
        return matrix.getToBehaviors(type);
    }

    /**
     * Sets the behaviors associated with the a cell of the given cell type.
     * 
     * @param cellType
     *            edge type
     * @param behaviors
     *            The set of behaviors to be added
     */
    public void setCellBehaviors(String cellType, Set<Behavior> behaviors)
    {
        matrix.setCellBehaviors(cellType, behaviors);
    }

    /**
     * Initializes the Time Keeper's StateVals
     * 
     * @param timeStep
     *            The initial value of the timeStep
     * @param timeSeed
     *            The initial value of the time
     * @param tickSeed
     *            The initial value of the tick.
     * @param tickInterval
     *            The interval of the tick.
     */
    public void initializeTimeKeeper(double timeStep, double timeSeed, long tickInterval, long tickSeed)
    {
        this.model.initializeTimeKeeper(timeStep, timeSeed, tickInterval, tickSeed);
    }

    /**
     * Adds a Triple Object representing statevals and their initial values to
     * be installed in the holon with the specified holonID. The information
     * contained in this triple is associated with the provided table name.
     * 
     * @param tableName
     *            table where the initial value triple information came form.
     * @param holonID
     *            Holon ID where the stateval described is to be installed
     * @param triple
     *            The actual triple.
     */
    public void addInitialValueTriple(String tableName, String holonID, Triple<String, String, Object> triple)
    {
        try
        {
            network.addInitialValueTriple(tableName, holonID, triple);
        }
        catch (NetworkException e)
        {
            NEOLogger.logException(model, e.getMessage(), e);
        }
    }

    /**
     * Sets the list of Triple Objects representing statevals and their default
     * values to be installed in the to side of the edge with the specified
     * type.
     * 
     * @param holonType
     *            Holon type where the stateval described is to be installed
     * @param triple
     *            The list of actual triples.
     */
    public void setToEdgeDefaultTriples(String holonType, List<Triple<String, String, Object>> triple)
    {
        network.setToEdgeDefaultTriples(holonType, triple);
    }

    /**
     * Sets the list of Triple Objects representing statevals and their default
     * values to be installed in the from side of the edge with the specified
     * type.
     * 
     * @param holonType
     *            Holon type where the stateval described is to be installed
     * @param triple
     *            The list of actual triples.
     */
    public void setFromEdgeDefaultTriples(String holonType, List<Triple<String, String, Object>> triple)
    {
        network.setFromEdgeDefaultTriples(holonType, triple);
    }

    /**
     * Sets the list of Triple Objects representing statevals and their default
     * values to be installed in the cell with the specified type.
     * 
     * @param holonType
     *            Holon type where the stateval described is to be installed
     * @param triple
     *            The list of actual triples.
     */
    public void setCellDefaultTriples(String holonType, List<Triple<String, String, Object>> triple)
    {
        network.setCellDefaultTriples(holonType, triple);
    }

    /**
     * Adds the provided stopping condition to the execution solver, which
     * provides the ability to stop the loop when the condition is met.
     * 
     * @param stop
     *            Stopping Condition to be added.
     */
    public void addStoppingCondition(StoppingCondition stop)
    {
        model.addStoppingCondition(stop);
    }

    /**
     * Retrieves a reference to the attached NEODbController object.
     * 
     * @return the associated NEO Db Controller information object.
     */
    public NEODbController getNEODbController()
    {
        return dbControl;
    }

    /**
     * Retrieves the output configuration manager responsible for controlling
     * when and where output should be directed.
     * 
     * @return The OuputConfigurationManager associated with this DbMediator's
     *         SimulationModel.
     */
    public OutputConfigManager getOutputConfigManager()
    {
        return model.getOutputConfigManager();
    }

    public UpdatableConfigManager getUpdatableConfigManager()
    {
        return model.getUpdatableConfigManager();
    }

    /**
     * Returns the complete model name of the model associated with this
     * dbconfig. Where the complete model name is specified by:
     * <model-prefix>-<run-name>-<time_date>
     * 
     * @return The model run name associated to the SimulationModel object
     *         associated with this instance of the DbMediator.
     */
    public String getModelName()
    {
        return model.getName();
    }

    /**
     * Tests whether the model uses unique names for its output table names.
     * That is when the system combines the model and run names with the output
     * table names, if this flag is set to true, then it will include the
     * current time_date to make the table name unique. If false, it will
     * exclude the data_time section and ovewrite exiting tables with the same
     * name. Having this flag set to false is preferred when initially debugging
     * a model, but during operational use of the model it should be set to
     * true.
     * 
     * @return True if unique table names are allowed, false otherwise.
     */
    public boolean isUniqueTableNames()
    {
        return model.getOutputConfigManager().isUniqueTableNames();
    }

    /**
     * @return the matrix object for the associated model
     */
    public Matrix getMatrix()
    {
        return matrix;
    }

    /**
     * @param edgeTypeToBehaviorPairs
     */
    public void setEdgeBehaviors(Map<String, Set<BehaviorPair>> edgeTypeToBehaviorPairs)
    {
        matrix.setEdgeBehaviors(edgeTypeToBehaviorPairs);        
    }
    
    public Set<BehaviorPair> getEdgeBehaviors(String edgeType) {
        return matrix.getEdgeBehaviors(edgeType);
    }
}
