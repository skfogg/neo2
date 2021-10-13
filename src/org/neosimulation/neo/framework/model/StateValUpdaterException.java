/**
 * 
 */
package org.neosimulation.neo.framework.model;


/**
 * StateValUpdaterException - An exception thrown by the StateValUpdater class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateValUpdaterException extends IUpdatableException {

    /**
     * Constructs a new StateValUpdaterException with no message specified
     */
    public StateValUpdaterException()
    {
    }

    /**
     * Constructs a new StateValUpdaterException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StateValUpdaterException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateValUpdaterException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValUpdaterException.
     */
    public StateValUpdaterException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValUpdaterException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValUpdaterException.
     */
    public StateValUpdaterException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
