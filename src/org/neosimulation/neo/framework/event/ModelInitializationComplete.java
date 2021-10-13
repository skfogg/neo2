/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelInitializationComplete - An event signaling that some Simulation Model
 * has just completed the inialization phase.
 * 
 * @author Isaac Griffith
 */
public class ModelInitializationComplete extends NEOEvent {

    /**
     * Constructs a new ModelInitializationComplete event.
     * 
     * @param source
     *            The Simulation Model that just completed initialization.
     */
    public ModelInitializationComplete(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
