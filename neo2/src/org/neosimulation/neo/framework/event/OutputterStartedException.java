/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * OutputterStartedException - An exception thrown by the OutputterStarted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputterStartedException extends TableProcessorException {

    /**
     * Constructs a new OutputterStartedException with no message specified
     */
    public OutputterStartedException()
    {
    }

    /**
     * Constructs a new OutputterStartedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputterStartedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputterStartedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterStartedException.
     */
    public OutputterStartedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputterStartedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterStartedException.
     */
    public OutputterStartedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
