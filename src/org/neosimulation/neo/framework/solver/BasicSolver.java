/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.holon.Edge;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.OutputConfigManager;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;
import org.neosimulation.neo.framework.output.IOutputListener;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.framework.time.TimeKeeper;

/**
 * BasicSolver - Represents the most basic notion of executing a model. It
 * provides a simple single thread, iterative loop implementation.
 * 
 * @author Isaac Griffith
 */
public class BasicSolver extends Solver {

    /**
     * List of Nodes representing the processing order of the model
     */
    private List<IUpdatable> processList = new LinkedList<>();

    /**
     * Constructs a new BasicSolver for the provided model.
     * 
     * @param isInit
     *            true if this solver is for the initialization sequence
     * @param model
     *            Model to solve.
     */
    public BasicSolver(boolean isInit, SimulationModel model)
    {
        super(isInit, model);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Solver#initialize()
     */
    protected final void initialize()
    {
        processList = model.getDependencyManager().getProcessList();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Solver#execute()
     */
    @Override
    public void execute()
    {
        initialize();

        if (!isInit)
        {
            // ---- Do not remove the lines between dashes -----
            // System.out.println("\tExecution list");
            // for (IUpdatable n : processList)
            // {
            // StateValUpdater svu = (StateValUpdater) n;
            // StateVal sv = svu.getStateVal();
            // String holonName = "";
            // if (sv.getHolon() != null)
            // holonName = sv.getHolon().getName();
            // System.out.println("\t\t" + holonName + "." + sv.getName() + " "
            // + sv.getDynam().getClass().getSimpleName());
            // }
            //
            // System.out.println("\tEdge StateVal List:");
            // for (String id : model.getMatrix().getEdgeIDs())
            // {
            // System.out.println("\t" + id);
            // Edge e = model.getMatrix().getEdge(id);
            // Face to = e.getToFace();
            // System.out.println("\t\t" + to.getName());
            // for (StateVal sv : to.getStateVals())
            // System.out.println("\t\t\t" + sv.getName());
            //
            // Face from = e.getFromFace();
            // System.out.println("\t\t" + from.getName());
            // for (StateVal sv : from.getStateVals())
            // System.out.println("\t\t\t" + sv.getName());
            // }
            //
            // System.out.println("\n\tKnown Stopping Conditions");
            // for (StoppingCondition cond : stops)
            // {
            // System.out.println("\t\t" + cond);
            // }
            // --------------------------------------------------------

            // Process Initial Output for every StateVal in the Network
            // for (StateVal sv : model.getNetwork().getStateVals())
            // {
            // Set<String> tableNames =
            // model.getOutputTableNames(sv.getUpdater());
            // tableNames.add(model.getOutputConfigManager().getOutputInitialValuesTable());
            // this.processOutput(sv, tableNames);
            // }
        }

        while (!shouldExit())
        {
            TimeKeeper tk = this.model.getTimeKeeper();
            OutputConfigManager outMgr = this.model.getOutputConfigManager();
            outMgr.setTimeTick((long) tk.getTick());

            if (!isInit)
                processList = model.getDependencyManager().getProcessList(tk.getTick());

            for (int j = 0; j < processList.size(); j++)
            {
                while (this.isPaused)
                {
                    try
                    {
                        this.wait(1000);
                    }
                    catch (InterruptedException e)
                    {
                        NEOLogger.logInfoException(model,
                                "Interrupted while waiting to check if solver is still paused.", e);
                    }
                }

                IUpdatable updatable = processList.get(j);
                if (isInit)
                {
                    updatable.initialize();
                }
                else
                {
                    updatable.update();
                }
            }

            if (!isInit)
            {
                for (StateVal sv : model.getNetwork().getStateVals())
                {
                    if (model.shouldOutput(sv.getUpdater()))
                    {
                        this.processOutput(sv);
                    }
                }
            }
            else
            {
                for (StateVal sv : model.getNetwork().getStateVals())
                {
                    Set<String> tableNames = model.getOutputTableNames(sv.getUpdater());
                    tableNames.add(model.getOutputConfigManager().getOutputInitialValuesTable());
                    this.processOutput(sv, tableNames);
                }
            }
            for (IOutputListener listener : model.getOutputListeners())
                listener.flush();
        }

        this.exit();
    }
}
