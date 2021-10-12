/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * ModelEventProviderException - An exception thrown by the ModelEventProvider class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelEventProviderException extends TableProcessorException {

    /**
     * Constructs a new ModelEventProviderException with no message specified
     */
    public ModelEventProviderException()
    {
    }

    /**
     * Constructs a new ModelEventProviderException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelEventProviderException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelEventProviderException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelEventProviderException.
     */
    public ModelEventProviderException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelEventProviderException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelEventProviderException.
     */
    public ModelEventProviderException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
