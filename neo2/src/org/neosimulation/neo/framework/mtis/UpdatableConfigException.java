/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

import org.neosimulation.neo.framework.NEOException;

/**
 * UpdatableConfigException - An exception thrown by the UpdatableConfig class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class UpdatableConfigException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UpdatableConfigException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new UpdatableConfigException with no message specified
     */
    public UpdatableConfigException()
    {
    }

    /**
     * Constructs a new UpdatableConfigException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public UpdatableConfigException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new UpdatableConfigException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this UpdatableConfigException.
     */
    public UpdatableConfigException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new UpdatableConfigException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this UpdatableConfigException.
     */
    public UpdatableConfigException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
