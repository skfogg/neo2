/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelExecutionStartedException - An exception thrown by the ModelExecutionStarted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelExecutionStartedException extends TableProcessorException {

    /**
     * Constructs a new ModelExecutionStartedException with no message specified
     */
    public ModelExecutionStartedException()
    {
    }

    /**
     * Constructs a new ModelExecutionStartedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelExecutionStartedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelExecutionStartedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelExecutionStartedException.
     */
    public ModelExecutionStartedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelExecutionStartedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelExecutionStartedException.
     */
    public ModelExecutionStartedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
