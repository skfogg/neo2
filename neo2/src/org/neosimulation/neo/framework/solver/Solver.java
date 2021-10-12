package org.neosimulation.neo.framework.solver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;
import org.neosimulation.neo.framework.output.IOutputListener;
import org.neosimulation.neo.framework.output.IOutputProvider;
import org.neosimulation.neo.framework.output.OutputEvent;
import org.neosimulation.neo.framework.output.OutputQueue;
import org.neosimulation.neo.framework.output.OutputType;
import org.neosimulation.neo.framework.output.Outputter;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.framework.time.TimeKeeper;

/**
 * Solver - This class encapsulates the logic and information necessary to
 * control the iteration of a Model initialization or execution.
 * 
 * @author Isaac Griffith
 */
public abstract class Solver implements Runnable, Serializable {

    /**
     * Containing SimulationModel
     */
    protected SimulationModel model;
    /**
     * Type of this Solver
     */
    protected final boolean isInit;
    /**
     * Flag to determine if the model has been paused
     */
    protected boolean isPaused = false;
    /**
     * List of all active stopping conditions
     */
    protected List<StoppingCondition> stops;

    /**
     * Constructs a new Solver of the given isInit for the provided
     * SimulationModel
     * 
     * @param isInit
     *            Type of Solver to construct
     * @param model
     *            SimulationModel this LoopManger is contained in
     */
    public Solver(boolean isInit, SimulationModel model)
    {
        this.isInit = isInit;
        this.model = model;
        stops = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        execute();
    }

    /**
     * Adds an OutputJob based on the information stored in the provided node to
     * the OutputExecutor.
     * 
     * @param process
     *            Node to process and generate and OutputJob for.
     */
    public synchronized void addOutput(IUpdatable updatable)
    {
        if (updatable != null)
        {
            if (updatable instanceof StateValUpdater)
            {
                StateValUpdater stateValUpdater = (StateValUpdater) updatable;
                StateVal stateVal = stateValUpdater.getStateVal();

                if (notFromTimeKeeper(stateVal))
                {
                    processOutput(stateVal);
                }
            }
        }
    }

    /**
     * Checks whether the provided StateVal is not one associated with the
     * TimeKeeper.
     * 
     * @param sv
     *            StateVal to check
     * @return false if the provided StateVal is one of the TimeKeeper's
     *         StateVals, true otherwise.
     */
    private boolean notFromTimeKeeper(StateVal sv)
    {
        String name = sv.getName();
        return !(name.equals(TimeKeeper.TICK_STATEVAL_NAME) || name.equals(TimeKeeper.TIME_STATEVAL_NAME) || name
                .equals(TimeKeeper.TIMESTEP_STATEVAL_NAME));
    }

    /**
     * Processes the provided StateVal for output.
     * 
     * @param stateVal
     *            StateVal to be processed
     */
    protected void processOutput(StateVal stateVal)
    {
        Set<String> tableNames = model.getOutputTableNames(stateVal.getUpdater());
        if (!tableNames.isEmpty())
        {
            processOutput(stateVal, tableNames);
        }
    }

    /**
     * Processes the provided StateVal for output into the provided named table.
     * 
     * @param stateVal
     *            StateVal to be processed for output
     * @param tableNames
     *            names of the tables into which the output should be stored.
     */
    public void processOutput(StateVal stateVal, Set<String> tableNames)
    {
        Holon holon = stateVal.getHolon();

        double time = model.getCurrentTime();
        String holonName = holon.getName();
        String stateValName = stateVal.getName();
        long tick = model.getTick();
        String value = stateVal.getStringValue();

        if (model.getOutputConfigManager().isLocalOutput())
        {
            System.out.println((tick - model.getTimeKeeper().getTickInterval()) + "\t" + time + "\t" + holonName + "\t" + stateValName + "\t" + value);
        }
        
        for (String tableName : tableNames)
        {
            if (!tableName.isEmpty() && tableName != null)
                fireOutput(tableName, (tick - model.getTimeKeeper().getTickInterval()), time, holonName, stateValName,
                        value);
        }
    }

