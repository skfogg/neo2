/**
 * 
 */
package org.neosimulation.neo.framework.database;

import org.neosimulation.neo.framework.NEOException;

/**
 * DatabaseManagerException - An exception thrown by the DatabaseManager class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DatabaseManagerException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DatabaseManagerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new DatabaseManagerException with no message specified
     */
    public DatabaseManagerException()
    {
    }

    /**
     * Constructs a new DatabaseManagerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DatabaseManagerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DatabaseManagerException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseManagerException.
     */
    public DatabaseManagerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DatabaseManagerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DatabaseManagerException.
     */
    public DatabaseManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
