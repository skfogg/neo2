/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import org.neosimulation.neo.framework.model.SimulationModelException;

/**
 * OutputExecutorException - An exception thrown by the OutputExecutor class
 * when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputExecutorException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputExecutorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputExecutorException with no message specified
     */
    public OutputExecutorException()
    {
    }

    /**
     * Constructs a new OutputExecutorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputExecutorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputExecutorException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputExecutorException.
     */
    public OutputExecutorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputExecutorException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputExecutorException.
     */
    public OutputExecutorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}