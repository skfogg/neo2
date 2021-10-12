/**
 * 
 */
package org.neosimulation.neo.framework.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.ModelManager;
import org.neosimulation.neo.framework.manager.NEOModelManager;

/**
 * NEOContext - An object which encapsulates all the information necessary to
 * build model runs based on model configurations and external arguments passed
 * in by a NEORuntime object. This class acts as the main repository of
 * information for the Model Manager when dealing with Simulation Model
 * construction. NEOContext is also the object which is passed to the
 * ModelIntiator when generating new model runs.
 * 
 * @author Isaac Griffith
 */
public class NEOContext implements Loggable {

    /**
     * The Associated NEOLogger to log problems with this class
     */
    private NEOLogger logger;
    /**
     * The model manager associated to this context
     */
    private ModelManager manager;
    /**
     * Model specific contextual information storage.
     */
    private Map<String, String> contextInfo;
    /**
     * Model specific DbConfigs
     */
    private Map<String, HashMap<String, DbConfig>> modelDbConfigs;
    /**
     * Model specific RunConfigurations
     */
    private Map<String, HashMap<String, RunConfig>> modelRunConfigs;
    /**
     * Mapping of ModelConfigs to model prefixes
     */
    private Map<String, ModelConfig> modelConfigs;

    /**
     * Contructs a new empty instance of NEOContext
     */
    public NEOContext()
    {
        this(NEOModelManager.getInstance(), true);
    }

    /**
     * Constructs a new empty instance of NEOContext using the provided model
     * manager
     * 
     * @param manager
     *            The model manager this context is associated with
     * @param logging
     *            if true then the logger will be enabled, false it is not.
     */
    public NEOContext(ModelManager manager, boolean logging)
    {
        this.manager = manager;

        if (logging)
        {
            enableLogging();
        }
        else
        {
            disableLogging();
        }

        modelDbConfigs = new HashMap<>();
        modelRunConfigs = new HashMap<>();
        modelConfigs = new HashMap<>();
        contextInfo = new HashMap<>();
    }

    /**
     * Adds the provided db config to the NEOContext and associates it with the
     * provided model prefix.
     * 
     * @param modelPrefix
     *            the model prefix to associate the DbConfig with
     * @param dbConfig
     *            The DbConfig to be added, if it is null or not active then it
     *            will be disregarded.
     * @throws NEOContextException
     *             thrown if the provided model prefix is null, empty, or not a
     *             prefix of a current model config.
     */
    public void addModelDbConfig(String modelPrefix, DbConfig dbConfig) throws NEOContextException
    {
        if (isDbConfigInvalid(dbConfig))
        {
            return;
        }

        if (isModelPrefixInvalid(modelPrefix))
        {
            throw new NEOContextException("Provided model prefix for the dbconfig is invalid.");
        }

        String name = dbConfig.getName();

        HashMap<String, DbConfig> temp;
        if (modelDbConfigs.containsKey(modelPrefix))
        {
            temp = modelDbConfigs.get(modelPrefix);
        }
        else
        {
            temp = new HashMap<String, DbConfig>();
            modelDbConfigs.put(modelPrefix, temp);
        }

        temp.put(name, dbConfig);
    }

    /**
     * Adds a run config associated with the given model prefix to this
     * NEOContext.
     * 
     * @param modelPrefix
     *            model prefix to which the provided run config is associated
     *            with
     * @param runConfig
     *            run config to be added, if this RunConfig is null or not
     *            active, it will be disregarded.
     * @throws NEOContextException
     *             Thrown when an invalid modelPrefix is provided, such as when
     *             the prefix is empty, null, or does not exist in this context.
     */
    public void addModelRunConfig(String modelPrefix, RunConfig runConfig) throws NEOContextException
    {
        if (isRunConfigInvalid(runConfig))
        {
            return;
        }

        if (isModelPrefixInvalid(modelPrefix))
        {
            throw new NEOContextException("Provided model prefix for the runconfig is invalid.");
        }

        String name = runConfig.getModelPrefix() + '-' + runConfig.getName();

        HashMap<String, RunConfig> temp;
        if (modelRunConfigs.containsKey(modelPrefix))
        {
            temp = modelRunConfigs.get(modelPrefix);
        }
        else
        {
            temp = new HashMap<String, RunConfig>();
            modelRunConfigs.put(modelPrefix, temp);
        }

        temp.put(name, runConfig);
    }

    /**
     * Retrieves a map relating db config names to DbConfigs, associated with
     * the provided model prefix
     * 
     * @param modelPrefix
     *            model prefix
     * @return Map relating db config names to DbConfigs which are associated to
     *         the provided model prefix.
     */
    public HashMap<String, DbConfig> getModelDbConfigs(String modelPrefix)
    {
        HashMap<String, DbConfig> retVal = new HashMap<>();

        if (modelDbConfigs.containsKey(modelPrefix))
            retVal = modelDbConfigs.get(modelPrefix);

        return retVal;
    }

