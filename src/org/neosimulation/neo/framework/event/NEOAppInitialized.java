/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * NEOAppInitialized - An event that signals that some NEOApp has just begun its
 * initialization.
 * 
 * @author Isaac Griffith
 */
public class NEOAppInitialized extends NEOEvent {

    /**
     * Constructs a new NEOAppInitialized event
     * 
     * @param source
     *            the NEOApp that just entered the initialization phase
     */
    public NEOAppInitialized(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
