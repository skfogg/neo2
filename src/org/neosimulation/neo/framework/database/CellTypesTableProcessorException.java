/**
 * 
 */
package org.neosimulation.neo.framework.database;


/**
 * CellTypesTableProcessorException - An exception thrown by the CellTypesTableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class CellTypesTableProcessorException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellTypesTableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellTypesTableProcessorException with no message specified
     */
    public CellTypesTableProcessorException()
    {
    }

    /**
     * Constructs a new CellTypesTableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CellTypesTableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CellTypesTableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellTypesTableProcessorException.
     */
    public CellTypesTableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellTypesTableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellTypesTableProcessorException.
     */
    public CellTypesTableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
