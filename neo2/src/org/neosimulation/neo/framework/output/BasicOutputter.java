/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * BasicOutputter - A simplified implementation of the Outputter, which operates
 * solely within the main thread of execution.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class BasicOutputter extends Outputter {

    /**
     * Creates a Outputter on the same thread as solver that will use the
     * properties file to connect to a database. The basic queue and basic
     * processor will also be created.
     * 
     * @param config
     *            The DbConfig object describing the means by which a connection
     *            to the database can be formed.
     */
    public BasicOutputter(DbConfig config)
    {
        super(config);
        processor = new BasicOutputProcessor(this);
        queue = new BasicOutputQueue(this);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#execute()
     */
    @Override
    protected void execute()
    {
        processor.execute();
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
        // Always false as event is handled at the time of submission
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.Outputter#kill()
     */
    @Override
    public void kill()
    {
        super.kill();
        this.endAll();
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
