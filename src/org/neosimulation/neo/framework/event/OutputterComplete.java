/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * OutputterCompleted - An event signaling that the Outputter has finished
 * processing its final output event.
 * 
 * @author Isaac Griffith
 */
public class OutputterComplete extends NEOEvent {

    /**
     * Constructs a new OutputterComplete event
     * 
     * @param source
     *            The Output that has just completed processing
     */
    public OutputterComplete(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
