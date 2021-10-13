/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelInitializationStartedException - An exception thrown by the ModelInitializationStarted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelInitializationStartedException extends TableProcessorException {

    /**
     * Constructs a new ModelInitializationStartedException with no message specified
     */
    public ModelInitializationStartedException()
    {
    }

    /**
     * Constructs a new ModelInitializationStartedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelInitializationStartedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelInitializationStartedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelInitializationStartedException.
     */
    public ModelInitializationStartedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelInitializationStartedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelInitializationStartedException.
     */
    public ModelInitializationStartedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
