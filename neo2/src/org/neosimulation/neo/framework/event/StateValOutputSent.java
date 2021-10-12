/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * StateValOutputSent - An event signaling that some StateVal's current value
 * has been sent to the outputter.
 * 
 * @author Isaac Griffith
 */
public class StateValOutputSent extends NEOEvent {

    /**
     * Constructs a new StateValOutputSent event
     * 
     * @param source
     *            The StateVal whose value was just sent to the Outputter.
     */
    public StateValOutputSent(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
