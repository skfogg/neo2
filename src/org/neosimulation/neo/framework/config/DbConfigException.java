/**
 * 
 */
package org.neosimulation.neo.framework.config;

import org.neosimulation.neo.framework.NEOException;

/**
 * DbConfigException - An exception thrown by the DbConfig class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DbConfigException extends ModelConfigException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DbConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new DbConfigException with no message specified
     */
    public DbConfigException()
    {
    }

    /**
     * Constructs a new DbConfigException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DbConfigException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DbConfigException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DbConfigException.
     */
    public DbConfigException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DbConfigException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DbConfigException.
     */
    public DbConfigException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
