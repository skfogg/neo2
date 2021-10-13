/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * LoopIterationComplete - An event signaling that the main execution loop has
 * completed an iteration.
 * 
 * @author Isaac Griffith
 */
public class LoopIterationComplete extends NEOEvent {

    /**
     * Constructs a new LoopIterationComplete event.
     * 
     * @param source
     *            Simulation Model that has just completed a iteration of
     *            execution
     */
    public LoopIterationComplete(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
