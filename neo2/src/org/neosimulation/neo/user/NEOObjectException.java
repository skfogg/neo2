/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.manager.ModelManagerException;

/**
 * NEOObjectException - An exception thrown by the NEOObject class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOObjectException extends ModelManagerException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NEOObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new NEOObjectException with no message specified
     */
    public NEOObjectException()
    {
    }

    /**
     * Constructs a new NEOObjectException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOObjectException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOObjectException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOObjectException.
     */
    public NEOObjectException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOObjectException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOObjectException.
     */
    public NEOObjectException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
