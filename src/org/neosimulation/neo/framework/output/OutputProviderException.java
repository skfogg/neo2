/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.NEOException;

/**
 * OutputProviderException - An exception thrown by the OutputProvider class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputProviderException extends OutputEventException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputProviderException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputProviderException with no message specified
     */
    public OutputProviderException()
    {
    }

    /**
     * Constructs a new OutputProviderException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputProviderException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputProviderException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputProviderException.
     */
    public OutputProviderException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputProviderException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputProviderException.
     */
    public OutputProviderException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
