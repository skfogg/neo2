/**
 * 
 */
package org.neosimulation.neo.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.neosimulation.neo.framework.behcurr.CurrencyManager;
import org.neosimulation.neo.framework.config.ModelConfig;
import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.config.RunConfig;
import org.neosimulation.neo.framework.database.DbMediator;
import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.OutputConfigManager;
import org.neosimulation.neo.framework.mtis.UpdatableConfigManager;
import org.neosimulation.neo.framework.output.IOutputListener;
import org.neosimulation.neo.framework.output.IOutputProvider;
import org.neosimulation.neo.framework.solver.Solver;
import org.neosimulation.neo.framework.solver.StoppingCondition;
import org.neosimulation.neo.framework.time.TimeKeeper;

/**
 * @author Isaac
 */
public abstract class SimulationModel implements Loggable, IOutputProvider, Callable<SimulationModel>, Serializable {

    /**
     * The next UID for a holon associated with this SimulationModel
     */
    protected long nextHolonID;
    /**
     * The next UID for a stateval associated with this SimulationModel
     */
    protected long nextStateValID;
    /**
     * The runtime logger for this SimulationModel
     */
    protected NEOLogger logger;
    /**
     * The NEOContext that created this Model
     */
    protected final NEOContext context;
    /**
     * The ModelConfig which created this SimulationModel
     */
    protected final ModelConfig config;
    /**
     * The RunConfig which helped to create this SimulationModel
     */
    protected final RunConfig runConfig;
    /**
     * The CurrencyManager containing the necessary information contained within
     * the database about currently installed resource packages
     */
    protected CurrencyManager currManager;
    /**
     * List of OuputListeners attached to this Model
     */
    protected List<IOutputListener> outputListeners;
    /**
     * The complete model name in the following format: <model prefix>-<run
     * name>-<current date>. Where the current date is in the following format
     * hhmm_MMDDYYYY. That is the time specified in 24-hour format and the date.
     */
    protected String name;
    /**
     * The TimeKeeper StateVal which keeps track of this SimulationModel's Model
     * Time
     */
    protected TimeKeeper timeKeeper;
    /**
     * The Loop Manager in charge of the Main Execution Loop
     */
    protected Solver execSolver;
    /**
     * The Loop Manager in charge of the Initialization Loop
     */
    protected Solver initSolver;
    /**
     * The object charged with maintaining all information about the structure
     * of the Holons and their StateVals
     */
    protected Matrix matrix;
    /**
     * The object charged with maintaining the information regarding IUpdatables
     * and their relation to the objects in the Matrix
     */
    protected Network network;
    /**
     * Object in charge of maintaining all information originally stored in the
     * Database tables used to initially create this SimulationModel
     */
    protected DbMediator mediator;
    /**
     * The unique identifier of this SimulationModel
     */
    protected long uniqueID;
    /**
     * Flag representing whether or not this SimulationModel is in the
     * initialization phase
     */
    protected boolean isInitializing = true;
    /**
     * The OutputConfigManager which contains and provides access to this
     * SimulationModel's OutputConfigs
     */
    protected OutputConfigManager outManager;
    /**
     * The DependencyManager that maintains the dependencies between IUpdatables
     * utilized by this model
     */
    protected DependencyManager depManager;
    /**
     * The UpdatableConfigManager which maintains the different views of the
     * grand loop for this model
     */
    protected UpdatableConfigManager updatableManager;

    /**
     * Constructs a new Empty SimulationModel with a null NEOContext reference,
     * a null ModelConfig reference, and a null RunConfig reference.
     */
    public SimulationModel()
    {
        this(null, null, null);
    }

    /**
     * Constructs a new SimulationModel connected to the provided NEOContext,
     * ModelConfig and RunConfig.
     * 
     * @param context
     *            The NEOContext
     * @param config
     *            The ModelConfig
     * @param runConfig
     *            The RunConfig
     */
    public SimulationModel(NEOContext context, ModelConfig config, RunConfig runConfig)
    {
        this.context = context;
        this.config = config;
        this.runConfig = runConfig;

        this.currManager = new CurrencyManager();
        outputListeners = new ArrayList<IOutputListener>();
        depManager = new DependencyManager(this);
        updatableManager = new UpdatableConfigManager(this);
        outManager = new OutputConfigManager(this);
    }

    /**
     * Executes the initialization loop manager and then the runLoopManger.
     */
    public abstract void execute();

    /**
     * Provides access to the underlying Network object of this SimulationModel
     * 
     * @return This SimulationModel's Network object
     */
    public final synchronized Network getNetwork()
    {
        return network;
    }

