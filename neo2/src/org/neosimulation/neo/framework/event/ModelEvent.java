/**
 * 
 */
package org.neosimulation.neo.framework.event;

import java.util.EventObject;

/**
 * ModelEvent - Base class for model related events
 * 
 * @author Isaac Griffith
 */
public abstract class ModelEvent extends EventObject {

    /**
     * Constructs a new ModelEvent class
     * 
     * @param source
     *            The simulation model that produced this event
     */
    public ModelEvent(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
