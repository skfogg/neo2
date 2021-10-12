/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOEventProviderException - An exception thrown by the NEOEventProvider class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOEventProviderException extends TableProcessorException {

    /**
     * Constructs a new NEOEventProviderException with no message specified
     */
    public NEOEventProviderException()
    {
    }

    /**
     * Constructs a new NEOEventProviderException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOEventProviderException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOEventProviderException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOEventProviderException.
     */
    public NEOEventProviderException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOEventProviderException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOEventProviderException.
     */
    public NEOEventProviderException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
