/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelInitializationFailed - An event signaling that some Simulation Model's
 * initialization has failed.
 * 
 * @author Isaac Griffith
 */
public class ModelInitializationFailed extends NEOEvent {

    /**
     * Constructs a new ModelInitializationFailed event
     * 
     * @param source
     *            The Simulation Model which failed to initialize.
     */
    public ModelInitializationFailed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
