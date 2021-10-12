package org.neosimulation.neo.framework.output;

/**
 * SingleOutputQueue - A Single threaded implementation of HybridOutputQueue
 * used by the SingleOutputter.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class SingleOutputQueue extends HybridOutputQueue {

    /**
     * Creates the SingleOutputQueue. The output queue is a storehouse for
     * OutputEvents. The outputs should be processed by the
     * SingleOutputProcessor
     * 
     * @param outputter
     *            Outputter this queue is associated with.
     */
    public SingleOutputQueue(SingleOutputter outputter)
    {
        super(outputter);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputQueue#getOutputter()
     */
    @Override
    public Outputter getOutputter()
    {
        return outputter;
    }

    /**
     * Monitors the queue by checking the memory available. then processes it
     */
    public void execute()
    {
        // nothing else needs to be determined because there is only one queue
        // in the single threaded version
        determineQueueStatus();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.IOutputListener#flush()
     */
    @Override
    public void flush()
    {
        outputter.flush();
    }
}
