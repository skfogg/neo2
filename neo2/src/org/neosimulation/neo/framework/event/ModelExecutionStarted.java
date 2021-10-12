/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelExecutionStarted - An event signaling that the execution phase of the
 * model run has begun.
 * 
 * @author Isaac Griffith
 */
public class ModelExecutionStarted extends NEOEvent {

    /**
     * Constructs a new ModelExecutionStarted event.
     * 
     * @param source
     *            The Simulation Model which has just entered the execution
     *            phase.
     */
    public ModelExecutionStarted(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
