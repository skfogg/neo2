/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelInitializationCompleteException - An exception thrown by the ModelInitializationComplete class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelInitializationCompleteException extends TableProcessorException {

    /**
     * Constructs a new ModelInitializationCompleteException with no message specified
     */
    public ModelInitializationCompleteException()
    {
    }

    /**
     * Constructs a new ModelInitializationCompleteException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelInitializationCompleteException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelInitializationCompleteException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelInitializationCompleteException.
     */
    public ModelInitializationCompleteException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelInitializationCompleteException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelInitializationCompleteException.
     */
    public ModelInitializationCompleteException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
