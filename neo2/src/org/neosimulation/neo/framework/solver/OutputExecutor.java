/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * OutputExecutor - Manages the creation of OutputEvents from OutputJobs without
 * interrupting the flow of execution in the Grand Loop, currently this class
 * uses a single thread per OutputListener attached to the SimulationModel.
 * 
 * @author Isaac Griffith
 */
public class OutputExecutor implements Runnable, Serializable {

    /**
     * Queue of OutputJobs pending processing
     */
    private Queue<OutputJob> pending;
    /**
     * Queue of Futures from OutputJobs currently in process
     */
    private List<Future<?>> processing;
    /**
     * The Service managing Threads and the handing out of jobs.
     */
    private ExecutorService outputService;
    /**
     * Flag to indicate whether the OutputExecutor is paused or not
     */
    private boolean paused;
    /**
     * Flag to indicate whether the OutputExecutor has finished processing all
     * of its jobs
     */
    private boolean finished;
    /**
     * SimulationModel providing the OutputJobs requiring processing
     */
    private SimulationModel model;
    /**
     * Flag to indicate whether or not the OutputExecutor can terminate once it
     * has finished processing
     */
    private boolean terminate;

    /**
     * Constructs a new OutputExecutor which processes OutputJobs handed to it
     * by the provided SimulationModel
     * 
     * @param model
     *            SimulationModel providing OutputJobs
     */
    public OutputExecutor(SimulationModel model)
    {
        pending = new ConcurrentLinkedQueue<>();
        processing = new CopyOnWriteArrayList<>();
        outputService = Executors.newSingleThreadExecutor();
        paused = false;
        finished = true;
        terminate = false;
        this.model = model;
    }

    /**
     * Forces the OutputExecutor to stop processing jobs
     */
    public synchronized void pause()
    {
        this.paused = true;
    }

    /**
     * Allows the OutputExecutor to continue processing jobs
     */
    public synchronized void resume()
    {
        this.paused = false;
    }

    /**
     * Checks to see whether the OutputExecutor has completed processing all of
     * its jobs.
     * 
     * @return true if there are no jobs waiting to be processed, or are in the
     *         middle of processing, false otherwise.
     */
    @SuppressWarnings("unused")
    public boolean isFinished()
    {
        return finished;
    }

    /**
     * Adds a job to pending job queue
     * 
     * @param job
     *            Job to be added
     */
    public synchronized void add(OutputJob job)
    {
        pending.add(job);

        if (finished)
        {
            finished = false;
        }
    }

    /**
     * Sets the terminate flag to true, the informs the OutputExecutor that the
     * SimulationModel is waiting for it to finish processing all of its output
     * jobs before the model can move onto the next iteration of the Grand Loop.
     */
    public synchronized void canTerminate()
    {
        this.terminate = true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        // first check if there are any pending jobs
        // The while loop is there to wait until the finished flag and the
        // terminate flag is set
        // while ((!this.paused || !this.finished) && !this.terminate)
        while (!((this.paused || this.finished) && this.terminate))
        {
            if (!pending.isEmpty())
            {
                OutputJob job = pending.poll();
                OutputAgent agent = new OutputAgent(model, job);

                Future<?> future = outputService.submit(agent);
                processing.add(future);
            }

            for (int i = 0; i < processing.size(); i++)
            {
                Future<?> future = processing.get(i);
                if (future.isDone())
                {
                    processing.remove(i);
                    i--;
                }
            }
            if (processing.isEmpty() && pending.isEmpty())
            {
                this.finished = true;
            }
            else
            {
                this.finished = false;
            }
        }
    }
}
