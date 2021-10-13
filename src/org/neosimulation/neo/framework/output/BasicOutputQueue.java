/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * BasicOutputQueue - A Simplified implementation of the OutputQueue which does
 * not delay processing. It operates this way due to the necessity to the fact
 * that the BasicOutputter (and associated classes) are forced to operate within
 * the bounds of the main execution thread.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class BasicOutputQueue extends OutputQueue {

    /**
     * Creates the BasicOutputQueue. the output queue is an access point into
     * which output is sent. Once an event is sent over through the interface,
     * the output is processed into the database
     */
    public BasicOutputQueue(BasicOutputter outputter)
    {
        super(outputter);
    }

    /**
     * Instantly processes the output event instead of holding in a queue. This
     * is to required to stay on the same thread as the solver and not overrun
     * in memory requirements. Will not process if outputter is killed. This
     * enables the solver to run without having the outputter work.
     * 
     * @param outputEvent
     *            Event sent by the Solver
     */
    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputListener#reportOutputEvent
     * (org.neosimulation.neo.framework.output.OutputEvent)
     */
    @Override
    public boolean reportOutputEvent(OutputEvent outputEvent)
    {
        if (!outputter.isDead())
        {
            this.outputs.offer(outputEvent);
            outputter.execute();
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputQueue#getOutputter()
     */
    @Override
    public Outputter getOutputter()
    {
        return (BasicOutputter) outputter;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.IOutputListener#finishedOutput
     * (org.neosimulation.neo.framework.output.IOutputProvider)
     */
    @Override
    public void finishedOutput(IOutputProvider provider)
    {
        provider.removeOutputListener(this);
        outputProviders.remove(provider);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.IOutputListener#flush()
     */
    @Override
    public void flush()
    {
        while (!outputs.isEmpty())
        {
            outputter.execute();
        }

        outputter.flush();
    }
}
