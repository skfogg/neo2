/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * NetworkCreated - An event that signals the creation of a Model's internal
 * structure between currencies
 * 
 * @author Isaac Griffith
 */
public class NetworkCreated extends NEOEvent {

    /**
     * Constructs a new NetworkCreated event
     * 
     * @param source
     *            The Simulation Model whose network was just created.
     */
    public NetworkCreated(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
