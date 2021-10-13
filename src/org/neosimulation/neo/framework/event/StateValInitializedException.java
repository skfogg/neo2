/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * StateValInitializedException - An exception thrown by the StateValInitialized class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateValInitializedException extends TableProcessorException {

    /**
     * Constructs a new StateValInitializedException with no message specified
     */
    public StateValInitializedException()
    {
    }

    /**
     * Constructs a new StateValInitializedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StateValInitializedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateValInitializedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValInitializedException.
     */
    public StateValInitializedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValInitializedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValInitializedException.
     */
    public StateValInitializedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
