/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * LoopIterationFailed - An event signaling that some Simulation Model's
 * execution loop has failed in some fashion.
 * 
 * @author Isaac Griffith
 */
public class LoopIterationFailed extends NEOEvent {

    /**
     * Constructs a new LoopIterationFailed event.
     * 
     * @param source
     *            Simulation Model whose main execution loop has just failed.
     */
    public LoopIterationFailed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
