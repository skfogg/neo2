/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * StateValOutputSentException - An exception thrown by the StateValOutputSent class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateValOutputSentException extends TableProcessorException {

    /**
     * Constructs a new StateValOutputSentException with no message specified
     */
    public StateValOutputSentException()
    {
    }

    /**
     * Constructs a new StateValOutputSentException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StateValOutputSentException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateValOutputSentException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValOutputSentException.
     */
    public StateValOutputSentException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValOutputSentException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValOutputSentException.
     */
    public StateValOutputSentException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
