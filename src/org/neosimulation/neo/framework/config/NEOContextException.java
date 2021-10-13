/**
 * 
 */
package org.neosimulation.neo.framework.config;

import org.neosimulation.neo.framework.manager.ModelManagerException;

/**
 * NEOContextException - An exception thrown by the NEOContext class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOContextException extends ModelManagerException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NEOContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new NEOContextException with no message specified
     */
    public NEOContextException()
    {
    }

    /**
     * Constructs a new NEOContextException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOContextException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOContextException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOContextException.
     */
    public NEOContextException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOContextException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOContextException.
     */
    public NEOContextException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
