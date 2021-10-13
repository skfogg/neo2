/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * NEOAppFailed - An event that signals that some NEOApp has failed in some
 * fashion.
 * 
 * @author Isaac Griffith
 */
public class NEOAppFailed extends NEOEvent {

    /**
     * Constructs a new NEOAppFailed event
     * 
     * @param source
     *            The NEOApp that just failed.
     */
    public NEOAppFailed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
