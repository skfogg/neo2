/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * NEOAppStarted - An event signaling that some NEOApp has just started
 * execution
 * 
 * @author Isaac Griffith
 */
public class NEOAppStarted extends NEOEvent {

    /**
     * Constructs a new NEOAppStarted event
     * 
     * @param source
     *            The NEOApp that has just entered the execution phase
     */
    public NEOAppStarted(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
