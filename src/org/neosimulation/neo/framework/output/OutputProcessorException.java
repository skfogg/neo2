/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.NEOException;

/**
 * OutputProcessorException - An exception thrown by the OutputProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputProcessorException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputProcessorException with no message specified
     */
    public OutputProcessorException()
    {
    }

    /**
     * Constructs a new OutputProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputProcessorException.
     */
    public OutputProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputProcessorException.
     */
    public OutputProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}