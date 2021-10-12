/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * NEOAppCompleted - An event signaling that some NEOApp has completed
 * execution.
 * 
 * @author Isaac Griffith
 */
public class NEOAppCompleted extends NEOEvent {

    /**
     * Constructs a new NEOAppCompleted event
     * 
     * @param source
     *            The NEOApp that just completed execution
     */
    public NEOAppCompleted(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
