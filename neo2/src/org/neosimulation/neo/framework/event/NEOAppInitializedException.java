/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOAppInitializedException - An exception thrown by the NEOAppInitialized class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOAppInitializedException extends TableProcessorException {

    /**
     * Constructs a new NEOAppInitializedException with no message specified
     */
    public NEOAppInitializedException()
    {
    }

    /**
     * Constructs a new NEOAppInitializedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOAppInitializedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOAppInitializedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppInitializedException.
     */
    public NEOAppInitializedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOAppInitializedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppInitializedException.
     */
    public NEOAppInitializedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
