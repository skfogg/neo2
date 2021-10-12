/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * OutputterFailed - An event signaling that the Outputter has failed in some
 * fashion.
 * 
 * @author Isaac Griffith
 */
public class OutputterFailed extends NEOEvent {

    /**
     * Constructs a new OutputterFailed event.
     * 
     * @param source
     *            The Outputter that failed.
     */
    public OutputterFailed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
