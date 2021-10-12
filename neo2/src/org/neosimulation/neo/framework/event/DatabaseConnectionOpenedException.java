/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DatabaseConnectionOpenedException - An exception thrown by the DatabaseConnectionOpened class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionOpenedException extends TableProcessorException {

    /**
     * Constructs a new DatabaseConnectionOpenedException with no message specified
     */
    public DatabaseConnectionOpenedException()
    {
    }

    /**
     * Constructs a new DatabaseConnectionOpenedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DatabaseConnectionOpenedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DatabaseConnectionOpenedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionOpenedException.
     */
    public DatabaseConnectionOpenedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DatabaseConnectionOpenedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionOpenedException.
     */
    public DatabaseConnectionOpenedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}