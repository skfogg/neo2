/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * LoopIterationStarted - An event signaling that some Simulation Model has just
 * started the next iteration of its main execution loop.
 * 
 * @author Isaac Griffith
 */
public class LoopIterationStarted extends NEOEvent {

    /**
     * Constructs a new LoopIterationStarted Event
     * 
     * @param source
     *            The Simulation Model whose main execution loop has just began
     *            it's next iteration
     */
    public LoopIterationStarted(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
