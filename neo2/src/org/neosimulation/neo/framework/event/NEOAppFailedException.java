/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOAppFailedException - An exception thrown by the NEOAppFailed class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOAppFailedException extends TableProcessorException {

    /**
     * Constructs a new NEOAppFailedException with no message specified
     */
    public NEOAppFailedException()
    {
    }

    /**
     * Constructs a new NEOAppFailedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOAppFailedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOAppFailedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppFailedException.
     */
    public NEOAppFailedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOAppFailedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppFailedException.
     */
    public NEOAppFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
