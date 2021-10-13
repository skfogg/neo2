/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelEventException - An exception thrown by the ModelEvent class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelEventException extends TableProcessorException {

    /**
     * Constructs a new ModelEventException with no message specified
     */
    public ModelEventException()
    {
    }

    /**
     * Constructs a new ModelEventException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelEventException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelEventException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelEventException.
     */
    public ModelEventException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelEventException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelEventException.
     */
    public ModelEventException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
