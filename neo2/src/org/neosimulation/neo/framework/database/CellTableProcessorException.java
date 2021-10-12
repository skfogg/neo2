/**
 * 
 */
package org.neosimulation.neo.framework.database;


/**
 * CellTableProcessorException - An exception thrown by the CellTableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class CellTableProcessorException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellTableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellTableProcessorException with no message specified
     */
    public CellTableProcessorException()
    {
    }

    /**
     * Constructs a new CellTableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CellTableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CellTableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellTableProcessorException.
     */
    public CellTableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellTableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellTableProcessorException.
     */
    public CellTableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
