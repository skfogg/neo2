/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * StoppingConditionReachedException - An exception thrown by the StoppingConditionReached class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class StoppingConditionReachedException extends TableProcessorException {

    /**
     * Constructs a new StoppingConditionReachedException with no message specified
     */
    public StoppingConditionReachedException()
    {
    }

    /**
     * Constructs a new StoppingConditionReachedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StoppingConditionReachedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StoppingConditionReachedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StoppingConditionReachedException.
     */
    public StoppingConditionReachedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StoppingConditionReachedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StoppingConditionReachedException.
     */
    public StoppingConditionReachedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
