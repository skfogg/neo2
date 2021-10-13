/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.NEOException;

/**
 * StateValException - An exception thrown by the StateVal
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateValException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public StateValException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new StateValException with the default message.
     */
    public StateValException()
    {
    }

    /**
     * Constructs a new StateValException with the specified string as
     * the message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public StateValException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateValException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            StateValException
     */
    public StateValException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValException which passes the provided
     * message and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public StateValException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
