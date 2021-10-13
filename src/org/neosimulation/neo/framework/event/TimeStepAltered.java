/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * TimeStepAltered - An event indicating that some Simulation Model's timeStep
 * interval has been adjusted.
 * 
 * @author Isaac Griffith
 */
public class TimeStepAltered extends NEOEvent {

    /**
     * Constructs a new TimeStepAltered event
     * 
     * @param source
     *            The Simulation Model whose time step interval has been
     *            modified.
     */
    public TimeStepAltered(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
