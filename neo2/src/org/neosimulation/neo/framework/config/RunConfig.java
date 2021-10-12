/**
 * 
 */
package org.neosimulation.neo.framework.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.neosimulation.neo.framework.solver.BasicSolverFactory;
import org.neosimulation.neo.framework.solver.SolverFactory;

/**
 * RunConfig - Class which encapsulates the necessary information to instantiate
 * and begin the process to build and run a SimulationModel.
 * 
 * @author Isaac Griffith
 */
public class RunConfig {

    /**
     * SolverType - An enumeration providing choices for solver types to be used
     * to describe how a simulation model is ran.
     * 
     * @author Isaac Griffith
     */
    enum SolverType {

        /**
         * Installs a basic solver, which executes the model within a single
         * thread.
         */
        basic,
        /**
         * Installs a parallel solver, which executes the model concurrently.
         */
        parallel,
    };

    /**
     * RunType - An enumeration providing choices for run types to be used to
     * describe how to run simulation models built using RunConfig.
     * 
     * @author Isaac Griffith
     */
    enum RunType {

        /**
         * Runs one simulation after another, with the potential ability to pipe
         * the output of one model as the input to the next
         */
        batch,
        /**
         * Runs each simulation model simultaneously with the remaining ones.
         */
        sequential,
        /**
         * Runs a single model then the next (if there are multiple) with piping
         * of input or output
         */
        single,
    }

    /**
     * the associated ModelConfig's model prefix
     */
    private final String modelPrefix;
    /**
     * Sovler factory used to generate solvers for SimulationModels built using
     * this run config
     */
    private SolverFactory factory;
    /**
     * the map relating dbConfig name to DbConfigs used by this run config.
     */
    private Map<String, DbConfig> dbConfigs;
    /**
     * base model name for models built from this run config
     */
    private final String modelName;
    /**
     * the input db config name
     */
    private String inputDbConfigName = "";
    /**
     * List of app instances to be used with models built with this run config
     */
    private List<AppInstance> apps;
    /**
     * the run type
     */
    private RunType runType;
    /**
     * the solver type
     */
    private SolverType solverType;
    /**
     * the name of this run config
     */
    private String name;
    /**
     * Flag to determine whether to use this configuration or not
     */
    private boolean active;

    /**
     * Creates a new RunConfig associated with the ModelConfig with the given
     * model prefix and with the provided run name.
     * 
     * @param modelPrefix
     *            The model prefix
     * @param runName
     *            The run name
     * @throws RunConfigException
     *             Thrown if the provided modelPrefix or runName are either null
     *             or empty strings
     */
    public RunConfig(String modelPrefix, String runName) throws RunConfigException
    {
        validateString(modelPrefix,
                "Invalid model prefix specified: %s, thus we cannot guarantee uniqueness of the model name.");
        validateString(runName,
                "Invalid run name specified: %s, thus we cannot guarantee uniqueness of the model name.");

        this.modelPrefix = modelPrefix;
        this.name = runName;
        this.modelName = getModelName(modelPrefix, runName);
        this.dbConfigs = new HashMap<>();
        this.active = true;
        this.apps = new LinkedList<AppInstance>();
    }

    /**
     * Validates the provided string value, and if not valid utilizes the
     * provided exception formatting string to report the problem.
     * 
     * @param value
     *            value to be validated
     * @param exceptionFormat
     *            exception formatting string
     * @throws RunConfigException
     *             Thrown if the provided string value is not valid.
     */
    private void validateString(String value, String exceptionFormat) throws RunConfigException
    {
        if (value == null || value.isEmpty())
        {
            throw new RunConfigException(String.format(exceptionFormat, value));
        }
    }

    /**
     * Returns the complete model name used by this RunConfig, where the model
     * name is: <model prefix>-<run name>-hhmm_DDMMYYYY
     * 
     * @param modelPrefix
     *            The model prefix
     * @param runName
     *            The run name
     * @return the complete unique model name
     */
    private String getModelName(String modelPrefix, String runName)
    {
        String retVal = String.format("%s-%s", modelPrefix, runName);

        return retVal;
    }

    /**
     * Retrieves the prefix of the model config to which this RunConfig is
     * associated.
     * 
     * @return the modelPrefix of the ModelConfig to which this RunConfig is
     *         associated
     */
    public String getModelPrefix()
    {
        return modelPrefix;
    }

    /**
     * Retrieves a mapping of all associated DbConfigs, which are indexed by
     * their names.
     * 
     * @return the map relating DbConfig names to DbConfigs used by this
     *         RunConfig
     */
    public Map<String, DbConfig> getDbConfigs()
    {
        return dbConfigs;
    }

    /**
     * Retrieves the name to be associated with the model created from this
     * RunConfig
     * 
     * @return the model name
     */
    public String getModelName()
    {
        return modelName;
    }

