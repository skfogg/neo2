/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * LoopIterationCompletedException - An exception thrown by the LoopIterationCompleted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class LoopIterationCompletedException extends TableProcessorException {

    /**
     * Constructs a new LoopIterationCompletedException with no message specified
     */
    public LoopIterationCompletedException()
    {
    }

    /**
     * Constructs a new LoopIterationCompletedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public LoopIterationCompletedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new LoopIterationCompletedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this LoopIterationCompletedException.
     */
    public LoopIterationCompletedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new LoopIterationCompletedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this LoopIterationCompletedException.
     */
    public LoopIterationCompletedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
