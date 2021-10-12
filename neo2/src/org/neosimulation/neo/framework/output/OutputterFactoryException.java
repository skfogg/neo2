/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.NEOException;

/**
 * OutputterFactoryException - An exception thrown by the OutputterFactory class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputterFactoryException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputterFactoryException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputterFactoryException with no message specified
     */
    public OutputterFactoryException()
    {
    }

    /**
     * Constructs a new OutputterFactoryException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputterFactoryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputterFactoryException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterFactoryException.
     */
    public OutputterFactoryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputterFactoryException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterFactoryException.
     */
    public OutputterFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
