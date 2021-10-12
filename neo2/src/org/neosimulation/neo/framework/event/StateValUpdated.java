/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * StateValUpdated - An event signaling that some StateVal has just been updated
 * by its Dynam.
 * 
 * @author Isaac Griffith
 */
public class StateValUpdated extends NEOEvent {

    /**
     * Constructs a new StateValUpdated event
     * 
     * @param source
     *            The StateVal that was just updated.
     */
    public StateValUpdated(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
