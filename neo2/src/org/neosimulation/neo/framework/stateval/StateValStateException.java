/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.NEOException;

/**
 * StateValStateException - An exectpion thrown by the StateValState class (and
 * its subclasses) when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateValStateException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public StateValStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new StateValStateException with no message
     */
    public StateValStateException()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new StateValStateException with the specified string as the
     * message returend
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StateValStateException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new StateValStateException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValStateException.
     */
    public StateValStateException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValStateException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValStateException.
     */
    public StateValStateException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
