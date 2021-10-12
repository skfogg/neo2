package org.neosimulation.neo.framework.output;

import java.util.Iterator;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * ParallelOutputQueue - An OutputQueue will hold all the output events from all
 * the IOutputProviders.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public final class ParallelOutputQueue extends HybridOutputQueue {

    /**
     * Creates an output queue. The OutputQueue is a storehouse for
     * OutputEvents. The outputs should be processed by OutputProcessors.
     * 
     * @param outputter
     *            The Outputter that exists on this system.
     */
    protected ParallelOutputQueue(ParallelOutputter outputter)
    {
        super(outputter);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.HybridOutputQueue#execute()
     */
    @Override
    public void execute()
    {
        // FIXME Requires Refactoring
        try
        {
            long start, end, sleepTime;
            while (!kill)
            {
                start = System.currentTimeMillis();
                determineQueueStatus();
                end = System.currentTimeMillis();

                // sleeps for the remainder of a second.
                sleepTime = 1000 - (end - start);
                Thread.sleep(sleepTime > 0 ? sleepTime : 5);
            }
        }
        catch (InterruptedException e)
        {
            NEOLogger.logInfo(outputter, "The output queue has been interrupted");
        }
        finally
        {
            // grab new provider?
            // TODO: If there is an interruption, then this needs to be fixed.
            if (outputProviders.size() > 0)
            {
                Iterator<IOutputProvider> iter = outputProviders.iterator();
                while (iter.hasNext())
                {
                    IOutputProvider provider = iter.next();
                    provider.pause();
                    provider.removeOutputListener(this);
                }
            }
        }
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