    /**
     * Returns all run configs associated with the given model prefix.
     * 
     * @param modelPrefix
     *            model prefix
     * @return List of run configs associated with the provided model prefix
     * @throws NEOContextException
     *             thrown if an invalid model prefix is provided, such as a null
     *             value or empty string.
     */
    public List<RunConfig> getModelRunConfigs(String modelPrefix) throws NEOContextException
    {
        List<RunConfig> retVal = new ArrayList<>();
        if (isModelPrefixInvalid(modelPrefix))
        {
            throw new NEOContextException("Provided model prefix is in valid.");
        }

        if (modelRunConfigs.containsKey(modelPrefix))
        {
            HashMap<String, RunConfig> temp = modelRunConfigs.get(modelPrefix);
            for (String key : temp.keySet())
                retVal.add(temp.get(key));
        }

        return retVal;
    }

    /**
     * Tests whether an app with the given name exists
     * 
     * @param name
     *            name of the app in question
     * @return true if an app with the given name exists, false otherwise.
     */
    public boolean isAppAvailable(String name)
    {
        // TODO Implement this method
        return false;
    }

    /**
     * Returns the ModelConfig associated with the provided model prefix.
     * 
     * @param modelPrefix
     *            model prefix attached to the model config requested
     * @return the model config associated with the provided model prefix, or
     *         null if there is no model config with the provided prefix
     * @throws NEOContextException
     *             thrown if an invalid value, a null or empty string, is
     *             provided as the model prefix.
     */
    public ModelConfig getModelConfig(String modelPrefix) throws NEOContextException
    {
        if (isModelPrefixInvalid(modelPrefix))
        {
            throw new NEOContextException("Invalid model prefix specified.");
        }

        ModelConfig retVal = null;
        if (modelConfigs.containsKey(modelPrefix))
        {
            retVal = modelConfigs.get(modelPrefix);
        }

        return retVal;
    }

    /**
     * Tests whether the provided model prefix value is invalid or not
     * 
     * @param modelPrefix
     *            Model prefix to test
     * @return true if the prefix is not valid, false otherwise.
     */
    private boolean isModelPrefixInvalid(String modelPrefix)
    {
        return (modelPrefix == null || modelPrefix.isEmpty() || !modelConfigs.containsKey(modelPrefix));
    }

    /**
     * Tests whether the provided RunConfig object is invalid or not
     * 
     * @param runConfig
     *            RunConfig object under test
     * @return true if the runconfig is not valid, false otherwise.
     */
    private boolean isRunConfigInvalid(RunConfig runConfig)
    {
        return (runConfig == null || !runConfig.isActive());
    }

    /**
     * Tests whether the provided DbConfig object is invalid or not
     * 
     * @param dbConfig
     *            DbConfig object to test.
     * @return true if the object is invalid, false otherwise
     */
    private boolean isDbConfigInvalid(DbConfig dbConfig)
    {
        return (dbConfig == null || !dbConfig.isActive());
    }

    /**
     * Adds the provided ModelConfig to this NEOContext
     * 
     * @param config
     *            model config to add. If this model config is not active or is
     *            null it is disregarded.
     */
    public void addModelConfig(ModelConfig config)
    {
        if (config == null || !config.isActive())
            return;

        modelConfigs.put(config.getPrefix(), config);
    }

    /**
     * Retrieves the ModelManager to which this NEOContext is associated
     * 
     * @return the model manager this NEOContext is associated to
     */
    public ModelManager getManager()
    {
        return manager;
    }

    /**
     * Retrieves a set of strings representing the known model prefixes of
     * models to be associated with this NEOContext.
     * 
     * @return set of all known model prefixes associated with this NEOContext
     */
    public Set<String> getModelPrefixes()
    {
        return modelConfigs.keySet();
    }

    /**
     * Retrieves a list of all ModelConfigs contained within this NEOContext
     * 
     * @return a list of all known model config objects associated with this
     *         NEOContext
     */
    public List<ModelConfig> getAllModelConfigs()
    {
        List<ModelConfig> configs = new ArrayList<>();

        for (String prefix : modelConfigs.keySet())
        {
            configs.add(modelConfigs.get(prefix));
        }

        return configs;
    }

    /**
     * Sets the entire set of context variables and values to the provided map
     * 
     * @param context
     *            new context variables and values map, if null this sets the
     *            contextInfo to an empty Map
     */
    public void setContextInfo(Map<String, String> context)
    {
        if (context == null)
        {
            contextInfo = new HashMap<>();
        }
        else
        {
            contextInfo = context;
        }
    }

    /**
     * Retrieves the value of the context variable named by the provided key
     * 
     * @param key
     *            name of the context variable
     * @return value of the context variable provided, or null if no such
     *         variable with that name exists
     */
    public String getContextInfo(String key)
    {
        return contextInfo.get(key);
    }

    /**
     * Sets the context information variable named with the specified key to the
     * value specified in value.
     * 
     * @param key
     *            name of the context variable to set
     * @param value
     *            new value of the variable
     */
    public void setContextInfo(String key, String value)
    {
        if (key == null || key.isEmpty() || value == null || value.isEmpty())
            return;

        contextInfo.put(key, value);
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

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#enableLogging()
     */
    @Override
    public void enableLogging()
    {
        logger = new NEOLogger("NEOContext", true);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#disableLogging()
     */
    @Override
    public void disableLogging()
    {
        logger = null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#isLoggingEnabled()
     */
    @Override
    public boolean isLoggingEnabled()
    {
        return (logger != null);
    }
}
