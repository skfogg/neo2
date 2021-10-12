/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * LoopIterationStartedException - An exception thrown by the LoopIterationStarted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class LoopIterationStartedException extends TableProcessorException {

    /**
     * Constructs a new LoopIterationStartedException with no message specified
     */
    public LoopIterationStartedException()
    {
    }

    /**
     * Constructs a new LoopIterationStartedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public LoopIterationStartedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new LoopIterationStartedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this LoopIterationStartedException.
     */
    public LoopIterationStartedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new LoopIterationStartedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this LoopIterationStartedException.
     */
    public LoopIterationStartedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
