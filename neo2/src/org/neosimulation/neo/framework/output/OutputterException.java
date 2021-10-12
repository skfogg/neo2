/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.NEOException;

/**
 * OutputterException - An exception thrown by the Outputter class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputterException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputterException with no message specified
     */
    public OutputterException()
    {
    }

    /**
     * Constructs a new OutputterException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputterException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputterException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterException.
     */
    public OutputterException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputterException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterException.
     */
    public OutputterException(String message, Throwable cause)
    {
        super(message, cause);
    }
}