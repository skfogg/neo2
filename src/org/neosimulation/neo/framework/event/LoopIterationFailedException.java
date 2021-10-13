/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * LoopIterationFailedException - An exception thrown by the LoopIterationFailed class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class LoopIterationFailedException extends TableProcessorException {

    /**
     * Constructs a new LoopIterationFailedException with no message specified
     */
    public LoopIterationFailedException()
    {
    }

    /**
     * Constructs a new LoopIterationFailedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public LoopIterationFailedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new LoopIterationFailedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this LoopIterationFailedException.
     */
    public LoopIterationFailedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new LoopIterationFailedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this LoopIterationFailedException.
     */
    public LoopIterationFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
