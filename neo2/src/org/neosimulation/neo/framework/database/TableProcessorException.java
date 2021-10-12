/**
 * 
 */
package org.neosimulation.neo.framework.database;

import org.neosimulation.neo.framework.NEOException;

/**
 * TableProcessorException - An exception thrown by the TableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class TableProcessorException extends NEODbControllerException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new TableProcessorException with no message specified
     */
    public TableProcessorException()
    {
    }

    /**
     * Constructs a new TableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public TableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new TableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TableProcessorException.
     */
    public TableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new TableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TableProcessorException.
     */
    public TableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