    /**
     * Fires output to all SimulationModel OutputListeners
     * 
     * @param tableName
     *            Name of the table to which output is to be put
     * @param tick
     *            Value of the tick
     * @param time
     *            Value of model time
     * @param holonName
     *            Holon Name
     * @param stateValName
     *            StateVal Name
     * @param value
     *            StateVal Value
     */
    private void fireOutput(String tableName, long tick, double time, String holonName, String stateValName,
            String value)
    {
        for (IOutputListener listener : model.getOutputListeners())
        {
            OutputEvent event = new OutputEvent((IOutputProvider) model, tableName, OutputType.Insert, tick, time,
                    holonName, stateValName, value);
            listener.reportOutputEvent(event);
            // outputService.add(job);
        }
    }

    /**
     * Method to pause the output service and the solver
     */
    public synchronized void pause()
    {
        this.isPaused = true;
    }

    /**
     * Method to resume the output service
     */
    public synchronized void resume()
    {
        this.isPaused = false;
    }

    /**
     * Starts the executor service by adding jobs to it, for each job added, a
     * Future is created and then mapped to the Node. Each job generates a new
     * LoopExecutor to process the node
     */
    public abstract void execute();

    /**
     * Using the graphs set iterator a mapping of threads to nodes is created,
     * one thread per LoopExecutor. The threads are then joined to each other in
     * order to ensure that connected nodes are not executed out of order.
     */
    protected abstract void initialize();

    /**
     * Called to quit the simulation. Currently this is designed for a single
     * model system.
     */
    /*
     * TODO: if we desire (and we do) the ability to operate multiple models at
     * any one time, this method should be moved into NEORuntime and with a
     * chain of callbacks to SimulationModel. It also will simply end the
     * calling model's thread and unless all other models have finished it will
     * simply keep the system running. The only time System.exit() will be
     * called is if all models have completed. The logic which ensures that all
     * output has completed for this solver should remain within the solver, but
     * the rest of the above mentioned logic should be moved to other places
     */
    protected synchronized void exit()
    {
        if (!isInit)
        {
            OutputQueue queue = (OutputQueue) model.getOutputListeners().get(0);
            queue.finishedOutput(model);

            while (queue.getNumberOfOutputEvents() > 0)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            Outputter out = queue.getOutputter();
            out.kill();

            while (!out.isDead())
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return;
        }
    }

    /**
     * Tests whether a stopping condition has been met and the model should
     * exit.
     * 
     * @return true if a StoppingCondition has been met, false otherwise
     */
    protected boolean shouldExit()
    {
        boolean retVal = false;

        if (model.isLoggingEnabled())
        {
            long problems = model.getLogger().getNumberOfSevereProblemsLogged();
            if (problems >= 1)
            {
                retVal = true;
                NEOLogger.logInfo(model,
                        "Too many problems encountered, stopping the model run for: " + model.getName());
            }
        }

        for (StoppingCondition stop : stops)
        {
            if (stop.shouldStop(this.model.getTimeKeeper()))
            {
                retVal = true;
            }
        }

        return retVal;
    }

    /**
     * Allows the user to generate a stopping condition for the beginning of the
     * next time tick. The user can use this to conditionally cause the model to
     * stop executing without having to worry about ensuring all output has
     * finished, etc.
     */
    public synchronized void gracefulExit()
    {
        StoppingCondition cond = new StoppingCondition(StoppingCondition.ConditionType.UserInitiated,
                this.model.getTick() + this.model.getTimeStep());

        addStoppingCondition(cond);
    }

    /**
     * Adds the specified stopping condition to the list of active stopping
     * conditions
     * 
     * @param cond
     *            Condition to be added to the active stopping conditons list
     */
    public void addStoppingCondition(StoppingCondition cond)
    {
        stops.add(cond);
    }

    /**
     * Sets the list of active stopping conditions to be the provided list
     * 
     * @param stops
     *            List of Stopping Conditions to be set to activ
     */
    public synchronized void setStoppingConditions(List<StoppingCondition> stops)
    {
        this.stops = stops;
    }

    /**
     * Retrieves the list of StoppingCondition objects associated with this
     * Solver.
     * 
     * @return All Known stopping conditions associated with this Solver.
     */
    public List<StoppingCondition> getStoppingConditions()
    {
        return stops;
    }
}