package org.neosimulation.neo.framework.output;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * ParallelOutputter - The outputter is the overall class that controls The
 * creation of OutputQueue, registration with OutputQueue, and creation of
 * OutputProcessors. IOutputProviders register for an OutputQueue through the
 * Outputter.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public class ParallelOutputter extends Outputter {

    /**
     * The list of output processors.
     */
    private Queue<OutputProcessor> processors;
    // TODO: implement full list of queues, not just one.
    /**
     * 
     */
    private List<OutputQueue> queues;
    /**
     * The list of futures allowing us to check that jobs submitted to the
     * threadpool are complete.
     */
    private List<Future<?>> futures;

    /**
     * Constructs a new instance of ParallelOutputter using the provided
     * DbConfig to connect to the database. When an IOUtputProvider connects to
     * this Outputter, the Queue and Processors will be created as needed.
     * 
     * @param config
     *            The DbConfig describing the properties used to connect to the
     *            database.
     */
    public ParallelOutputter(DbConfig config)
    {
        super(config);
        this.queue = new ParallelOutputQueue(this);
        this.processors = new ConcurrentLinkedQueue<>();
        this.queues = new CopyOnWriteArrayList<>();
        futures = new CopyOnWriteArrayList<>();
        threadPool = Executors.newCachedThreadPool();
        futures.add(threadPool.submit(queue));

        myThread = new Thread(this);
        myThread.start();
    }

    /**
     * Monitors the queues and determines if more output processors or less
     * output processors is needed.
     */
    private void monitorQueues()
    {
        // FIXME Requires Refactoring
        boolean hasMessages = false;

        // Iterator<OutputQueue> iter = queue.iterator();

        // while (iter.hasNext()) {
        if (queue.getNumberOfOutputEvents() > 0)
        {
            hasMessages = true;
            // break;
        }
        // }

        // If there are no messages, then there is no point to have
        // any output processors. The output processors kill themselves though.
        if (!hasMessages)
        {
            timeMultiplier = 1;
        }
        else
        {
            // the trival case. If there are no processors and there are
            // messages, then a processor needs to be created to retrieve all
            // the
            // messages.
            if (processors.size() == 0)
            {
                ParallelOutputProcessor processor = new ParallelOutputProcessor(this);
                futures.add(threadPool.submit(processor));
                processors.add(processor);
            }
            else
            {
                for (OutputProcessor processor : processors)
                {
                    if (processor.isDead())
                    {
                        futures.add(threadPool.submit(processor));
                    }
                }
            }
        }
    }

    /**
     * Kills all the available processors by cycling through them and kills
     * them.
     */
    @SuppressWarnings("unused")
    private void killAllProcessors()
    {
        ArrayList<OutputProcessor> removals = new ArrayList<OutputProcessor>();
        Iterator<OutputProcessor> projIter = processors.iterator();
        while (projIter.hasNext())
        {
            OutputProcessor process = projIter.next();
            removals.add(process);
            process.kill();
        }

        // removes the killed processes.
        processors.removeAll(removals);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#execute()
     */
    @Override
    public void execute()
    {
        try
        {
            long start, end, sleepTime;
            int i = 0;
            while (!kill)
            {
                if (i > 100)
                {
                    i = 0;
                    NEOLogger.logInfo(this, "Queued Messages: " + queue.getNumberOfOutputEvents());
                }

                start = System.currentTimeMillis();
                monitorQueues();
                end = System.currentTimeMillis();
                sleepTime = 1000 - (end - start);
                Thread.sleep(sleepTime > 0 ? sleepTime : 5);
                i++;
            }
        }
        catch (InterruptedException e)
        {
            NEOLogger.logException(this, "Outputter has failed.", e);
        }
        finally
        {
            this.endAll();
        }
        dead = true;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#endAll()
     */
    @Override
    public void endAll()
    {
        queue.kill();
        while (queue.getNumberOfOutputEvents() > 0)
        {
            try
            {
                this.wait(1000);
            }
            catch (InterruptedException ex)
            {
            }
        }

        super.endAll();

        for (Future<?> future : futures)
        {
            if (!future.isDone())
            {
                try
                {
                    this.wait(1000);
                }
                catch (InterruptedException ex)
                {
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.Outputter#getNextOutputEvent()
     */
    @Override
    protected OutputEvent getNextOutputEvent()
    {
        // OutputQueue queue = null;
        // QueueStatus status = QueueStatus.Empty;
        // Iterator<OutputQueue> iter = queue.iterator();
        //
        // while (iter.hasNext())
        // {
        // OutputQueue q = iter.next();
        // if (status.getValue() <= q.getStatus().getValue())
        // {
        // queue = q;
        // status = q.getStatus();
        // }
        // }

        // Since the current system is a single system. There is no queue
        // selection. In the future there will be a queue selection.
        return queue.getNextOutputEvent();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.Outputter#isOutputEventAvailable()
     */
    @Override
    protected boolean isOutputEventAvailable()
    {
        /*
         * Iterator<OutputQueue> iter = queue.iterator(); while (iter.hasNext())
         * { if (iter.next().getNumberOfOutputEvents() > 0) { return true; } }
         * return false;
         */
        if (queue.getNumberOfOutputEvents() > 0)
        {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.Outputter#getOutputProcessor()
     */
    @Override
    public OutputProcessor getOutputProcessor()
    {
        // TODO Implement this method
        return processors.peek();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#flush()
     */
    @Override
    public void flush()
    {
        for (OutputProcessor processor : processors)
        {
            processor.flush();
        }
    }
}
