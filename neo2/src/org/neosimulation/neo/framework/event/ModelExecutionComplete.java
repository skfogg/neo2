/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelExecutionComplete - An event signaling that some Simulation Model has
 * completed its execution
 * 
 * @author Isaac Griffith
 */
public class ModelExecutionComplete extends NEOEvent {

    /**
     * Constructs a new ModelExecutionComplete event.
     * 
     * @param source
     *            The Simulation Model that has just completed its execution.
     */
    public ModelExecutionComplete(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
