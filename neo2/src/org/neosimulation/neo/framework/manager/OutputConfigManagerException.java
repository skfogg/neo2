/**
 * 
 */
package org.neosimulation.neo.framework.manager;

import org.neosimulation.neo.framework.model.SimulationModelException;

/**
 * OutputConfigManagerException - An exception thrown by the OutputConfigManager
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputConfigManagerException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputConfigManagerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputConfigManagerException with no message specified
     */
    public OutputConfigManagerException()
    {
    }

    /**
     * Constructs a new OutputConfigManagerException with the specified string
     * as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputConfigManagerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputConfigManagerException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputConfigManagerException.
     */
    public OutputConfigManagerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputConfigManagerException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputConfigManagerException.
     */
    public OutputConfigManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
