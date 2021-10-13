/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * CellPoolDoubleException - An exception thrown by the CellPoolDouble class when a
 * problem occurs
 * @author Isaac Griffith
 *
 */
public class CellPoolDoubleException extends StateDoubleException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellPoolDoubleException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellPoolDoubleException with no message specified
     */
    public CellPoolDoubleException()
    {
    }

    /**
     * Constructs a new CellPoolDoubleException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CellPoolDoubleException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CellPoolDoubleException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellPoolDoubleException.
     */
    public CellPoolDoubleException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellPoolDoubleException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellPoolDoubleException.
     */
    public CellPoolDoubleException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
