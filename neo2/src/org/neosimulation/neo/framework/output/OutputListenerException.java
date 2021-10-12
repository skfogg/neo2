/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.NEOException;

/**
 * OutputListenerException - An exception thrown by the OutputListener class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputListenerException extends OutputEventException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputListenerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputListenerException with no message specified
     */
    public OutputListenerException()
    {
    }

    /**
     * Constructs a new OutputListenerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputListenerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputListenerException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputListenerException.
     */
    public OutputListenerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputListenerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputListenerException.
     */
    public OutputListenerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
