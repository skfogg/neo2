/**
 * 
 */
package org.neosimulation.neo.framework.event;

import java.util.EventObject;

/**
 * NEOEvent - the base class for all NEOEvents
 * 
 * @author Isaac Griffith
 */
public abstract class NEOEvent extends EventObject {

    /**
     * Constructs a new NEOEvent
     * 
     * @param source
     *            The object that issued the event
     */
    public NEOEvent(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
