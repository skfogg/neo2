/**
 * 
 */
package org.neosimulation.neo.framework.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.neosimulation.neo.framework.config.AppInstance;
import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.config.ModelConfig;
import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.config.NEOContextException;
import org.neosimulation.neo.framework.config.RunConfig;
import org.neosimulation.neo.framework.database.DatabaseManager;
import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.Matrix;
import org.neosimulation.neo.framework.model.NEOSimulationModel;
import org.neosimulation.neo.framework.model.Network;
import org.neosimulation.neo.framework.model.NetworkBuilder;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.SimulationModelInitializationException;
import org.neosimulation.neo.framework.output.Outputter;
import org.neosimulation.neo.framework.output.OutputterFactory;
import org.neosimulation.neo.framework.solver.Solver;
import org.neosimulation.neo.framework.solver.SolverFactory;

/**
 * ModelInitiator - Class used by the ModelManager to create and initialize
 * SimulationModel instances.
 * 
 * @author Isaac Griffith
 */
public class ModelInitiator implements Loggable {

    /**
     * Next SimulationModel internal unique id
     */
    private static long nextModelID;
    /**
     * Map of RunConfig names to RunConfig instances
     */
    private Map<String, RunConfig> runConfigs;
    /**
     * Associated ModelManger
     */
    private ModelManager manager;
    /**
     * DatabaseManager used to maintain database connections
     */
    private DatabaseManager dbManager;
    /**
     * NEOLogger associated with initiation of models to report errors and other
     * problems
     */
    private NEOLogger logger;

    /**
     * Constructs a new ModelIntiator and associates it with the provided
     * ModelManaer.
     * 
     * @param manager
     *            associated ModelManager for this ModelInitiator.
     */
    public ModelInitiator(ModelManager manager)
    {
        this.manager = manager;
        dbManager = new DatabaseManager();

        if (manager.isLoggingEnabled())
            enableLogging();
    }

    /**
     * Creates SimulationModel instances based on the provided NEOContext and
     * ModelConfig using the provided SQL queries (stored in the Properties
     * object) to extract information from the NEO Input tables.
     * 
     * @param queries
     *            Properties file representing the SQL queries used to extract
     *            necessary information from NEO Input tables
     * @param config
     *            ModelConfig providing necessary information used to begin
     *            building SimulationModels
     * @param context
     *            NEOContext associated with the provided ModelConfig
     */
    public void createModels(Properties queries, ModelConfig config, NEOContext context)
    {
        List<SimulationModel> toExecute = new ArrayList<>();
        try
        {
            initializeModels(queries, config, context);
        }
        catch (SimulationModelInitializationException smiex)
        {
            NEOLogger.logException(manager, "Could not initialize Model Runs.", smiex);
        }
    }

    /**
     * Initializes this SimulationModel from the information stored in the
     * Database, and creates the necessary LoopMangers to control the Model
     * 
     * @param queries
     *            Properties file representing the SQL queries used to extract
     *            necessary information from NEO Input tables
     * @param modelConfig
     *            ModelConfig providing necessary information used to begin
     *            building SimulationModels
     * @param context
     *            NEOContext associated with the provided ModelConfig
     * @throws SimulationModelInitializationException
     *             thrown if the no simulation model instance can be created
     */
    private final synchronized void initializeModels(Properties queries, ModelConfig modelConfig, NEOContext context)
            throws SimulationModelInitializationException
    {
        try
        {
            List<RunConfig> runConfigs = context.getModelRunConfigs(modelConfig.getPrefix());

            DbConfig outputConfig = modelConfig.getOutputDbConfig();
            OutputterFactory outFactory = modelConfig.getOutputterFactory();
            Outputter outputter = outFactory.createOutputter(outputConfig);
            modelConfig.setOutputter(outputter);

            for (RunConfig config : runConfigs)
            {
                SimulationModel model = null;
                try
                {
                    // instantiate model
                    model = new NEOSimulationModel(context, modelConfig, config, true);
                    model.getOutputConfigManager().setLocalOutput(modelConfig.useLocalOutput());
                    model.getOutputConfigManager().setUniqueTableNames(modelConfig.isUniqueTables());

                    SolverFactory factory = config.getSolverFactory();
                    Solver initSolver = factory.createInitSolver(model);
                    Solver execSolver = factory.createExecSolver(model);

                    model.setInitSolver(initSolver);
                    model.setExecSolver(execSolver);

                    NetworkBuilder netBuilder = new NetworkBuilder(queries, config.getInputDbConfig(), model);
                    Network network = netBuilder.generateNetwork();
                    Matrix matrix = netBuilder.getMatrix();

                    model.setTopology(matrix, network);

                    manager.addModelToQueue(model);

                    // instantiate apps
                    List<AppInstance> instances = config.getAppInstances();

                    for (AppInstance instance : instances)
                    {
                        boolean available = context.isAppAvailable(instance.getClassName());
                        if (available)
                        {
                            // Reflectively instantiate - Need a means to
                            // differentiate
                            // between instances
                            manager.addAppToQueue(model, instance);
                        }
                    }

                    outputter.registerModel(model);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    NEOLogger.logException(manager, "Simulation Model Could not be built", ex);
                    throw new SimulationModelInitializationException("Simulation Model could not be initialized.");
                }
            }
        }
        catch (NEOContextException e)
        {
            NEOLogger.logException(manager, e.getMessage(), e);
        }
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
        logger = new NEOLogger("Model Manager", true);
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
