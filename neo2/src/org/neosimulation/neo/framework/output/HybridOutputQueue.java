package org.neosimulation.neo.framework.output;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * HybridOutputQueue - An intermediary implementation of the OutputQueue which
 * sets the OutputQueue up to handle threaded opearation.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public abstract class HybridOutputQueue extends OutputQueue {

    /**
     * The status of the queue.
     */
    protected QueueStatus status;
    /**
     * the time spent in the state of being critical.
     */
    protected long startTimeInCritical;
    /**
     * the time spent in the state of being full.
     */
    protected long startTimeInFull;
    /**
     * Halts the output queue from accepting any more input until things are
     * resolved.
     */
    private boolean halt = false;
    /**
     * Kill the output queue, stopping it operation completely.
     */
    protected boolean kill = false;
    /**
     * With a stateval id being 15 characters long, this should still be safe
     * with the expected bytes in output queues.
     */
    private static final int EXPECTED_BYTES_IN_OUTPUT_QUEUE = 131;
    /**
     * When the amount of expected outputs to be created is at >1000, then we
     * are entering critical mass.
     */
    private static final int CRITICAL_MEMORY_AMOUNT = 3100;
    /**
     * When the amount of expected outputs to be created is at >75, then we are
     * entering a state of being full.
     */
    private static final int FULL_MEMORY_AMOUNT = 2600;
    /**
     * The minimum amount of operating memory.
     */
    private static final int OUT_OF_MEMORY_AMOUNT = 2000;
    /**
     * The memory required to exit halt.
     */
    protected static final int MEMORY_TO_EXIT_HALT = (int) Runtime.getRuntime().maxMemory() / 80;
    /**
     * The maximum time in full until its time to remove itself out of full and
     * pause output.
     */
    protected static final int MAX_TIME_IN_FULL = 8000;
    /**
     * Time spent in critical until its time to pause the IOutputProviders.
     */
    protected static final int MAX_TIME_IN_CRITICAL = 12500;

    /**
     * Constructs a new HybridOutputQueue
     */
    public HybridOutputQueue(Outputter outputter)
    {
        super(outputter);
        this.outputs = new ConcurrentLinkedQueue<>();
        this.outputProviders = new ConcurrentLinkedQueue<>();
        this.status = QueueStatus.Empty;
        this.startTimeInCritical = startTimeInFull = 0;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputQueue#run()
     */
    @Override
    public void run()
    {
        execute();
    }

    /**
     * Retrieves the total amount of memory that is currently available.
     */
    protected long getMemory()
    {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * Performs a few tests to determine the queue's status.
     */
    protected void determineQueueStatus()
    {
        long memory = getMemory();
        long numberOfOutputs = memory / EXPECTED_BYTES_IN_OUTPUT_QUEUE;
        if (halt)
        {
            /**
             * Fault checking. If the time in full or critical is going on to
             * long, its time to pause and let some memory be allocated back.
             */
            if (memory > MEMORY_TO_EXIT_HALT)
            {
                resumeOutput();
            }
        }

        if (numberOfOutputs > CRITICAL_MEMORY_AMOUNT)
        {
            setVariables(QueueStatus.Normal, 0, 0);
        }
        else if (numberOfOutputs > FULL_MEMORY_AMOUNT && timeInCritical() == 0)
        {
            setVariables(QueueStatus.Critical, 0, System.currentTimeMillis());
        }
        else if (timeInFull() == 0)
        {
            // the time in critical does not get reset because it is still below
            // critical.
            // The reason is because if the queue is jumping between
            // full/critical, no more output processors would be
            // created.
            setVariables(QueueStatus.Full, System.currentTimeMillis(), Long.MIN_VALUE);
        }

        if (shouldPause())
        {
            pauseOutput();
        }
    }

    /**
     * Sets the status, startTimeInFull, and startTimeInCritical to the provided
     * values.
     * 
     * @param status
     *            status new value
     * @param startTimeInFull
     *            startTimeInFull new value
     * @param startTimeInCritical
     *            startTimeInCritical new value
     */
    private void setVariables(QueueStatus status, long startTimeInFull, long startTimeInCritical)
    {
        this.status = status;
        if (startTimeInFull != Long.MIN_VALUE)
            this.startTimeInFull = startTimeInFull;
        if (startTimeInCritical != Long.MIN_VALUE)
            this.startTimeInCritical = startTimeInCritical;
    }

    /**
     * Tests whether the output queue should pause or not.
     * 
     * @return true if it should pause, false otherwise
     */
    private boolean shouldPause()
    {
        return (timeInFull() > MAX_TIME_IN_FULL || timeInCritical() > MAX_TIME_IN_CRITICAL);
    }

    /**
     * Resets the time that the queue has been in full/critical.
     */
    protected synchronized void resetCriticalAndFullTime()
    {
        startTimeInCritical = 0;
        startTimeInFull = 0;
        determineQueueStatus();
    }

    /**
     * Whether the queue is in critical mode or not.
     * 
     * @return true/false if in critical mode.
     */
    protected synchronized boolean isCritical()
    {
        return status == QueueStatus.Critical;
    }

    /**
     * Whether the queue is in full mode or not.
     * 
     * @return true/false if in full mode.
     */
    protected synchronized boolean isFull()
    {
        return status == QueueStatus.Full;
    }

    /**
     * Retrieves the total time (in ms) that this queue has spent in Critical
     * status
     * 
     * @return The amount of time that the queue has spent in critical status.
     */
    protected synchronized long timeInCritical()
    {
        long returnValue = 0;

        if (startTimeInCritical != 0)
        {
            returnValue = System.currentTimeMillis() - startTimeInCritical;
        }

        return returnValue;
    }

    /**
     * Retrieves the total time (in ms) that this queue has spent in Full status
     * 
     * @return The amount of time that the queue has spent in Full status.
     */
    protected synchronized long timeInFull()
    {
        long returnValue = 0;

        if (startTimeInFull != 0)
        {
            returnValue = System.currentTimeMillis() - startTimeInFull;
        }

        return returnValue;
    }

    /**
     * pauses the inputters so that the output processors can catch up with the
     * amount of input coming in.
     */
    private synchronized void pauseOutput()
    {
        halt = true;
        Iterator<IOutputProvider> iter = outputProviders.iterator();
        while (iter.hasNext())
        {
            iter.next().pause();
        }
    }

    /**
     * resumes the Output Providers
     */
    private synchronized void resumeOutput()
    {
        halt = false;
        Iterator<IOutputProvider> iter = outputProviders.iterator();
        while (iter.hasNext())
        {
            iter.next().resume();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputQueue#getNextOutputEvent()
     */
    @Override
    protected OutputEvent getNextOutputEvent()
    {
        // to verify that this will not be turned into a race condition, thus
        // crashing the program ;)
        OutputEvent output = outputs.poll();
        if (outputs.isEmpty() && status.equals(QueueStatus.Emptying))
        {
            status = QueueStatus.Empty;
            flush();
            kill();
        }
        return output;
    }

    /**
     * Retrieves the status of this Queue.
     * 
     * @return the status of the queue.
     */
    public synchronized QueueStatus getStatus()
    {
        return status;
    }

    /**
     * Tests whether this OutputQueue has been halted or not.
     * 
     * @return true if the queue has halted, false otherwise.
     */
    protected synchronized boolean isHalted()
    {
        return halt;
    }

    /**
     * Registers the model with this specific queue.
     * 
     * @param provider
     *            OutputProvider providing OutputEvents to be processed
     */
    @Override
    protected synchronized void registerModel(IOutputProvider provider)
    {
        // adds this queue as an output listener.
        provider.addOutputListener(this);
        outputProviders.add(provider);
        provider.resume();
    }

    /**
     * The IOutputProvider has now finished with all outputting and has no more
     * need to have an output queue.
     * 
     * @param provider
     *            OutputProvider providing OutputEvents to be processed
     */
    public synchronized void finishedOutput(IOutputProvider provider)
    {
        provider.removeOutputListener(this);
        outputProviders.remove(provider);

        if (outputProviders.size() == 0)
        {
            this.status = QueueStatus.Emptying;
        }
        System.out.println("Q status = " + status);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputListener#reportOutputEvent
     * (org.neosimulation.neo.framework.output.OutputEvent)
     */
    @Override
    public boolean reportOutputEvent(OutputEvent outputEvent)
    {
        // int numberOfOutputs = (int) Runtime.getRuntime().freeMemory() /
        // EXPECTED_BYTES_IN_OUTPUT_QUEUE;
        int numberOfOutputs = (int) (getMemory() / EXPECTED_BYTES_IN_OUTPUT_QUEUE);
        boolean returnValue;

        if (numberOfOutputs < OUT_OF_MEMORY_AMOUNT)
        {
            returnValue = false;
        }
        else
        {
            returnValue = true;
            outputs.add(outputEvent);
        }

        return returnValue;
    }

    /**
     * Retrieves the number of models currently connected to this output queue.
     * 
     * @return the number of models connected to this output queue.
     */
    protected synchronized int getNumberOfModels()
    {
        return outputProviders.size();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputQueue#kill()
     */
    @Override
    protected void kill()
    {
        kill = true;
    }

}