    /**
     * Sets the available DbConfigs to be used by this RunConfig, note that a
     * null value will clear the existing list.
     * 
     * @param dbConfigs
     *            the map of DbConfig names and DbConfigs used by
     *            SimulationModels associated with this RunConfig
     */
    public void setDbConfigs(Map<String, DbConfig> dbConfigs)
    {
        if (dbConfigs == null)
        {
            this.dbConfigs.clear();
        }
        else
        {
            this.dbConfigs = dbConfigs;
        }
    }

    /**
     * Sets the name of the Input DbConfig associated with this RunConfig
     * 
     * @param name
     *            name of the input DbConfig to use to build SimulationModels
     *            with
     */
    public void setInputDbConfigName(String name)
    {
        inputDbConfigName = name;
    }

    /**
     * Retrieves the name of the Input DbConfig.
     * 
     * @return the input DbConfig name
     */
    public String getInputDbConfigName()
    {
        return inputDbConfigName;
    }

    /**
     * Retrieves the DbConfig object that is used to extract the input
     * information from the database that will be used to build the
     * SimulationModel
     * 
     * @return the DbConfig to use to build SimulationModels using this
     *         RunConfig, or null if no input dbconfig name has been set or no
     *         such dbconfig with that name exists.
     */
    public DbConfig getInputDbConfig()
    {
        return dbConfigs.get(inputDbConfigName);
    }

    /**
     * Sets the app instances to be associated with this RunConfig to the list
     * provided
     * 
     * @param apps
     *            List of AppInstances to use
     */
    public void setAppInstances(List<AppInstance> apps)
    {
        this.apps = apps;
    }

    /**
     * Returns the list of app instances to be associated with a SimulationModel
     * created with this RunConfig
     * 
     * @return List of AppInstances associated with this RunConfig
     */
    public List<AppInstance> getAppInstances()
    {
        return apps;
    }

    /**
     * Sets the solver type associated with this RunConfig
     * 
     * @param solver
     *            string representation used to set the SolverFactory for this
     *            RunConfig
     * @throws RunConfigException
     *             Thrown when the provided solver type name does not match an
     *             existing type in the SolverType enumeration, or if it is
     *             null.
     */
    public void setSolverType(String solver) throws RunConfigException
    {
        if (solver == null)
        {
            throw new RunConfigException("Invalid value, " + solver + ", for solver-type in run config: " + this.name
                    + " in model config for model: " + this.modelPrefix
                    + ". Only a value specified in the SolverType enumeration is currently accepted.");
        }
        try
        {
            solverType = SolverType.valueOf(solver);
            switch (solverType)
            {
            case basic:
                factory = BasicSolverFactory.getInstance();
                break;
            case parallel:
                break;
            }
        }
        catch (IllegalArgumentException e)
        {
            throw new RunConfigException("Invalid value, " + solver + ", for solver-type in run config: " + this.name
                    + " in model config for model: " + this.modelPrefix
                    + ". Only a value specified in the SolverType enumeration is currently accepted.", e);
        }
    }

    /**
     * Retrieves the SolverFactory to be used when generating a SimulationModel
     * with this RunConfig
     * 
     * @return The SolverFactory used to generate solvers for simulation models
     *         using this run configuration
     */
    public SolverFactory getSolverFactory()
    {
        return factory;
    }

    /**
     * Sets the run type associated with this run configuration
     * 
     * @param run
     *            string representing the run type
     * @throws RunConfigException
     *             thrown if the provided run type name does not match the name
     *             of a member of the RunType enumeration, or if the provided
     *             run type name is null.
     */
    public void setRunType(String run) throws RunConfigException
    {
        if (run == null)
        {
            throw new RunConfigException("Invalid value, " + run + ", for run-type in run config: " + this.name
                    + " in model config for model: " + this.modelPrefix
                    + ". Only a value specified in the RunType enumeration is currently accepted.");
        }
        try
        {
            runType = RunType.valueOf(run);
            // TODO Implement this logic
            switch (runType)
            {
            case batch:
            case sequential:
            case single:
            }
        }
        catch (IllegalArgumentException e)
        {
            throw new RunConfigException("Invalid value, " + run + ", for run-type in run config: " + this.name
                    + " in model config for model: " + this.modelPrefix
                    + ". Only a value specified in the RunType enumeration is currently accepted.", e);
        }
    }

    /**
     * Sets the name of this provided string. Note that names should be unique
     * to the associated model prefix
     * 
     * @param runName
     *            new name of this run config
     */
    public void setName(String runName)
    {
        name = runName;
    }

    /**
     * Retrieves the name of this RunConfig
     * 
     * @return name of this run configuration
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the flag indicating whether this run config is enabled
     * 
     * @param isActive
     *            true to enable this config, false to disable.
     */
    public void setActive(boolean isActive)
    {
        this.active = isActive;
    }

    /**
     * Tests whether this RunConfig represents an active
     * 
     * @return true if this run config is enabled
     */
    public boolean isActive()
    {
        return active;
    }
}
