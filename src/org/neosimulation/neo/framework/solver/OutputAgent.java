/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import java.io.Serializable;
import java.util.List;

import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.output.IOutputListener;
import org.neosimulation.neo.framework.output.IOutputProvider;
import org.neosimulation.neo.framework.output.OutputEvent;
import org.neosimulation.neo.framework.output.OutputType;

/**
 * OutputAgent - A Thread which handles OutputJobs created by a LoopAgent or
 * Solver and generates the corresponding OutputEvents to be handled by and
 * OutputListener.
 * 
 * @author Isaac Griffith
 */
public class OutputAgent implements Runnable, Serializable {

    /**
     * The SimulationModel this OutputAgent is processing output for
     */
    private SimulationModel model;
    /**
     * The OutputJob containing information to be placed in an OutputQueue.
     */
    private OutputJob job;

    /**
     * Constructs a new OutputAgent processing output information stored in the
     * provided OutputJob, for the provided SimulationModel.
     * 
     * @param model
     *            SimulationModel providing output information
     * @param job
     *            The Information to be Processed
     */
    public OutputAgent(SimulationModel model, OutputJob job)
    {
        this.model = model;
        this.job = job;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        OutputEvent event = new OutputEvent((IOutputProvider) model, job.getTableName(), OutputType.Insert, job.getTick(), job.getTime(),
                job.getHolonName(), job.getStateVal(), job.getValue());
        List<IOutputListener> listeners = model.getOutputListeners();
        for(int i = 0; i < listeners.size(); i++)
        {
            listeners.get(i).reportOutputEvent(event);
        }
    }
}
