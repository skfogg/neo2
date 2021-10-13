/**
 * 
 */
package org.neosimulation.neo.framework.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.neosimulation.neo.framework.config.ConfigLoader;
import org.neosimulation.neo.framework.config.ConfigLoaderError;
import org.neosimulation.neo.framework.config.ModelConfig;
import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.config.NEOContextException;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.user.NEOApp;

/**
 * NEOModelManager - A Singleton implementation of the ModelManager interface.
 * This class is meant to be the de facto standard implementation of a model
 * manager.
 * 
 * @author Isaac Griffith
 */
public class NEOModelManager extends ModelManager {

    /**
     * Singleton instance
     */
    private static ModelManager instance;

    /**
     * Constructs a new instance of NEOModelMangaer which includes the ability
     * log errors and exceptions.
     */
    private NEOModelManager()
    {
        super(true);

        apps = new HashMap<>();
        modelApps = new HashMap<>();
        models = new LinkedList<>();
        runIds = new HashMap<>();
        loader = new ConfigLoader(this);
        contexts = new LinkedList<>();
        initiator = new ModelInitiator(this);
        frameworkLocation = System.getenv("NEO_HOME");

        loader.loadFrameworkResources();
    }

    /**
     * Retrieves the single instance of ModelManager to be used.
     * 
     * @return the singleton instance of this model manager
     */
    public synchronized static ModelManager getInstance()
    {
        if (instance == null)
        {
            instance = new NEOModelManager();
        }

        return instance;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.ModelManager#processContext(org.neosimulation
     * .neo.framework.config.NEOContext)
     */
    @Override
    public void processContext(NEOContext context)
    {
        Set<String> modelPrefixes = context.getModelPrefixes();
        for (String prefix : modelPrefixes)
        {
            ModelConfig config;
            try
            {
                config = context.getModelConfig(prefix);
                initiator.createModels(queries, config, context);
            }
            catch (NEOContextException e)
            {
                NEOLogger.logException(this, "There was a problem creating model run instances.", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.ModelManager#process()
     */
    @Override
    public void process()
    {
        checkForValidContextsAndProcess();

        List<Long> problems = runModelsAndApps();

        handleExit(problems);
    }

    /**
     * Verifies that there exists at least one valid NEOContext object and if so
     * begins to process that context.
     */
    private void checkForValidContextsAndProcess()
    {
        if (!contexts.isEmpty())
        {
            int count = 0;
            for (NEOContext context : contexts)
            {
                count += context.getAllModelConfigs().size();
            }

            if (count < 1)
                throw new ConfigLoaderError("The Config Loader could not find any active Model Configs.");
        }
        while (!contexts.isEmpty())
        {
            processContext(contexts.poll());
        }
    }

    /**
     * Handles the process of exiting from the system. If the list of problems
     * contains counts > 0, then it exits with an error, otherwise it exits
     * normally.
     * 
     * @param problems
     */
    private void handleExit(List<Long> problems)
    {
        // Removing this functionality is not acceptable according to the
        // current design
        // for (long count : problems)
        // {
        // if (count > 0)
        // System.exit(1);
        // }

        clearAndCloseLoggers();

        // System.exit(0);
    }

    /**
     * Clears out and closes all open loggers.
     */
    private void clearAndCloseLoggers()
    {
        Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();

        while (loggerNames.hasMoreElements())
        {
            String name = loggerNames.nextElement();
            Logger log = LogManager.getLogManager().getLogger(name);

            if (log != null)
            {
                Handler handlers[] = log.getHandlers();
                for (Handler h : handlers)
                {
                    log.removeHandler(h);
                    h.flush();
                    h.close();
                }
            }
        }
    }

    /**
     * Begins the process of running SimulationModel objects and their
     * associated applications.
     * 
     * @return List containing the counts of each app/model's error/exception
     *         counts.
     */
    private List<Long> runModelsAndApps()
    {
        List<Long> problems = new ArrayList<>();

        while (!models.isEmpty())
        {
            SimulationModel model = models.poll();
            List<NEOApp> apps = modelApps.get(model.getName());

            if (apps != null && !apps.isEmpty())
            {
                for (NEOApp app : apps)
                {
                    app.execute(model);
                }
            }

            model.execute();
            if (model.isLoggingEnabled())
            {
                problems.add(model.getLogger().getNumberOfSevereProblemsLogged());
            }
        }

        return problems;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.ModelManager#loadModelResources(java.
     * io.File, org.neosimulation.neo.framework.config.NEOContext)
     */
    @Override
    public synchronized void loadModelResources(File modelBase, NEOContext context)
    {
        String modelLocation = modelBase.getPath();
        loader.loadModelResources(modelLocation, context);
    }
}