    /**
     * Provides access to all known OutputListeners attached to this
     * SimulationModel
     * 
     * @return List of attached IOutputListeners
     */
    public synchronized List<IOutputListener> getOutputListeners()
    {
        return outputListeners;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputProvider#addOutputListener
     * (org.neosimulation.neo.framework.output.IOutputListener)
     */
    @Override
    public synchronized void addOutputListener(IOutputListener listener)
    {
        outputListeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputProvider#removeOutputListener
     * (org.neosimulation.neo.framework.output.IOutputListener)
     */
    @Override
    public synchronized void removeOutputListener(IOutputListener listener)
    {
        outputListeners.remove(listener);
    }

    /**
     * Provides access to this SimulationModel's TimeKeeper
     * 
     * @return the TimeKeeper
     */
    public final synchronized TimeKeeper getTimeKeeper()
    {
        return timeKeeper;
    }

    /**
     * Command to the TimeKeeper stored in this model to retrieve the Tick
     * StateVal's value
     * 
     * @return Current value of the Tick StateVal of this model's TimeKeeper
     */
    public synchronized long getTick()
    {
        return timeKeeper.getTick();
    }

    /**
     * Command to the TimeKeeper stored in this model to retrieve the Current
     * Time StateVal's value
     * 
     * @return Current time of the Time StateVal of this model's TimeKeeper
     */
    public synchronized double getCurrentTime()
    {
        return timeKeeper.getCurrentTime();
    }

    /**
     * Adds the provided stopping condition to the Main Execution Solver, in
     * order to ensure that it will stop when required.
     * 
     * @param stop
     *            Stopping condition to be added.
     */
    public synchronized void addStoppingCondition(StoppingCondition stop)
    {
        this.execSolver.addStoppingCondition(stop);
    }

    /**
     * Retrieves the CurrencyManager object responsible for maintaining the
     * information about currencies used within this SimulationModel
     * 
     * @return The CurrencyManager for the Resources used in this
     *         SimulationModel
     */
    public final CurrencyManager getCurrencyManager()
    {
        return currManager;
    }

    /**
     * Retrieves the current TimeStep of this SimulationModel as stored within
     * the TimeKeeper associated with this SimulationModel
     * 
     * @return the current TimeStep for the is SimulationModel
     */
    public synchronized double getTimeStep()
    {
        return timeKeeper.getTimeStep();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#getLogger()
     */
    @Override
    public NEOLogger getLogger()
    {
        return logger;
    }

    /**
     * Sets the initialization solver for this SimulationModel to the provided
     * Solver.
     * 
     * @param initSolver
     *            The new Initialization Solver
     */
    public final void setInitSolver(Solver initSolver)
    {
        this.initSolver = initSolver;
    }

    /**
     * Sets the execution solver for this SimulationModel to the provided Solver
     * 
     * @param execSolver
     *            The new Execution Solver
     */
    public final void setExecSolver(Solver execSolver)
    {
        this.execSolver = execSolver;
    }

    /**
     * Sets the Currency Manager for SimulationModel to the one provided.
     * 
     * @param currManager
     *            The new Currency Manager
     */
    public final void setCurrencyManager(CurrencyManager currManager)
    {
        this.currManager = currManager;
    }

    /**
     * Sets the TimeKeeper of this SimulationModel to the provided one.
     * 
     * @param timeKeeper
     *            The new TimeKeeper
     */
    public final void setTimeKeeper(TimeKeeper timeKeeper)
    {
        this.timeKeeper = timeKeeper;
    }

    /**
     * Retrieves the Solver object responsible for operating this
     * SimulationModel
     * 
     * @return The SimulationModel's Execution Solver
     */
    public Solver getExecSolver()
    {
        return execSolver;
    }

    /**
     * Retrieves the Solver object responsible for initializing this
     * SimulationModel
     * 
     * @return The SimulationModel's Initialization Solver
     */
    public Solver getInitSolver()
    {
        return initSolver;
    }

    /**
     * Retrieves the OutputConfigManager object responsible for maintaining
     * output configuration information for this SimulationModel
     * 
     * @return The SimulationModel's current OutputConfigManager
     */
    public synchronized OutputConfigManager getOutputConfigManager()
    {
        return outManager;
    }

    /**
     * Retrieves the Matrix Object responsible for storing the Holon Topology of
     * this SimulationModel.
     * 
     * @return The Matrix describing the topology of the SimulationModel
     */
    public synchronized final Matrix getMatrix()
    {
        return matrix;
    }

    /**
     * Retrieves the output table names associated with the provided stateval
     * updater for the current tick of the timekeeper.
     * 
     * @param stateValUpdater
     *            StateVal Updater whose output is ready.
     * @return Names of the Output Tables to store the provided stateval
     *         updater's output to.
     */
    public synchronized Set<String> getOutputTableNames(IUpdatable stateValUpdater)
    {
        return outManager.getTableNames(timeKeeper.getTick(), stateValUpdater);
    }

    /**
     * Returns the next holon id for a holon to be attached to this model.
     * 
     * @return next holon id number in sequence.
     */
    public final synchronized long getNextHolonID()
    {
        long retVal = -1L;

        retVal = nextHolonID;
        nextHolonID++;

        return retVal;
    }

    /**
     * the next unique stateval id for this model.
     * 
     * @return next StateVal id number in sequence.
     */
    public final synchronized long getNextStateValID()
    {
        long retVal = -1L;

        retVal = nextStateValID;
        nextStateValID++;

        return retVal;
    }

    /**
     * Initializes the Time Keeper to the proper values.
     * 
     * @param timeStep
     *            The interval for the TimeKeeper's time
     * @param timeSeed
     *            The initial value for the TimeKeeper's time value
     * @param tickInterval
     *            The interval for the TimeKeeper's tick
     * @param tickSeed
     *            The initial value for the TimeKeeper's tick value
     */
    public synchronized final void initializeTimeKeeper(double timeStep, double timeSeed, long tickInterval,
            long tickSeed)
    {
        timeKeeper = new TimeKeeper(timeStep, timeSeed, tickInterval, tickSeed, this);
    }

    /**
     * Tests whether the provided IUpdatable should output to the database.
     * 
     * @param updatable
     *            The IUpdatable that is a candidate for output.
     * @return true if the IUpdatable should be considered for output, false
     *         otherwise.
     */
    public synchronized boolean shouldOutput(IUpdatable updatable)
    {
        long tick = timeKeeper.getTick();
        return outManager.shouldOutput(tick, updatable);
    }

    /**
     * Sets the Topology variable of this SimulationModel once they have been
     * built. The provided Matrix (underlying Holon topology) and Network
     * (underlying StateVal/Dynam/IUpdatable) topology) are provided by the
     * NetworkBuilder.
     * 
     * @param matrix
     *            The Holon topology of the SimulationModel
     * @param network
     *            The StateVal, Dynam, and IUpdatable topology of the
     *            SimulationModel
     */
    public synchronized final void setTopology(Matrix matrix, Network network)
    {
        this.matrix = matrix;
        this.network = network;
    }

    /**
     * Retrieves the complete and unique name of this specific Simulation Model
     * run.
     * 
     * @return The complete name of this SimulationModel run. That is
     *         <model-prefix>-<run-name>-<current date/time>
     */
    public synchronized final String getName()
    {
        return name;
    }

    /**
     * Sets the name of this model to the provided string.
     * 
     * @param name
     *            The name should be in the following format:
     *            <model-prefix>-<run-name>-<current date/time hhmm_MMDDYYYY>
     */
    public synchronized final void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retrieves the NEOContext object which stored application specific
     * information used both to build this SimulationModel and during runtime of
     * the SimulationModel.
     * 
     * @return This SimulationModel's associated NEOContext object.
     */
    public synchronized final NEOContext getContext()
    {
        return context;
    }

    /**
     * Retrieves the associated ModelConfig object which stored the simulation
     * model specific information used to build this SimulationModel
     * 
     * @return This SimulationModel's associated ModelConfig object.
     */
    public synchronized final ModelConfig getModelConfig()
    {
        return config;
    }

    /**
     * Retrieves the associated RunConfig object which stored the simulation
     * model run specific information used to build this SimulationModel
     * 
     * @return This SimulationModel's associated RunConfig object
     */
    public synchronized final RunConfig getRunConfig()
    {
        return runConfig;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#enableLogging()
     */
    @Override
    public synchronized void enableLogging()
    {
        logger = new NEOLogger(runConfig.getModelName(), true);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#disableLogging()
     */
    @Override
    public synchronized void disableLogging()
    {
        logger = null;
    }

    /**
     * Retrieves a list of all known stopping conditions associated with this
     * SimulationModel
     * 
     * @return List of current stopping conditions
     */
    public synchronized List<StoppingCondition> getStoppingConditions()
    {
        return this.execSolver.getStoppingConditions();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#isLoggingEnabled()
     */
    @Override
    public synchronized boolean isLoggingEnabled()
    {
        return (logger != null);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.IOutputProvider#getId()
     */
    @Override
    public synchronized long getId()
    {
        return uniqueID;
    }

    /**
     * Sets this SimulationModel's unique ID to the specified id.
     * 
     * @param id
     *            new Unique ID of the simulation model.
     */
    protected final synchronized void setID(long id)
    {
        uniqueID = id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public synchronized String toString()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputProvider#getOutputTableNames
     * ()
     */
    @Override
    public Set<String> getOutputTableNames()
    {
        Set<String> tableNames = outManager.getOutputTableNames();
        tableNames.add(outManager.getOutputInitialValuesTable());

        return tableNames;
    }

    /**
     * Tests whether this model is currently in the inialization phase or not.
     * @return True if currently initializing, false otherwise
     */
    public boolean isInitializing()
    {
        return isInitializing;
    }

    /**
     * @return this model's dependency manager
     */
    public DependencyManager getDependencyManager()
    {
        return depManager;
    }

    /**
     * @return this model's updatable configuration manager
     */
    public UpdatableConfigManager getUpdatableConfigManager()
    {
        return updatableManager;
    }
}