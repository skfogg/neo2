/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * StateValInitialized - An event that signals that some StateVal has just been
 * initialzied
 * 
 * @author Isaac Griffith
 */
public class StateValInitialized extends NEOEvent {

    /**
     * Constructs a new StateValInitialized event
     * 
     * @param source
     *            The StateVal that was just initialized.
     */
    public StateValInitialized(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
