package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * SingleOutputter - An implementation of the Outputter which uses a separate
 * thread from the main execution thread. This separate thread maintains all
 * operations of the outputter without spawning any additional worker threads.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class SingleOutputter extends Outputter {

    /**
     * Creates a single threaded Outputter that will use the properties file to
     * connect to a database. The Single thread Queue and single thread
     * Processor will also be created.
     * 
     * @param config
     *            the DbConfig encapsulation of the information required to
     *            connect to the database into which output should be placed.
     */
    public SingleOutputter(DbConfig config)
    {
        super(config);
        processor = new SingleOutputProcessor(this);
        queue = new SingleOutputQueue(this);
        dead = false;
        kill = false;

        myThread = new Thread(this);
        myThread.start();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.Outputter#getNextOutputEvent()
     */
    @Override
    protected OutputEvent getNextOutputEvent()
    {
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
        if (queue.getNumberOfOutputEvents() > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Unique thread implementation. Running in a seperate thread from the
     * solver, execute will continuously check for output and process it until
     * kill message it sent notifying the object that the solver is complete and
     * no more events will be queued.
     */
    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#execute()
     */
    @Override
    public void execute()
    {
        while (!kill)
        {
            processOutput();
            // Sleep for a small duration
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                NEOLogger.logInfo(this, "Thread interrupted while waiting for kill signal in the outputter.");
            }
        }
        endAll();
    }

    /**
     * Single pass to process all events in the queue. Does not influence the
     * state of the outputter, whether it is dead/killed
     */
    public void processOutput()
    {
        queue.run();
        if (queue.getNumberOfOutputEvents() > 0)
        {
            processor.run();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#endAll()
     */
    @Override
    public void endAll()
    {
        queue.kill();

        super.endAll();

        dead = true;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#flush()
     */
    @Override
    public void flush()
    {
        processor.flush();
    }
}
