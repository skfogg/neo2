/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * StoppingConditionReached - An event signaling that a Simulation Model's
 * Stopping Condition has been reached.
 * 
 * @author Isaac Griffith
 */
public class StoppingConditionReached extends NEOEvent {

    /**
     * Constructs a new StoppingConditionReached event
     * 
     * @param source
     *            The StoppingCondition whose condition has been met.
     */
    public StoppingConditionReached(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
