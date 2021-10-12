/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DatabaseConnectionFailedException - An exception thrown by the DatabaseConnectionFailed class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionFailedException extends TableProcessorException {

    /**
     * Constructs a new DatabaseConnectionFailedException with no message specified
     */
    public DatabaseConnectionFailedException()
    {
    }

    /**
     * Constructs a new DatabaseConnectionFailedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DatabaseConnectionFailedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DatabaseConnectionFailedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionFailedException.
     */
    public DatabaseConnectionFailedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DatabaseConnectionFailedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionFailedException.
     */
    public DatabaseConnectionFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
