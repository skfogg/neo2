/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOEventListenerException - An exception thrown by the NEOEventListener class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOEventListenerException extends TableProcessorException {

    /**
     * Constructs a new NEOEventListenerException with no message specified
     */
    public NEOEventListenerException()
    {
    }

    /**
     * Constructs a new NEOEventListenerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOEventListenerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOEventListenerException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOEventListenerException.
     */
    public NEOEventListenerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOEventListenerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOEventListenerException.
     */
    public NEOEventListenerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
