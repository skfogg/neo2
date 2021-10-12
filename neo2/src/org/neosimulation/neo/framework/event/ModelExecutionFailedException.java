/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelExecutionFailedException - An exception thrown by the ModelExecutionFailed class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelExecutionFailedException extends TableProcessorException {

    /**
     * Constructs a new ModelExecutionFailedException with no message specified
     */
    public ModelExecutionFailedException()
    {
    }

    /**
     * Constructs a new ModelExecutionFailedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelExecutionFailedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelExecutionFailedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelExecutionFailedException.
     */
    public ModelExecutionFailedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelExecutionFailedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelExecutionFailedException.
     */
    public ModelExecutionFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
