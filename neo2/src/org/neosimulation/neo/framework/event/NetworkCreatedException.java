/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NetworkCreatedException - An exception thrown by the NetworkCreated class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NetworkCreatedException extends TableProcessorException {

    /**
     * Constructs a new NetworkCreatedException with no message specified
     */
    public NetworkCreatedException()
    {
    }

    /**
     * Constructs a new NetworkCreatedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NetworkCreatedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NetworkCreatedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NetworkCreatedException.
     */
    public NetworkCreatedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NetworkCreatedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NetworkCreatedException.
     */
    public NetworkCreatedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
