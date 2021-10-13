/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DatabaseConnectionClosedException - An exception thrown by the DatabaseConnectionClosed class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionClosedException extends TableProcessorException {

    /**
     * Constructs a new DatabaseConnectionClosedException with no message specified
     */
    public DatabaseConnectionClosedException()
    {
    }

    /**
     * Constructs a new DatabaseConnectionClosedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DatabaseConnectionClosedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DatabaseConnectionClosedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionClosedException.
     */
    public DatabaseConnectionClosedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DatabaseConnectionClosedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionClosedException.
     */
    public DatabaseConnectionClosedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
