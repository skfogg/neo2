/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DatabaseConnectionCreatedException - An exception thrown by the DatabaseConnectionCreated class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionCreatedException extends TableProcessorException {

    /**
     * Constructs a new DatabaseConnectionCreatedException with no message specified
     */
    public DatabaseConnectionCreatedException()
    {
    }

    /**
     * Constructs a new DatabaseConnectionCreatedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DatabaseConnectionCreatedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DatabaseConnectionCreatedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionCreatedException.
     */
    public DatabaseConnectionCreatedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DatabaseConnectionCreatedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseConnectionCreatedException.
     */
    public DatabaseConnectionCreatedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
