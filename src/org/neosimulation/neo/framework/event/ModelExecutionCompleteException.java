/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelExecutionCompleteException - An exception thrown by the ModelExecutionComplete class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelExecutionCompleteException extends TableProcessorException {

    /**
     * Constructs a new ModelExecutionCompleteException with no message specified
     */
    public ModelExecutionCompleteException()
    {
    }

    /**
     * Constructs a new ModelExecutionCompleteException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelExecutionCompleteException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelExecutionCompleteException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelExecutionCompleteException.
     */
    public ModelExecutionCompleteException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelExecutionCompleteException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelExecutionCompleteException.
     */
    public ModelExecutionCompleteException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
