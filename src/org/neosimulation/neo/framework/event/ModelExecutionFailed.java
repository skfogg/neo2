/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelExecutionFailed - An event signaling that some SimulationModel execution
 * has failed.
 * 
 * @author Isaac Griffith
 */
public class ModelExecutionFailed extends NEOEvent {

    /**
     * Constructs a new ModelExecutionFailed Event
     * 
     * @param source
     *            The model that failed
     */
    public ModelExecutionFailed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
