/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.neosimulation.neo.framework.output;

/**
 * IOutputListener - An object that will listen to an IOutputProvider.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public interface IOutputListener {

    /**
     * Attempts to report an output event. If there is not enough memory
     * available, the event will not store the output.
     * 
     * @param outputEvent
     *            Event sent by the Solver
     */
    boolean reportOutputEvent(OutputEvent outputEvent);

    /**
     * The IOutputProvider has now finished with all outputting and has no more
     * need to have an output queue.
     */
    void finishedOutput(IOutputProvider provider);

    /**
     * Retrieves the number of current output events still waiting to be
     * processed.
     * 
     * @return The number of output events needed to process.
     */
    int getNumberOfOutputEvents();

    /**
     * Retrieves the Outputter to which this IOutputListener is associated with
     * 
     * @return The Outputter this IOutputListener is associated with
     */
    Outputter getOutputter();

    void flush();
}
