/**
 * 
 */
package org.neosimulation.neo.framework.model;

import java.util.Calendar;

import org.neosimulation.neo.framework.config.ModelConfig;
import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.config.RunConfig;
import org.neosimulation.neo.framework.solver.StoppingCondition;
import org.neosimulation.neo.framework.solver.StoppingCondition.ConditionType;

/**
 * NEOSimulationModel - Represents the model under execution. The NEOSimulationModel has it's hands in almost all parts of the framework. In order to ensure
 * that Package Developers have access to the simulation model all StateVals and all Holons have reference to the simulation model that they are currently
 * executing within. The NEOSimulationModel maintains the only references to the generated Matrix and Network objects which provide the underlying hierarchy of
 * Holons which comprise the Model. The NEOSimulationModel also contains the only reference to the DatabaseManager which maintains all the information that was
 * contained within the Initialization Tables in the database. Finally the NEOSimulationModel contains the three key components to Model Execution. The first
 * two are the Initialization Loop Manager and the Run Loop Manager both of which provide for and control the simulation, while the third is the TimeKeeper
 * which provides a break between cycles of execution and which allows the potential for retrieving previously calculated values, etc. Only one TimeKeeper can
 * be associated with a NEOSimulationModel The model object's constructor will call the timekeeper's constructor so that each model object has one and only one
 * TimeKeeper, but multiple models could run in the same JVM if we ever decide that is important. The TimeKeeper reference is made available to the package
 * developer.
 * 
 * @author Isaac Griffith
 */
public class NEOSimulationModel extends SimulationModel {

    /**
     * Constructs a new empty NEOSimulationModel
     */
    public NEOSimulationModel(NEOContext context, ModelConfig config, RunConfig runConfig, boolean enableLogging)
    {
        super(context, config, runConfig);

        this.name = runConfig.getModelName() + "_"
                + String.format("%1$tH%1$tM_%1$tm%1$td%1$tY", Calendar.getInstance());
        if (enableLogging)
            enableLogging();

        nextHolonID = 0L;
        nextStateValID = 0L;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.SimulationModel#execute()
     */
    @Override
    public final synchronized void execute()
    {
        outManager.processConfigs();
        depManager.setRegistrations();
        System.out.println("---------------------------------------------------------------");
        System.out.println(" Model Run Information: " + name);
        System.out.println("---------------------------------------------------------------");
        System.out.println("   * Initializing Model");
        this.isInitializing = true;
        depManager.createGraphs();
        initSolver.addStoppingCondition(new StoppingCondition(ConditionType.AtTick, timeKeeper.getTick()));
        initSolver.execute();

        // Check if any problems have been logged during initialization.
        if (logger.getNumberOfSevereProblemsLogged() >= 1)
        {
            System.out.println("        * Number of severe problems logged: "
                    + logger.getNumberOfSevereProblemsLogged());
            return;
        }
        else
        {
            logger.clearCounts();
        }

        System.out.println("   * Executing Model");
        this.isInitializing = false;
        depManager.clear();
        depManager.createGraphs();
        // Thread runLoopThread = new Thread(execSolver);
        // runLoopThread.start();
        execSolver.execute();
        System.out.println("   * Model Run Complete!");
        System.out.print("\n");
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.IOutputProvider#pause()
     */
    @Override
    public synchronized void pause()
    {
        // If execSolver hasn't been initialized, then we obviously are not yet
        // attempting to output, so do nothing.
        if (execSolver != null)
        {
            execSolver.pause();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.IOutputProvider#resume()
     */
    @Override
    public synchronized void resume()
    {
        // If execSolver hasn't been initialized, then we obviously are not
        // attempting to output yet
        if (execSolver != null)
        {
            execSolver.resume();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public SimulationModel call() throws Exception
    {
        execute();

        return this;
    }
}
