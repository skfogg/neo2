/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOAppCompletedException - An exception thrown by the NEOAppCompleted class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOAppCompletedException extends TableProcessorException {

    /**
     * Constructs a new NEOAppCompletedException with no message specified
     */
    public NEOAppCompletedException()
    {
    }

    /**
     * Constructs a new NEOAppCompletedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOAppCompletedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOAppCompletedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppCompletedException.
     */
    public NEOAppCompletedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOAppCompletedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppCompletedException.
     */
    public NEOAppCompletedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
