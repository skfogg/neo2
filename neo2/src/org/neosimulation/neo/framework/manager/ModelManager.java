/**
 * 
 */
package org.neosimulation.neo.framework.manager;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import org.neosimulation.neo.framework.config.AppInstance;
import org.neosimulation.neo.framework.config.ConfigLoader;
import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.database.ConfigKeys;
import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.user.NEOApp;

/**
 * ModelManager - An interface defining the contract by which all model managers
 * must commit in order to be used by the rest of the system.
 * 
 * @author Isaac Griffith
 */
public abstract class ModelManager implements Loggable {

    /**
     * Map of List of NEOApps to executed as indexed by SimulationModel names
     */
    protected Map<String, List<NEOApp>> modelApps;
    /**
     * Map of available NEOApps indexed by their NEOApp names
     */
    protected Map<String, NEOApp> apps;
    /**
     * Queue of SimulationModels to be intialized and executed
     */
    protected Queue<SimulationModel> models;
    /**
     * ConfigKeys object used to validate NEO Input database control tables
     */
    protected ConfigKeys keys;
    /**
     * The NEOLogger used to log ModelManager related problems.
     */
    protected NEOLogger logger;
    /**
     * map of run ids indexed by model names
     */
    protected Map<String, Long> runIds;
    /**
     * The initiator used by this ModelManager to create SimulationModels
     */
    protected ModelInitiator initiator;
    /**
     * location of the NEO Framework installation
     */
    protected String frameworkLocation;
    /**
     * The SQL query string properties object
     */
    protected Properties queries;
    /**
     * Queue of NEOContext instances to be processed
     */
    protected Queue<NEOContext> contexts;
    /**
     * ConfigLoader used to load configuration information and app information
     */
    protected ConfigLoader loader;

    /**
     * ModelManager super constructor, which solely deals with the enabling or
     * disabling of logging based on the value of the boolean parameter. Since
     * this class is an abstract super type, it assumes that the business of
     * properly instantiating the associated fields correctly to subtypes of
     * this class. This was purposefully done to allow developers to choose
     * their own types implementation types (as well as to help deal with issues
     * of selecting proper collections which are thread safe) as well as leaving
     * the setting of the framework location to the implementation classes.
     * 
     * @param enableLogging
     *            boolean flag which enables or disables logging based on its
     *            value. True enables logging.
     */
    protected ModelManager(boolean enableLogging)
    {
        if (enableLogging)
        {
            enableLogging();
        }
    }

    /**
     * Returns the next unique id for a model with the provided model name.
     * 
     * @param modelName
     *            base name of the model for which an id is required
     * @return next unique model id associated with the provided model name.
     * @throws ModelManagerException
     *             thown in the provided model name is either null or empty
     */
    public synchronized long getNextRunId(String modelName) throws ModelManagerException
    {
        if (modelName == null || modelName.isEmpty())
        {
            throw new ModelManagerException("Invalid name of model");
        }
        long retVal = 0;
        if (runIds.containsKey(modelName))
        {
            retVal = runIds.get(modelName);
            runIds.put(modelName, retVal + 1);
        }
        else
        {
            runIds.put(modelName, retVal + 1);
        }

        return retVal;
    }

    /**
     * Adds the provided SimulationModel on the queue of models to be executed.
     * 
     * @param model
     *            SimulationModel to execute.
     */
    public synchronized void addModelToQueue(SimulationModel model)
    {
        models.offer(model);
    }

    /**
     * Adds and AppInstance object associated with the provided SimulationModel
     * instance.
     * 
     * @param model
     *            Associated SimulationModel
     * @param instance
     *            AppInstance associated with the provded SimulationModel
     */
    public synchronized void addAppToQueue(SimulationModel model, AppInstance instance)
    {
        // TODO Implement this method
    }

    /**
     * Loads model resources from the provided model base and loads them into
     * the provided NEOContext instance.
     * 
     * @param modelBase
     *            File object describing the model base.
     * @param context
     *            NEOContext instance into which the resources are loaded.
     */
    public abstract void loadModelResources(File modelBase, NEOContext context);

    /**
     * Sets the ConfigKeys instance describing the necessary keys used to
     * validate the NEO Input database control table
     * 
     * @param keys
     *            ConfigKeys instance to be used.
     */
    public synchronized void setConfigKeys(ConfigKeys keys)
    {
        this.keys = keys;
    }

    /**
     * Sets the properties object used to contain named SQL query strings.
     * 
     * @param queries
     *            the properties file
     */
    public synchronized void setQueries(Properties queries)
    {
        this.queries = queries;
    }

    /**
     * Returns the SQL query string associated with the provided query name.
     * 
     * @param name
     *            Name of the query to be retrieved.
     * @return SQL query string used to extract information from the NEO Input
     *         tables, that is associatd with the provided name. If no SQL query
     *         string is associated with the provided name, then null is
     *         returned.
     * @throws ModelManagerException
     */
    public synchronized String getQuery(String name) throws ModelManagerException
    {
        if (name == null || name.isEmpty() || !queries.containsKey(name))
        {
            throw new ModelManagerException("Query key unknown.");
        }
        else
        {
            return queries.getProperty(name);
        }
    }

    /**
     * Retrieves the location where the NEO Framework has been installed.
     * 
     * @return The location of the NEO Framework installation.
     */
    public synchronized String getFrameworkLocation()
    {
        return frameworkLocation;
    }

    /**
     * Processes the provided context and generates models to be executed.
     * 
     * @param context
     *            NEOContext instance to be processed
     */
    public abstract void processContext(NEOContext context);

    /**
     * Adds a NEOContext to be processed by this model manager
     * 
     * @param context
     *            the provided context to be added
     */
    public synchronized void addContext(NEOContext context)
    {
        contexts.offer(context);
    }

    /**
     * Starts the process of creating, initializing, and executing all created
     * SimulationModels and associated NEO Apps. <br>
     * <br>
     * TODO: Rework this to be multithreaded. This eventually will need to be a
     * main thread, while the two while loops are worker threads for this thread
     * constantly processing incoming requests. But this will have to wait until
     * we have a more service oriented architecture
     */
    public abstract void process();

    /**
     * Retrieves the ConfigKeys object which contains the set of potential valid
     * keys for a NEO Model Control Table using this version of NEO.
     * 
     * @return The ConfigKeys used to validate the NEO Input database control
     *         tables
     */
    public synchronized ConfigKeys getConfigKeys()
    {
        return keys;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#getLogger()
     */
    @Override
    public synchronized NEOLogger getLogger()
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
        logger = new NEOLogger("NEOModelManager", true);
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
