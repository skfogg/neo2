/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * OutputterStarted - An event signaling that some Outputter has begun
 * processing OutputEvents.
 * 
 * @author Isaac Griffith
 */
public class OutputterStarted extends NEOEvent {

    /**
     * Constructs a new OutputterStarted event
     * 
     * @param source
     *            The Outputter that has just started processing
     */
    public OutputterStarted(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
