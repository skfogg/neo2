/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelInitializationStarted - An event which signals that some Simulation
 * Model has recently begun the initialization phase
 * 
 * @author Isaac Griffith
 */
public class ModelInitialzationStarted extends NEOEvent {

    /**
     * Constructs a new ModelInitilzationStarted event
     * 
     * @param source
     *            The Simulation Model which has just entered the initialization
     *            phase.
     */
    public ModelInitialzationStarted(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
