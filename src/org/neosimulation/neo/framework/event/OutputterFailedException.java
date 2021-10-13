/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * OutputterFailedException - An exception thrown by the OutputterFailed class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputterFailedException extends TableProcessorException {

    /**
     * Constructs a new OutputterFailedException with no message specified
     */
    public OutputterFailedException()
    {
    }

    /**
     * Constructs a new OutputterFailedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputterFailedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputterFailedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterFailedException.
     */
    public OutputterFailedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputterFailedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterFailedException.
     */
    public OutputterFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
