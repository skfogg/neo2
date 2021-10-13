/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOAppStartedException - An exception thrown by the NEOAppStarted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOAppStartedException extends TableProcessorException {

    /**
     * Constructs a new NEOAppStartedException with no message specified
     */
    public NEOAppStartedException()
    {
    }

    /**
     * Constructs a new NEOAppStartedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOAppStartedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOAppStartedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppStartedException.
     */
    public NEOAppStartedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOAppStartedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppStartedException.
     */
    public NEOAppStartedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
