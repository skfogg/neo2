/**
 * 
 */
package org.neosimulation.neo.framework.output;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * OutputQueue - Class which stores and manages the events sent by
 * IOutputProviders.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public abstract class OutputQueue implements IOutputListener, Serializable, Runnable {
    /**
     * The outputter involved
     */
    protected Outputter outputter;

    /**
     * The output event queue.
     */
    protected Queue<OutputEvent> outputs;

    /**
     * The list of inputs that will be giving information to this queue.
     */
    protected Queue<IOutputProvider> outputProviders;

    /**
     * Constructs a new empty OutputQueue.
     */
    public OutputQueue(Outputter outputter)
    {
        this.outputter = outputter;
        this.outputs = new ConcurrentLinkedQueue<>();
        this.outputProviders = new ConcurrentLinkedQueue<>();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputListener#getOutputter()
     */
    @Override
    public abstract Outputter getOutputter();

    /**
     * returns the next OutputEvent
     * 
     * @return OutputEvent sent by the Solver
     */
    protected OutputEvent getNextOutputEvent()
    {
        return this.outputs.poll();
    }

    /**
     * Registers the model with this specific queue.
     * 
     * @param provider
     *            OutputProvider providing OutputEvents to be processed
     */
    protected synchronized void registerModel(IOutputProvider provider)
    {
        // adds this queue as an output listener.
        provider.addOutputListener(this);
        outputProviders.add(provider);
        provider.resume();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.IOutputListener#
     * getNumberOfOutputEvents()
     */
    @Override
    public synchronized int getNumberOfOutputEvents()
    {
        return outputs.size();
    }

    /**
     * Monitors the queue by seeing how much of the memory is available every
     * second.
     */
    public void execute()
    {
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
    }

    /**
     * Kills the output queue.
     */
    protected void kill()
    {
    }
}
